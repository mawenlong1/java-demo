cd /d E:\IdeaProjects\java-demo\spring-boot-docker
call mvn clean package -U -DskipTests
cd /d %~dp0
del *.jar
copy E:\IdeaProjects\java-demo\spring-boot-docker\target\spring-boot-docker-*.jar spring-boot-docker-*.jar
ren spring-boot-docker-*.jar spring-boot-docker.jar
docker build -t spring-boot-docker .
docker run -d -p 8080:8080 --name spring-boot-docker spring-boot-docker