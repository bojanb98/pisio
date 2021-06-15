FROM adoptopenjdk/openjdk11

COPY maven /app

WORKDIR /app

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${spring.profiles.active}", "${project.artifactId}-${project.version}.jar"]