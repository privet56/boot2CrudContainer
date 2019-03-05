set PATH=%PATH%;c:\Program Files\Oracle\VirtualBox;c:\kube
rem my app
start cmd /k kubectl port-forward esteemed-angelfish-boot2crud-helmworkflow-899bb9558-s7jrv 8080
rem port-forward from prometheus pod by name
start cmd /k kubectl port-forward prometheus-76b7745b64-jbrtw 9090 -n istio-system
rem grafana
start cmd /k kubectl port-forward grafana-59b8896965-glds9 3000 -n istio-system
rem jaeger ui (istio-tracing)
start cmd /k kubectl port-forward istio-tracing-6b994895fd-wrq79 16686 -n istio-system
