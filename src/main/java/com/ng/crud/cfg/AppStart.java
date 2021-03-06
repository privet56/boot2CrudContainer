package com.ng.crud.cfg;

import java.io.File;
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
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreFactory;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.cloud.FirestoreClient;
import com.ng.crud.util.Env;

//
@Component
public class AppStart implements ApplicationListener<ApplicationReadyEvent>
{
	public static final String FIRESTORE_EMULATOR_HOST 				= "FIRESTORE_EMULATOR_HOST";
	public static final String FIREBASE_FIRESTORE_EMULATOR_ADDRESS 	= "FIREBASE_FIRESTORE_EMULATOR_ADDRESS";
	public static final String GOOGLE_APPLICATION_CREDENTIALS 		= "GOOGLE_APPLICATION_CREDENTIALS";
	public static final String COLLECTION_NAME 						= "posts";
	
	public static void main(String[] args)
	{
		new AppStart().onApplicationEvent(null);
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event)
	{
		try
		{
			String projectId = "expoapp-1";
			String url = "localhost:8040";
			
			//GoogleCredentials.getApplicationDefault()//needs env var: GOOGLE_APPLICATION_CREDENTIALS
			String path = (new File(".")).getAbsolutePath()+"/src/main/resources/cred-local.json";
			Env.setEnvVar(GOOGLE_APPLICATION_CREDENTIALS, path);
			//Env.setEnvVar(FIRESTORE_EMULATOR_HOST				, url);
			//Env.setEnvVar(FIREBASE_FIRESTORE_EMULATOR_ADDRESS	, url);
			
			//FileInputStream serviceAccount = new FileInputStream("src/main/resources/cred-local.json");
			
			InstantiatingGrpcChannelProvider.Builder b = FirestoreOptions.getDefaultInstance().getDefaultTransportChannelProviderBuilder().setEndpoint(url);
			InstantiatingGrpcChannelProvider igcp = b.build();
			FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder().setHost(url).setChannelProvider(igcp).build();
			
			FirebaseOptions options = new FirebaseOptions.Builder()
				//.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setCredentials(GoogleCredentials.getApplicationDefault())		//reads GOOGLE_APPLICATION_CREDENTIALS
			    .setProjectId(projectId)
			    .setDatabaseUrl(url)
			    .setFirestoreOptions(firestoreOptions)
			    .build();
			
			FirebaseApp.initializeApp(options);
			Firestore db = FirestoreClient.getFirestore();
			
			CollectionReference posts = db.collection(COLLECTION_NAME);
			
			for(int i=0;i<3;i++)
			{	//write data
				DocumentReference docRef = posts.document("doc-"+i+"-"+System.currentTimeMillis());
				Map<String, Object> data = new HashMap<>();
				data.put("first", "Ada! "+System.currentTimeMillis());
				ApiFuture<WriteResult> result = docRef.set(data);					// asynchronously write data,	result.get() blocks on response
				out("Written!: " + result.get().getUpdateTime()+" \t url:"+url);
				result.get();
			}
			{	//read data
				ApiFuture<QuerySnapshot> query = db.collection(COLLECTION_NAME).get();		// asynchronously retrieve all users
				QuerySnapshot querySnapshot = query.get();									// query.get() blocks on response
				List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
				int i= 0;
				for (QueryDocumentSnapshot document : documents)
				{
					out("doId: " + document.getId());
				  i++;
				}
				out("Read!: #docs:" + i);
				
			}
			
			out("AppStart: FINISH");
		}
		catch(Exception e)
		{
			out("AppStart: Exception: "+e);			
			e.printStackTrace();
		}
	}
	protected void out(String s)
	{
		System.err.println(s);
	}
}

/*{
CreateRequest request = new CreateRequest()
        .setEmail("user@example.com")
        .setEmailVerified(false)
        .setPassword("secretPassword")
        .setPhoneNumber("+11234567890")
        .setDisplayName("John Doe")
        .setPhotoUrl("http://www.example.com/12345678/photo.png")
        .setDisabled(false);

    UserRecord userRecord = FirebaseAuth.getInstance().createUserAsync(request).get();
}*/

//Exception: java.io.IOException: The Application Default Credentials are not available. They are available if running in Google Compute Engine. Otherwise, the environment variable GOOGLE_APPLICATION_CREDENTIALS must be defined pointing to a file defining the credentials. See https://developers.google.com/accounts/docs/application-default-credentials
//GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();	// Use the application default credentials

//File f = new File(".");
//System.err.println(f.getCanonicalPath());//=project dir
