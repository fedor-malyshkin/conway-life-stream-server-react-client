# conway-life-stream-reactor-client
![ci-cd](https://github.com/fedor-malyshkin/conway-life-stream-reactor-client/workflows/ci-cd/badge.svg)

Basic [Reactor](https://projectreactor.io/)-based listening implementation with REST endpoints for statistics, created with Spring Boot.

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