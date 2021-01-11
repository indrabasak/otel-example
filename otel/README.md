java -javaagent:path/to/opentelemetry-javaagent-all.jar \
-Dotel.exporter=zipkin \
-jar myapp.jar



otel.exporter=jaeger
otel.exporter.jaeger.endpoint

The Jaeger gRPC endpoint to connect to. Default is localhost:14250.

otel.exporter.jaeger.service.name

The service name of this JVM instance. Default is unknown.

java -javaagent:otel/opentelemetry-javaagent-all.jar \
-Dotel.exporter=jaeger \
-Dotel.exporter.jaeger.service.name=otel-server \
-jar server/target/otel-server-1.0.0.jar

java -javaagent:otel/opentelemetry-javaagent-all.jar \
-Dotel.exporter=jaeger \
-Dotel.exporter.jaeger.endpoint=localhost:14250 \
-Dotel.exporter.jaeger.service.name=otel-server \
-jar server/target/otel-server-1.0.0.jar

java -javaagent:otel/opentelemetry-javaagent-all.jar \
-Dotel.exporter=jaeger \
-Dotel.exporter.jaeger.endpoint=localhost:14250 \
-Dotel.exporter.jaeger.service.name=otel-client \
-jar client/target/otel-client-1.0.0.jar

Jaeger Install
Install Operator

```
kubectl create namespace observability # <1>
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/crds/jaegertracing.io_jaegers_crd.yaml # <2>
kubectl create -n observability -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/service_account.yaml
kubectl create -n observability -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/role.yaml
kubectl create -n observability -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/role_binding.yaml
kubectl create -n observability -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/operator.yaml
```

<1> This creates the namespace used by default in the deployment files. If you want to install the Jaeger operator in a different namespace, you must edit the deployment files to change observability to the desired namespace value.

<2> This installs the “Custom Resource Definition” for the apiVersion: jaegertracing.io/v1

The operator will activate extra features if given cluster-wide permissions. To enable that, run:

```
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/cluster_role.yaml
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/cluster_role_binding.yaml
```

Note that you’ll need to download and customize the cluster_role_binding.yaml if you are using a namespace other than observability. You probably also want to download and customize the operator.yaml, setting the env var WATCH_NAMESPACES to have an empty value, so that it can watch for instances across all namespaces.

At this point, there should be a jaeger-operator deployment available. You can view it by running the following command:
```
$ kubectl get deployment jaeger-operator -n observability

NAME              DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
jaeger-operator   1         1         1            1           48s
```

```
NAME              READY   UP-TO-DATE   AVAILABLE   AGE
jaeger-operator   1/1     1            1           2d2h
```

The operator is now ready to create Jaeger instances.

##Quick Start - Deploying the AllInOne image
The simplest possible way to create a Jaeger instance is by creating a YAML file like the following example. This will install the default AllInOne strategy, which deploys the “all-in-one” image (agent, collector, query, ingestor, Jaeger UI) in a single pod, using in-memory storage by default.

The YAML file can then be used with kubectl:
```
kubectl apply -f simplest.yaml
```

In a few seconds, a new in-memory all-in-one instance of Jaeger will be available, suitable for quick demos and development purposes. To check the instances that were created, list the jaeger objects:
```
$ kubectl get jaegers
NAME       STATUS   VERSION   STRATEGY   STORAGE   AGE
simplest                                           10s
```

To get the pod name, query for the pods belonging to the simplest Jaeger instance:
```
$ kubectl get pods -l app.kubernetes.io/instance=simplest
NAME                        READY     STATUS    RESTARTS   AGE
simplest-6499bb6cdd-kqx75   1/1       Running   0          2m
```

docker run -d --name jaeger -p 16686:16686 -p 6831:6831/udp jaegertracing/all-in-one:1.21.0
docker run -d --name jaeger -p 16686:16686 -p 14250:14250 -p 6831:6831/udp jaegertracing/all-in-one:1.21.0

```
 docker ps
CONTAINER ID   IMAGE                             COMMAND                  CREATED          STATUS          PORTS                                                                                                  NAMES
41d67a05ea19   jaegertracing/all-in-one:1.21.0   "/go/bin/all-in-one-…"   40 seconds ago   Up 39 seconds   5775/udp, 5778/tcp, 14250/tcp, 6832/udp, 14268/tcp, 0.0.0.0:6831->6831/udp, 0.0.0.0:16686->16686/tcp   jaeger
e3a2ac00a415   jaegertracing/jaeger-operator     "/usr/local/bin/jaeg…"   2 days ago       Up 2 days                                                                                                              k8s_jaeger-operator_jaeger-operator-6d4fd54fc7-66zw8_observability_1f9eb6a3-d7b8-4201-a2df-d2d318c87b7e_0
```

```
docker ps -a
CONTAINER ID   IMAGE                             COMMAND                  CREATED         STATUS         PORTS                                                                                                  NAMES
41d67a05ea19   jaegertracing/all-in-one:1.21.0   "/go/bin/all-in-one-…"   3 minutes ago   Up 3 minutes   5775/udp, 5778/tcp, 14250/tcp, 6832/udp, 14268/tcp, 0.0.0.0:6831->6831/udp, 0.0.0.0:16686->16686/tcp   jaeger
e3a2ac00a415   jaegertracing/jaeger-operator     "/usr/local/bin/jaeg…"   2 days ago      Up 2 days                                                                                                             k8s_jaeger-operator_jaeger-operator-6d4fd54fc7-66zw8_observability_1f9eb6a3-d7b8-4201-a2df-d2d318c87b7e_0
```

