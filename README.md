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

## Tested featured
* [Reactor library](https://projectreactor.io/)
* [Spring WebFlux](https://spring.io/reactive), documentation [there](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
* Java 15
* [Java records](https://openjdk.java.net/jeps/359) 

## Requirements
* JDK 15

## How to create Docker image locally
```shell
./gradlew build
docker build -t ghcr.io/fedor-malyshkin/conway-life-stream-reactor-client:latest -f docker/Dockerfile build/libs
```

## How to push to GitHub Container Registry
```shell
export CR_PAT=YOUR_TOKEN
echo $CR_PAT | docker login ghcr.io -u fedor-malyshkin --password-stdin
docker push ghcr.io/fedor-malyshkin/conway-life-stream-reactor-client:latest
```

## Some technicalities
### Enable netty access to unsafe operations

As netty intensively uses some unsafe JVM features - they should be open by set of special commands.
Add such keys to your java app and don't forget to use them in your IDE during test (Dockerfile is updated for it)
```ssh
--add-opens java.base/jdk.internal.misc=ALL-UNNAMED
-Dio.netty.tryReflectionSetAccessible=true
--illegal-access=warn
```
### netty's epool library
### use of record and their serialization