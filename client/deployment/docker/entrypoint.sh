#!/bin/bash

cd /usr/local/basaki/otel-client
[[ -f config/env ]] && . config/env

java $JAVA_OPTS -javaagent:/usr/local/otel/opentelemetry-javaagent-all.jar -jar bin/otel-client.jar