# Firestore
## Build firestore Emulator as Docker image
```sh
# build docker image
docker build -t my_google_sdk_docker:alpine .
# del: docker rmi my_google_sdk_docker:alpine
# check if image built:
docker image ls | grep my_google_sdk_docker
# //TODO:_is persistence necessary? (-v ${PWD}/firestore-data:/opt/data)
docker run --rm -p 8040:8040 -it --name my_gcloud my_google_sdk_docker:alpine /bin/bash
> gcloud components list
> gcloud beta emulators firestore start --host-port=localhost:8040
```
<img src="../_res/firestoreAsDockerContainer.png" width="650px">
export FIRESTORE_EMULATOR_HOST=localhost:8040

