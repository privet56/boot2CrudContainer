set PATH=%PATH%;c:\Program Files\Oracle\VirtualBox;c:\kube
rem my app
start cmd /k kubectl port-forward musty-poodle-boot2crud-helmworkflow-84884dd886-7fk6d 8080
rem port-forward from prometheus pod by name
start cmd /k kubectl port-forward prometheus-76b7745b64-q88lw 9090 -n istio-system
rem grafana
start cmd /k kubectl port-forward grafana-59b8896965-f59g2 3000 -n istio-system
rem jaeger ui (istio-tracing)
start cmd /k kubectl port-forward istio-tracing-6b994895fd-fg8tb 16686 -n istio-system
