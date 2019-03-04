set PATH=%PATH%;c:\Program Files\Oracle\VirtualBox;c:\kube
rem my app
start cmd /k kubectl port-forward ironic-penguin-boot2crud-helmworkflow-864d5fbbfc-xp8mx 8080
rem port-forward from prometheus pod by name
start cmd /k kubectl port-forward prometheus-76b7745b64-w8mcx 9090 -n istio-system
rem grafana
start cmd /k kubectl port-forward grafana-59b8896965-gn7rv 3000 -n istio-system
rem jaeger ui (istio-tracing)
start cmd /k kubectl port-forward istio-tracing-6b994895fd-npjnc 16686 -n istio-system
