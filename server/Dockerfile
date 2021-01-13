FROM openjdk:11-jre

# Set the locale
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

MAINTAINER Indra Basak <indra.basak1@gmail.com>

# Expose SERVICE_NAME and PROJECT_VERSION to be passed in at build time, by the maven-dockerfile-plugin
ARG SERVICE_NAME
ARG PROJECT_VERSION
ADD target/${SERVICE_NAME}-${PROJECT_VERSION}.jar /usr/local/basaki/${SERVICE_NAME}/bin/${SERVICE_NAME}.jar
RUN echo target/${SERVICE_NAME}-${PROJECT_VERSION}.jar
ADD deployment/docker/entrypoint.sh /usr/local/basaki/${SERVICE_NAME}/bin
RUN chmod 755 /usr/local/basaki/${SERVICE_NAME}/bin/entrypoint.sh
RUN mkdir /usr/local/basaki/${SERVICE_NAME}/config
ADD deployment/docker/env /usr/local/basaki/${SERVICE_NAME}/config
RUN chmod 755 /usr/local/basaki/${SERVICE_NAME}/config/env

# Take care of OpenTelemetry files
ADD target/otel /usr/local/otel

ENV SERVICE_NAME ${SERVICE_NAME}
CMD /usr/local/basaki/${SERVICE_NAME}/bin/entrypoint.sh