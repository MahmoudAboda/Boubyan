#FROM maven
#RUN mkdir /app
#WORKDIR /app
#COPY . /app
#RUN mvn clean install
#CMD "mvn" "exec:java"

FROM openjdk:17
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]