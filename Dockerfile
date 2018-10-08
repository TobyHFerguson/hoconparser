FROM maven:3.3.3-jdk-8 as build
WORKDIR /project
COPY . /project
RUN mvn -B -f pom.xml package

FROM frolvlad/alpine-oraclejre8
WORKDIR /jars
COPY --from=build /project/target/hocon-0.0.1-SNAPSHOT.jar /jars
ENTRYPOINT ["/usr/bin/java", "-jar", "/jars/hocon-0.0.1-SNAPSHOT.jar"]
CMD ["-h"]
