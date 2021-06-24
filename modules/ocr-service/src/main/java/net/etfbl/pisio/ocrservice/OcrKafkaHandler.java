package net.etfbl.pisio.ocrservice;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import net.etfbl.pisio.kafkaconfiguration.model.StringJobData;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Configuration
@AllArgsConstructor
@EnableKafka
public class OcrKafkaHandler {

    private final OcrService ocrService;
    private final KafkaTemplate<String, StringJobData> kafkaTemplate;

    @KafkaListener(topics = "images", groupId = "ocr")
    public void handleImages(ImageJobData imageJobData) {
        List<String> result = ocrService.doOcr(imageJobData.getImagesBytes());
        StringJobData stringJobData = new StringJobData(imageJobData.getJobId(), result);
        kafkaTemplate.send("pdf", stringJobData);
    }

    @Bean
    public NewTopic imagesTopic() {
        return TopicBuilder
                .name("images")
                .build();
    }
}
