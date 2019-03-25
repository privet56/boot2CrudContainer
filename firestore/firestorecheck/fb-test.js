const firebase = require('@firebase/testing');

setTimeout(async function()
{
  try
  {
    console.log("step-1");
    const apps = firebase.initializeAdminApp({projectId: 'expoapp', databaseName: 'http://localhost:8040/', databaseURL: 'http://localhost:8040/', port: 8040, host: 'http://localhost:8040/',});
    //apps.functions().useFunctionsEmulator('http://localhost:8040');
    const app = firebase.apps()[0];
    const firestore = app.firestore();
    const document = firestore.doc('posts/intro-to-firestore');
    console.log("step: await document.set({ ...");
    await document.set({
      title: 'Welcome to Firestore',
      body: 'Hello World',
    });
    console.log("step-after set...");
    const doc = await document.get();
    console.log(doc.data());
    console.log("FINISH");
  }
  catch (error)
  {
    console.log("ERROR");
    console.error(error);
  }

  Promise.all(firebase.apps().map(app => app.delete()));

}, 0);
