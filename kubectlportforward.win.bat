set PATH=%PATH%;c:\Program Files\Oracle\VirtualBox;c:\kube
rem my app
start cmd /k kubectl port-forward cranky-panther-boot2crud-helmworkflow-7986d4b745-6b8bx 8080
rem port-forward from prometheus pod by name
start cmd /k kubectl port-forward prometheus-76b7745b64-wgv44 9090 -n istio-system
rem grafana
start cmd /k kubectl port-forward grafana-59b8896965-qj4ff 3000 -n istio-system
rem jaeger ui (istio-tracing)
start cmd /k kubectl port-forward istio-tracing-6b994895fd-hvbfd 16686 -n istio-system
