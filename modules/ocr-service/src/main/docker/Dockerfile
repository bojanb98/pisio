FROM adoptopenjdk/openjdk11

COPY maven /app

WORKDIR /app

RUN apt-get update && apt-get install tesseract-ocr -y

RUN mkdir /tessdata
COPY ~/Desktop/tessdata /tessdata

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${spring.profiles.active}", "${project.artifactId}-${project.version}.jar"]