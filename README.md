# boot2CrudContainer
Spring Boot App with REST WebServices in Docker Container with Kubernetes and Helm

## Setup on Ubuntu
### Install through ui: krusader, kate, konsole, docker, minikube
### Install through cmd: jdk, curl, git, dockerd(docker.io)
	$ sudo apt install default-jdk
	$ sudo apt install curl
	$ sudo apt install git
	$ sudo apt install docker.io
### Install through web: STS - Spring Tool Suite 4

## Java Development: with STS - Spring Tool Suite 4
<img src="_res/sts.png" width="650px">

## Swagger
### Editor (JSON or YAML)
<img src="_res/swagger.editor.png" width="650px">
<img src="_res/swagger.editor.2.png" width="650px">

### Browser - showing the REST API of the App
http://localhost:8080/swagger-ui.html
<img src="_res/swagger.api.png" width="650px">

## Docker build & run
(optional: you can docker-build through a maven-goal too (with a maven plug-in inserted in your pom.xml))
```sh
sudo bash 							# console as root
docker build -t boot2crud_image .
docker run -d -p 9090:8080 --name boot2crud_container boot2crud_image
docker ps							# lists running images --> open browser: http://localhost:9090/swagger-ui.html
docker kill boot2crud_container
docker ps -a						# lists killed containers too...
```
<img src="_res/docker.run.png" width="650px">

## k8s - local
In a VM, pre-requisite is numCPU>1
```sh
docker run -d -p 5000:5000 --restart=always --name registry registry:2
docker build -t boot2crud_image .
docker tag boot2crud_image localhost:5000/boot2crud_image
docker push localhost:5000/boot2crud_image

### if you want to use kvm as k8s-minikube-vm-driver:
# apt install libvirt-clients libvirt-daemon-system qemu-kvm
# usermod -a -G libvirt $(whoami)
# newgrp libvirt

grep -c ^processor /proc/cpuinfo	# returns numCPU
curl -Lo kubectl https://storage.googleapis.com/kubernetes-release/release/v1.13.2/bin/linux/amd64/kubectl && chmod +x kubectl && sudo cp kubectl /usr/local/bin/ && rm kubectl
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && chmod +x minikube
sudo cp minikube /usr/local/bin
sudo minikube start --vm-driver=kvm2 --insecure-registry localhost

# remember to turn off the imagePullPolicy:Always, as otherwise Kubernetes won't use images you built locally.
kubectl apply -f ./k8s/deployment.yaml
kubectl apply -f ./k8s/service.yaml
minikube service boot2crud-service --url # open the returned URL in the browser to see your app!
kubectl delete -f ./k8s/service.yaml
# kubectl delete deployment boot2crud-deployment
# kubectl expose deployment boot2crud-deployment --type=NodePort --port 8080 --target-port 9091
```
