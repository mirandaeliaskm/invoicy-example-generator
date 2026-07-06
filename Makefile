.PHONY: test run docker-build docker-up

test:
	mvn test

run:
	mvn spring-boot:run

docker-build:
	docker build -t invoicy-example-generator .

docker-up:
	docker compose up --build
