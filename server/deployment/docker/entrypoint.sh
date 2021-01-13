#!/bin/bash

cd /usr/local/basaki/otel-server
[[ -f config/env ]] && . config/env

java $JAVA_OPTS -javaagent:/usr/local/otel/opentelemetry-javaagent-all.jar -jar bin/otel-server.jar