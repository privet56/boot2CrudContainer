# k8s for Windows

## Software
1. pre-requisites:
	1. windows 10 with VT-x/AMD virtualization enabled
	2. correct Hyper-V setting (en- or disabled, if you have VirtualBox)
	3. 
2. install Docker Toolbox for Windows
3. download kubectl.exe
4. download minikube.exe
5. download helm.exe (and tiller.exe)

## Setup k8s
1. put the above binaries into c:\kube\ & your PATH (it has to be C:\!)
2. # minikube get-k8s-versions	# not possible any more ... :-(
3. start as admin
	1. with hyperv:
		1. docker-machine create -d hyperv --hyperv-virtual-switch "myswitch" myvm1	# ...does not work for me :-(
		2. minikube.exe start --kubernetes-version="v1.13.0" --vm-driver=hyperv
	2. with virtualbox:	
		1. deactivate Windows-features -> hyperv (& reboot & wait some minutes while rebooting)
		3. minikube.exe start --kubernetes-version="v1.13.0" --vm-driver="virtualbox" --memory 8192
4. kubectl.exe cluster-info
5. minikube.exe dashboard --url=true	# -> open it!

## Install istio
1. download istio ZIP manually & unpack
2. exec in its dir:
	1. helm template install/kubernetes/helm/istio --name istio --namespace istio-system --set servicegraph.enable=true --set ingress.enable=true --set tracing.enabled=true --set zipkin.enabled=true --set grafana.enabled=true --set servicegraph.enabled=true --set global.proxy.includeIPRanges="10.0.0.1/24" > ./istio4boot2crud.windows.yaml
	2. kubectl create namespace istio-system
	3. kubectl label namespace default istio-injection=enabled
	4. kubectl apply -f ./istio4boot2crud.windows.yaml
	5. pray! (+check in the k8s dashboard)
