FROM adoptopenjdk/openjdk11

COPY maven /app

RUN apt-get update
RUN apt-get install -y tesseract-ocr
RUN apt-get install -y tesseract-ocr-srp tesseract-ocr-bos tesseract-ocr-srp-latn

WORKDIR /app

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${spring.profiles.active}", "${project.artifactId}-${project.version}.jar"]