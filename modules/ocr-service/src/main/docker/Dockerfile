FROM adoptopenjdk/openjdk11

COPY maven /app

RUN apt-get update
RUN apt-get install tesseract-ocr=4.1.1-2build2 -y

RUN mkdir /tessdata
RUN curl https://github.com/tesseract-ocr/tessdata/blob/master/bos.traineddata > /tessdata/bos.traineddata
RUN curl https://github.com/tesseract-ocr/tessdata/blob/master/eng.traineddata > /tessdata/eng.traineddata
RUN curl https://github.com/tesseract-ocr/tessdata/blob/master/osd.traineddata > /tessdata/osd.traineddata
RUN curl https://github.com/tesseract-ocr/tessdata/blob/master/srp.traineddata > /tessdata/srp.traineddata
RUN curl https://github.com/tesseract-ocr/tessdata/blob/master/srp_latn.traineddata > /tessdata/srp_latn.traineddata

WORKDIR /app

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${spring.profiles.active}", "${project.artifactId}-${project.version}.jar"]