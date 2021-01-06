# don't use jdk - better jre
FROM adoptopenjdk:15.0.1_9-jre-hotspot

LABEL maintainer="Fedor Malyshkin"

EXPOSE 8080

RUN mkdir -p /opt/conway-life/
COPY conway-life-stream-reactor-client-boot.jar /opt/conway-life/
WORKDIR /opt/conway-life/

# let's create user for our app
RUN groupadd -r runuser && useradd -r -s /bin/false -g runuser runuser
# change our rights on workdir
RUN chown -R runuser:runuser  /opt/conway-life/
USER runuser

CMD java --enable-preview --add-opens java.base/jdk.internal.misc=ALL-UNNAMED -Dio.netty.tryReflectionSetAccessible=true --illegal-access=warn $JAVA_OPTS -Dfile.encoding=UTF-8 -jar conway-life-stream-reactor-client-boot.jar $JAVA_ARGS
