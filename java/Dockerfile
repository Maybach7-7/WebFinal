FROM maven:3.9.9-eclipse-temurin-21 as compiler
ARG REPO_NAME="FormHandler"
WORKDIR /app

COPY $REPO_NAME/pom.xml .
COPY $REPO_NAME/src ./src
RUN mvn clean package

FROM tomcat:jre21
COPY --from=compiler /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
