FROM adoptopenjdk/openjdk13:alpine

LABEL maintainer="Fedor Malyshkin"

EXPOSE 8080

RUN mkdir -p /opt/conway-life/

COPY conway-life-stream-reactor-client-all.jar /opt/conway-life/

WORKDIR /opt/conway-life/

CMD java $JAVA_OPTS -Dfile.encoding=UTF-8 -jar conway-life-stream-reactor-client-all.jar $JAVA_ARGS