```
 docker ps -a
CONTAINER ID   IMAGE                             COMMAND                  CREATED         STATUS         PORTS                                                                                                                 NAMES
82251037faac   jaegertracing/all-in-one:1.21.0   "/go/bin/all-in-one-…"   3 seconds ago   Up 2 seconds   5775/udp, 5778/tcp, 0.0.0.0:14250->14250/tcp, 6832/udp, 14268/tcp, 0.0.0.0:6831->6831/udp, 0.0.0.0:16686->16686/tcp   jaeger
e3a2ac00a415   jaegertracing/jaeger-operator     "/usr/local/bin/jaeg…"   2 days ago      Up 2 days                                                                                                                            k8s_jaeger-operator_jaeger-operator-6d4fd54fc7-66zw8_observability_1f9eb6a3-d7b8-4201-a2df-d2d318c87b7e_0
```

```
 kubectl port-forward $(kubectl get pods -l=app="jaeger" -o name) 16686:16686
error: TYPE/NAME and list of ports are required for port-forward
See 'kubectl port-forward -h' for help and examples
(base) Indra-Mac:otel-example indra$ kubectl get pods -l=app="jaeger" -o name
(base) Indra-Mac:otel-example indra$ kubectl logs -n observability deployment/jaeger-operator
time="2021-01-08T02:39:13Z" level=info msg=Versions arch=amd64 identity=observability.jaeger-operator jaeger=1.21.0 jaeger-operator=v1.21.2 operator-sdk=v0.18.2 os=linux version=go1.14.12
time="2021-01-08T02:39:14Z" level=info msg="Consider running the operator in a cluster-wide scope for extra features"
time="2021-01-08T02:39:14Z" level=info msg="Auto-detected the platform" platform=kubernetes
time="2021-01-08T02:39:14Z" level=info msg="Auto-detected ingress api" ingress-api=networking
time="2021-01-08T02:39:14Z" level=info msg="Automatically adjusted the 'es-provision' flag" es-provision=no
time="2021-01-08T02:39:14Z" level=info msg="Automatically adjusted the 'kafka-provision' flag" kafka-provision=no
time="2021-01-08T02:39:16Z" level=info msg="Install prometheus-operator in your cluster to create ServiceMonitor objects" error="no ServiceMonitor registered with the API"
(base) Indra-Mac:otel-example indra$ 
```

## Prometheus

```
kubectl delete deployments jaeger-operator -n observability
deployment.apps "jaeger-operator" deleted
(base) Indra-Mac:otel-example indra$ kubectl create -f otel/k8s/prometheus-operator.yml 
customresourcedefinition.apiextensions.k8s.io/alertmanagerconfigs.monitoring.coreos.com created
customresourcedefinition.apiextensions.k8s.io/alertmanagers.monitoring.coreos.com created
customresourcedefinition.apiextensions.k8s.io/podmonitors.monitoring.coreos.com created
customresourcedefinition.apiextensions.k8s.io/probes.monitoring.coreos.com created
customresourcedefinition.apiextensions.k8s.io/prometheuses.monitoring.coreos.com created
customresourcedefinition.apiextensions.k8s.io/prometheusrules.monitoring.coreos.com created
customresourcedefinition.apiextensions.k8s.io/servicemonitors.monitoring.coreos.com created
customresourcedefinition.apiextensions.k8s.io/thanosrulers.monitoring.coreos.com created
clusterrolebinding.rbac.authorization.k8s.io/prometheus-operator created
clusterrole.rbac.authorization.k8s.io/prometheus-operator created
deployment.apps/prometheus-operator created
serviceaccount/prometheus-operator created
service/prometheus-operator created
```

```
$ kubectl create -n observability -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/operator.yaml
deployment.apps/jaeger-operator created
(base) Indra-Mac:otel-example indra$ kubectl logs -n observability deployment/jaeger-operator
time="2021-01-11T05:06:33Z" level=info msg=Versions arch=amd64 identity=observability.jaeger-operator jaeger=1.21.0 jaeger-operator=v1.21.2 operator-sdk=v0.18.2 os=linux version=go1.14.12
time="2021-01-11T05:06:33Z" level=info msg="Consider running the operator in a cluster-wide scope for extra features"
time="2021-01-11T05:06:34Z" level=info msg="Auto-detected the platform" platform=kubernetes
time="2021-01-11T05:06:34Z" level=info msg="Auto-detected ingress api" ingress-api=networking
time="2021-01-11T05:06:34Z" level=info msg="Automatically adjusted the 'es-provision' flag" es-provision=no
time="2021-01-11T05:06:34Z" level=info msg="Automatically adjusted the 'kafka-provision' flag" kafka-provision=no
(base) Indra-Mac:otel-example indra$ kubectl get deployment jaeger-operator -n observability
NAME              READY   UP-TO-DATE   AVAILABLE   AGE
jaeger-operator   1/1     1            1           50s
(base) Indra-Mac:otel-example indra$ kubectl apply -f otel/simplest.yaml 
jaeger.jaegertracing.io/simplest unchanged
(base) Indra-Mac:otel-example indra$ kubectl get pods
NAME                                   READY   STATUS    RESTARTS   AGE
prometheus-operator-6657f58748-7kcsc   1/1     Running   0          6m23s
```

```
$ kubectl get pods -l app.kubernetes.io/instance=simplest
No resources found in default namespace.
(base) Indra-Mac:otel-example indra$ kubectl get jaegers
NAME       STATUS   VERSION   STRATEGY   STORAGE   AGE
simplest                                           23h
```