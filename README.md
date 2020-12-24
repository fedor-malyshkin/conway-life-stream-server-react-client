# conway-life-stream-reactor-client
![ci-cd](https://github.com/fedor-malyshkin/conway-life-stream-reactor-client/workflows/ci-cd/badge.svg)

Basic [Reactor](https://projectreactor.io/)-based listening implementation with REST endpoints for statistics, created with Spring Boot.

## Requirements
* JDK 15

## How to create Docker image locally
```shell
./gradlew build
docker build -t fedormalyshkin/conway-life-stream-reactor-client:latest -f docker/Dockerfile build/libs
```