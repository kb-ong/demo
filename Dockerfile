FROM eclipse-temurin:17
COPY test.jar /usr/apps/demo.jar
ENV JAVA_OPTS="-Xmx2g -Xms256m"
ENTRYPOINT ["java", "-jar", "/usr/apps/demo.jar" ]
