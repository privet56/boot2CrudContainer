kubectl create namespace istio-system
pause
kubectl label namespace default istio-injection=enabled
pause
kubectl apply -f install/kubernetes/helm/helm-service-account.yaml
pause
helm init --service-account tiller
pause
helm install install/kubernetes/helm/istio --name istio --namespace istio-system --set gateways.istio-ingressgateway.type=NodePort --set gateways.istio-egressgateway.type=NodePort --set sidecarInjectorWebhook.enabled=true --set global.mtls.enabled=false --set servicegraph.enable=true --set ingress.enable=true --set tracing.enabled=true --set zipkin.enabled=true --set grafana.enabled=true --set servicegraph.enabled=true --set global.proxy.includeIPRanges="10.0.0.1/24"
pause
