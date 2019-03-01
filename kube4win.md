# k8s for Windows

## Software
1. pre-requisites:
	1. windows 10 with VT-x/AMD virtualization enabled
	2. correct Hyper-V setting (en- or disabled, if you have VirtualBox)
2. install Docker Toolbox for Windows
3. download kubectl.exe
4. download minikube.exe
5. download helm.exe (and tiller.exe)

## Setup k8s
1. put the above binaries into c:\kube\ & your PATH (it has to be C:\!)
2. // minikube get-k8s-versions	# not possible any more ... :-(
3. start as admin from C:\kube\
	1. with hyperv:
		1. docker-machine create -d hyperv --hyperv-virtual-switch "myswitch" myvm1	# ...does not work for me :-(
		2. minikube.exe start --kubernetes-version="v1.13.0" --vm-driver="hyperv" --memory 8192 --insecure-registry localhost --docker-env NO_PROXY="localhost"
	2. with virtualbox:	
		1. deactivate Windows-features -> hyperv (& reboot & wait some minutes while rebooting)
		3. minikube.exe start --kubernetes-version="v1.13.0" --vm-driver="virtualbox" --memory 8192 --insecure-registry localhost --docker-env NO_PROXY="localhost"
4. kubectl.exe cluster-info
5. minikube.exe dashboard --url=true	# -> open it!
6. helm init & start docker registry (as described in [README.md](README.md))
	1. helm init should be done always after a minikube delete

## Install istio
1. download istio ZIP manually & unpack
2. exec in itio's dir:
	1. helm template install/kubernetes/helm/istio --name istio --namespace istio-system --set servicegraph.enable=true --set ingress.enable=true --set tracing.enabled=true --set zipkin.enabled=true --set grafana.enabled=true --set servicegraph.enabled=true --set global.proxy.includeIPRanges="10.0.0.1/24" > ./istio4boot2crud.windows.yaml
	2. kubectl create namespace istio-system
	3. kubectl label namespace default istio-injection=enabled
	4. kubectl apply -f ./istio4boot2crud.windows.yaml
	5. pray! (+check in the k8s dashboard)

## setup local docker registry within k8s
1. helm install stable/docker-registry --set service.nodePort=30500,service.type=NodePort # does not work
	
## Install your app
1. start local docker registry, push your image (as described in [README.md](README.md#k8s---local-setup))
2. helm install ... (as described in [README.md](README.md#helm))

## Gotchas:
1. Failed to pull image "localhost:5000/boot2crud_image:latest": rpc error: code = Unknown desc = Error response from daemon: Get http://localhost:5000/v2/: dial tcp 127.0.0.1:5000: connect: connection refused
	1. solution 1:
		1. kubectl get svc kube-dns -n kube-system  # note CLUSTER-IP
		1. minikube ssh
		1. chmod 777 /run/systemd/resolve/
		1. winscp into minikube (docker:tcuser)
		1. add 'namespace {CLUSTER-IP} into /run/systemd/resolve/resolv.conf (del old file)
		1. do the same with /etc/systemd/resolved.conf
		1. sudo systemctl restart systemd-resolved --wait
		... did not help
	2. solution 2:
		1. minikube stop
		1. VBoxManage modifyvm "minikube" --natdnshostresolver1 on		# get vm name with $ VBoxManage list vms
		2. minikube start {other params, as above}
		... did not help
	3. start minikube with docker-env:
		1. TODO: try it!
			1. --docker-env localhost:5000				or
			2. --docker-env NO_PROXY="localhost:5000"		or
			3. --docker-env NO_PROXY="localhost"
	4. instead of localhost, use the IP of your host (eg 172.27.12.92)
		1. on the command-line
			--docker-env
			--insecure-registry
		2. in values.yaml
		3. when creating the tag on docker
	5. add "InsecureRegistry": ["172.27.12.92:5000"], into c:\Users\{un}\.docker\machine\machines\default\config.json
	6. deploy your local registry within k8s & port-forward to localhost!
2. Error restarting cluster: restarting kube-proxy: waiting for kube-proxy to be up for configmap update: timed out waiting for the condition
	1. try minikube delete and del c:\Users\{un}\.minikube\ and start again...
