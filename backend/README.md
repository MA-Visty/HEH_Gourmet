# Backend

## Installation

### Requirements

- Preconfigured database

### Docker

1. Install [Docker](https://docs.docker.com/get-docker/)
2. use `gradlew docker-build` to build the docker image ( it will take compile the project and build the docker image).
   You can use the environment variable `DOCKERHUB_USERNAME` to specify the dockerhub username. If you don't specify it,
   it will not follow the dockerhub name scheme.
3. use your preferred method to run the docker image. You will need use the environment `DB_URL` to
   specify the database url. You can use the environment variable `DB_USERNAME` to specify the database
   username. You can use the environment variable `DB_PASSWORD` to specify the database password.
4. Optional Step : if the name follow the dockerhub schema you can use `gradlew docker-push` to push the image onto
   dockerhub