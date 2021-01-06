# conway-life-stream-reactor-client
![ci-cd](https://github.com/fedor-malyshkin/conway-life-stream-reactor-client/workflows/ci-cd/badge.svg)

## Overview
A basic stream aggregator that is built with help of [Reactor](https://projectreactor.io/) (a fourth-generation reactive library, based on the Reactive Streams
specification, for building non-blocking applications on the JVM).

This application is developed to be deployed in Kubernetes cluster, listen to the stream from other similar service
[conway-life-stream-server](https://github.com/fedor-malyshkin/conway-life-stream-server) (deployed in the same cluster) and aggregates
results with 2 internal streams and eventually publish the resulting aggregated stream through the single HTTP endpoint.

## Article
* TBD

## Used technologies
* [Reactor library](https://projectreactor.io/)
* [Spring WebFlux](https://spring.io/reactive)
* Java 15
* [Java records](https://openjdk.java.net/jeps/359) 
* [GitHub Container Registry](https://github.com/fedor-malyshkin?tab=packages) - a new Docker image repo from GitHub

### Reference documentation:
* [Reactor library](https://projectreactor.io/docs/core/release/reference/#which.create)
* [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)

## Requirements
* JDK 15

## Client
TBD

## Compilation and run
As a build tool I use [gradle](https://gradle.org/) and `gradle-wrapper`, so you can easily compile and build
application with `./gradlew build` and run with `./gradlew run`.

## CI/CD
For integration and deployment was used GitHub's framework - GitHub Actions. Workflow source is available [there](.github/workflows/ci-cd.yml)
Active workflow could be found [there](https://github.com/fedor-malyshkin/conway-life-stream-reactor-client/actions).

## Deployment
For deployment Kubernetes cluster was used. Deployment descriptors are available in `deployment` sub-folder.

### How to create Docker image manually
```shell
./gradlew build
docker build -t ghcr.io/fedor-malyshkin/conway-life-stream-reactor-client:latest -f docker/Dockerfile build/libs
```

### How to push to GitHub Container Registry manually
```shell
export CR_PAT=YOUR_TOKEN
echo $CR_PAT | docker login ghcr.io -u fedor-malyshkin --password-stdin
docker push ghcr.io/fedor-malyshkin/conway-life-stream-reactor-client:latest
```

### Kubernetes deployment:
The initial deployment is done by the commands (in `deployment` sub-folder):
```sh
kubectl apply -f stream-reactor-client-deployment.yml
kubectl apply -f stream-reactor-client-service.yml
kubectl apply -f stream-reactor-client-loadbalancer.yml
```

The assigned IP can be found with the command:
```sh
kubectl describe service/stream-server-loadbalancer | egrep Ingress
```

Whereas the correctness of work with the command:
```sh
curl http://<IP>/health
```

## Some technicalities
### Enable netty access to unsafe operations

As netty intensively uses some unsafe JVM features - they should be open by the set of special commands.
Add such keys to your java app and don't forget to use them in your IDE during test (Dockerfile is updated for it)

```ssh
--add-opens java.base/jdk.internal.misc=ALL-UNNAMED
-Dio.netty.tryReflectionSetAccessible=true
--illegal-access=warn
```

### netty's epool library

Don't forget to attach native JNI library that add features specific to a particular platform,
generate less garbage, and generally improve performance when compared to the NIO based transport.

Gradle:
```dtd
implementation "io.netty:netty-transport-native-epoll:${EPOLL_NATIVE_VER}:linux-x86_64"
```

### use of record and their serialization
It requires pay a close attention to version of serializer you use as support for java records has been added 
recently enough.

