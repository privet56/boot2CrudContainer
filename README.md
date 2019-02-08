# boot2CrudContainer
Spring Boot App with REST WebServices in Docker Container with Kubernetes and Helm

## Setup on Ubunut
### installed through ui: krusader, kate, konsole, docker, minikube
### installed through cmd: jdk, curl, git, dockerd(docker.io)
	$ sudo apt install default-jdk
	$ sudo apt install curl
	$ sudo apt install git
	$ sudo apt install docker.io
### installed through web: sts

## Java Development: with STS - Spring Tool Suite 4
<img src="_res/sts.png" width="650px">

## Swagger
### Editor (JSON or YAML)
<img src="_res/swagger.editor.png" width="650px">
<img src="_res/swagger.editor.2.png" width="650px">

### Browser - showing the REST API of the App
http://localhost:8080/swagger-ui.html
<img src="_res/swagger.api.png" width="650px">

## Docker build
(optional: you can docker-build a a maven-goal too (with a maven plug-in inserted in your pom.xml))
```sh
sudo docker build .
sudo docker run -p 8080:8080 76f0e2cf29a3
```
