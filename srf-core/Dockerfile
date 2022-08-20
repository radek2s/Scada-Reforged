FROM openjdk:17-alpine

ENV INFLUXDB_URL=http://influxdb:8086
ENV DS_VIRTUAL_URL=srf-ds-virtual:8081
ENV RABBITMQ_URL=rabbitmq

WORKDIR /opt/srf

COPY build/libs/srf-core-*.jar ./srf-core.jar

CMD ["java", "-jar", "/opt/srf/srf-core.jar"]

STOPSIGNAL SIGTERM
