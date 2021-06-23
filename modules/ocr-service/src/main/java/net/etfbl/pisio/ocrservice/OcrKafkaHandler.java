package net.etfbl.pisio.ocrservice;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@AllArgsConstructor
public class OcrKafkaHandler {

    private final OcrService ocrService;

    @KafkaListener(topics = "images", groupId = "ocr")
    public void handleImages(ImageJobData imageJobData) {

    }
}
