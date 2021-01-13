[![Build Status][travis-badge]][travis-badge-url]
[![Quality Gate][sonarqube-badge]][sonarqube-badge-url] 
[![Technical debt ratio][technical-debt-ratio-badge]][technical-debt-ratio-badge-url] 
[![Coverage][coverage-badge]][coverage-badge-url]

![](./img/tls.jpg)

Example of a Spring Boot Application Instrumented with OpenTelemetry
=========================================================================
This is an example of instrumenting a Spring Boot Application with OpenTelemetry.

### Build
To build the JAR, execute the following command from the parent directory:

```
mvn clean install
```

### Run
Run the executable jar from the command to start the application,
```
docker run -it basaki/otel-server:1.0.0 bin/sh
```

```
docker run --rm -p 8080:8080  --name=otel-server basaki/otel-server:1.0.0

 docker run --rm -p 8080:8080  --name=otel-server basaki/otel-server:1.0.0
 
 docker run --rm -p 8081:8081  --name=otel-client basaki/otel-client:1.0.0
```

### Usage
TODO


[travis-badge]: https://travis-ci.org/indrabasak/otel-example.svg?branch=master
[travis-badge-url]: https://travis-ci.org/indrabasak/otel-example/

[sonarqube-badge]: https://sonarcloud.io/api/project_badges/measure?project=com.basaki%3Aotel-example&metric=alert_status
[sonarqube-badge-url]: https://sonarcloud.io/dashboard/index/com.basaki:otel-example 

[technical-debt-ratio-badge]: https://sonarcloud.io/api/project_badges/measure?project=com.basaki%3Aotel-example&metric=sqale_index
[technical-debt-ratio-badge-url]: https://sonarcloud.io/dashboard/index/com.basaki:otel-example 

[coverage-badge]: https://sonarcloud.io/api/project_badges/measure?project=com.basaki%3Aotel-example&metric=coverage
[coverage-badge-url]: https://sonarcloud.io/dashboard/index/com.basaki:otel-example
