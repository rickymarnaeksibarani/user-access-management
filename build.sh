mvn clean install &&
docker build -f alpine.Dockerfile -t microservice/utils-userman-service:latest . && docker-compose up -d