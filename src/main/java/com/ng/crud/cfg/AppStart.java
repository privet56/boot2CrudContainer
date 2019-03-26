package com.ng.crud.cfg;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

//
@Component
public class AppStart implements ApplicationListener<ApplicationReadyEvent>
{
	public static void main(String[] args)
	{
		new AppStart().onApplicationEvent(null);
	}

	public static class MockGoogleCredentials extends GoogleCredentials {

		  private String tokenValue;
		  private long expiryTime;

		  public MockGoogleCredentials() {
		    this(null);
		  }

		  public MockGoogleCredentials(String tokenValue) {
		    this(tokenValue, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
		  }

		  public MockGoogleCredentials(String tokenValue, long expiryTime) {
		    this.tokenValue = tokenValue;
		    this.expiryTime = expiryTime;
		  }

		  @Override
		  public AccessToken refreshAccessToken() throws IOException {
		    return new AccessToken(tokenValue, new Date(expiryTime));
		  }

		  public void setExpiryTime(long expiryTime) {
		    this.expiryTime = expiryTime;
		  }
		}
	
	public static final FirestoreOptions FIRESTORE_OPTIONS = FirestoreOptions.newBuilder()
		      // Setting credentials is not required (they get overridden by Admin SDK), but without
		      // this Firestore logs an ugly warning during tests.
		      .setCredentials(new MockGoogleCredentials("owner"))
		      .setTimestampsInSnapshotsEnabled(true)
		      .build();
	
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event)
	{
		try
		{
			String projectId = "expoapp";
			String url = "http://localhost:8080/";
			System.setProperty("FIRESTORE_EMULATOR_HOST", url);
			//injectEnvironmentVariable("FIRESTORE_EMULATOR_HOST", url);
			//Exception: java.io.IOException: The Application Default Credentials are not available. They are available if running in Google Compute Engine. Otherwise, the environment variable GOOGLE_APPLICATION_CREDENTIALS must be defined pointing to a file defining the credentials. See https://developers.google.com/accounts/docs/application-default-credentials
			//GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();	// Use the application default credentials
			
			//FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
			    //.setCredentials(credentials)
				//.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setCredentials(new MockGoogleCredentials("test-token"))
			    .setProjectId(projectId)
			    .setDatabaseUrl(url)
			    .setFirestoreOptions(FIRESTORE_OPTIONS)
			    .build();
			
			FirebaseApp.initializeApp(options);
	
			Firestore db = FirestoreClient.getFirestore();
			
			CollectionReference posts = db.collection("posts");
			
			{	//write data
				DocumentReference docRef = posts.document("doc-"+System.currentTimeMillis());
				Map<String, Object> data = new HashMap<>();
				data.put("first", "Ada");
				//asynchronously write data
				ApiFuture<WriteResult> result = docRef.set(data);
				// result.get() blocks on response
				System.err.println("Written!: " + result.get().getUpdateTime());
			}
			{	//read data
				// asynchronously retrieve all users
				ApiFuture<QuerySnapshot> query = db.collection("users").get();
				// ...
				// query.get() blocks on response
				QuerySnapshot querySnapshot = query.get();
				List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
				int i= 0;
				for (QueryDocumentSnapshot document : documents) {
				  System.out.println("doId: " + document.getId());
				  i++;
				}
				System.err.println("Read!: #docs:" + i);
			}
			
			System.err.println("AppStart: FINISH");
		}
		catch(Exception e)
		{
			System.err.println("AppStart: Exception: "+e);
			e.printStackTrace();
		}
	}
	private static Field getAccessibleField(Class<?> clazz, String fieldName)
            throws NoSuchFieldException {

        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }
	private static void injectIntoUnmodifiableMap(String key, String value, Object map)
            throws ReflectiveOperationException {

        Class unmodifiableMap = Class.forName("java.util.Collections$UnmodifiableMap");
        Field field = getAccessibleField(unmodifiableMap, "m");
        Object obj = field.get(map);
        ((Map<String, String>) obj).put(key, value);
    }
	 private static void injectEnvironmentVariable(String key, String value)
	            throws Exception {

	        Class<?> processEnvironment = Class.forName("java.lang.ProcessEnvironment");

	        Field unmodifiableMapField = getAccessibleField(processEnvironment, "theUnmodifiableEnvironment");
	        Object unmodifiableMap = unmodifiableMapField.get(null);
	        injectIntoUnmodifiableMap(key, value, unmodifiableMap);

	        Field mapField = getAccessibleField(processEnvironment, "theEnvironment");
	        Map<String, String> map = (Map<String, String>) mapField.get(null);
	        map.put(key, value);
	    }
}
