//set env before loading firebase script!
process.env['FIREBASE_FIRESTORE_EMULATOR_ADDRESS'] = 'http://localhost:8040/';

const firebase = require('@firebase/testing');

setTimeout(async function()
{
  try
  {
    console.log("step-1");
    const apps = firebase.initializeAdminApp({projectId: 'expoapp', uid:'alice'});
    const app = firebase.apps()[0];
    const firestore = app.firestore();
    const posts = firestore.collection('posts');
    const document = posts.doc('intro-to-firestore'+(new Date()).getTime());
    console.log("step: await document.set({ ... (emulator:"+process.env['FIREBASE_FIRESTORE_EMULATOR_ADDRESS']);
    await document.set({
      title: 'Welcome to Firestore',
      body: 'Hello World'+(new Date()).getTime(),
    });
    console.log("step-after set...");
    const justSetDoc = await document.get();
    console.log("just-set-doc("+justSetDoc.id+"):"+JSON.stringify(justSetDoc.data()));
    const docs = await firestore.collection('posts').get();//TODO: error handling with try-catch
    docs.forEach(function(doc) {
      console.log("one-doc:"+JSON.stringify(doc.data()));
    });
    console.log("FINISH");
  }
  catch (error)
  {
    console.log("ERROR");
    console.error(error);
  }

  Promise.all(firebase.apps().map(app => app.delete()));

}, 0);

/*
firestore.collection('posts').get().then(function(querySnapshot){
  console.log("querySnapshot:"+querySnapshot);
  querySnapshot.forEach(function(doc) {
    console.log("one-doc:"+JSON.stringify(doc.data()));
  })
})
.catch(function(err) {
  console.log("err:"+err);
});
*/
