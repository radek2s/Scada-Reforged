FROM openjdk:17-alpine

ENV RABBITMQ_URL=rabbitmq

WORKDIR /opt/srf

COPY build/libs/srf-ds-virtual-*.jar ./srf-ds-virtual.jar

CMD ["java", "-jar", "/opt/srf/srf-ds-virtual.jar"]

STOPSIGNAL SIGTERM