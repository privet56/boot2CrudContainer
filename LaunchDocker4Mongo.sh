docker run --name expoappmongo --rm -v /home/ghe/expoappmongodb/:/data/db -e MONGO_INITDB_ROOT_USERNAME=expoapp -e MONGO_INITDB_ROOT_PASSWORD=s3cr3t -p 27017:27017 -d mongo:latest 
