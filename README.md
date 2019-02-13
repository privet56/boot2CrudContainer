# boot2CrudContainer
Spring Boot App with REST WebServices in Docker Container with Kubernetes and Helm & Istio

## Setup on Ubuntu
### Install through UI: krusader, kate, konsole, docker, minikube
### Install through web: STS - Spring Tool Suite 4
### Install through cmd: jdk, curl, git, dockerd(docker.io)
	$ sudo apt install default-jdk
	$ sudo apt install curl
	$ sudo apt install git
	$ sudo apt install docker.io

## Java Development: with STS - Spring Tool Suite 4
<img src="_res/sts.png" width="650px">

## Swagger
### Editor (JSON or YAML)

[./src/main/resources/swagger.api.def.json](./src/main/resources/swagger.api.def.json)

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

## k8s - local Setup
In a VM, pre-requisite is numCPU>1
```sh
docker run -d -p 5000:5000 --restart=always --name registry registry:2	# alternative: minikube addons enable registry
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
cp minikube /usr/local/bin
minikube start --vm-driver=kvm2 --insecure-registry localhost

minikube dashboard 

# remember to turn off the imagePullPolicy:Always, as otherwise Kubernetes won't use images you built locally.

## expose deployment manually:
kubectl apply -f ./k8s/deployment.yaml
kubectl expose deployment boot2crud-deployment --port=8080
minikube service boot2crud-deployment --url		# better: look minikube dashboard -> Services -> boot2crud-service -> Endpoints

## expose through service definition:
kubectl apply -f ./k8s/service.yaml
minikube service boot2crud-deployment --url		# better: look minikube dashboard -> Services -> boot2crud-service -> Endpoints

## cleanup
kubectl delete -f ./k8s/service.yaml
kubectl delete deployment boot2crud-deployment
```
### Local Minikube Dashboard listing exposed Deployment Endpoint:
<img src="_res/k8s.with.minikube.png" width="650px">

## Helm
### Install on Ubuntu
```sh
curl https://raw.githubusercontent.com/helm/helm/master/scripts/get > get_helm.sh
chmod 700 get_helm.sh
./get_helm.sh
helm init	# = sets up helm (happens here nothing project-specific, just machine specific setup in $HOME/.helm)
apt install socat
kubectl get pods --namespace kube-system	# now, tiller(=helm-server-side) will be listed
```
### Usage
helm placeholder configuration:
[./helm/boot2crud-helmworkflow/values.yaml](./helm/boot2crud-helmworkflow/values.yaml)
```sh
cd helm
helm create boot2crud-helmworkflow
# edit placeholder values manually in the created yaml files...
helm install boot2crud-helmworkflow/ 		# outputs also the created deployment name
# cleanup:
helm delete <deployment-name>
```

#### Helm installed chart:
<img src="_res/helm.installed.pod.png" width="650px">

#### TODO: solve 'Liveness probe failed' on local deployment

## Istio
```sh
curl -L https://git.io/getLatestIstio | sh -
kubectl apply -f install/kubernetes/istio-demo-auth.yaml
kubectl get svc -n istio-system 	# lists the installed istio components
kubectl get pods -n istio-system	# wait until pods are started (takes several minutes!)

kubectl get po -n istio-system
kubectl port-forward grafana-59b8896965-cb5v2 -n istio-system 3000

#uninstall:
kubectl delete -f install/kubernetes/istio-demo-auth.yaml

```
1. Prometheus URL: see k8s dashboard, eg. http://172.17.0.16:9090/graph


## TODO:
1. helm - local with liveness & readiness probe
2. istio - local - wip
3. functional/reactive java CRUD implementation
