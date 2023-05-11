run: --redis --mongodb --generateapi --build --fatjar --execute

--generateapi:
	./openapigenerator.sh

--build:
	./gradlew clean build

--fatjar:
	./gradlew shadow

--execute:
	VERTX_CONFIG_PATH=$(CURDIR)/services/events-api/src/main/resources/config.yaml ./gradlew run

--redis:
	docker run --name redislocal -p 6379:6379 -d redis

--mongodb:
	docker run -d -p 27017:27017 --name some-mongo mongo:latest
