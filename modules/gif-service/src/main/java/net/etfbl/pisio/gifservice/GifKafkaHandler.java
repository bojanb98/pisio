package net.etfbl.pisio.gifservice;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.FileWriteData;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableKafka
@AllArgsConstructor
public class GifKafkaHandler {

    private final GifService gifService;
    private final KafkaTemplate<String, FileWriteData> kafkaTemplate;

    @KafkaListener(topics = "images", groupId = "gif")
    public void handleImages(ImageJobData imageJobData) {
        byte[] gifData = gifService.createGif(imageJobData.getImagesBytes());
        FileWriteData writeData = new FileWriteData(imageJobData.getJobId(), "images.gif", gifData);
        kafkaTemplate.send("destFile", writeData);
    }

    @Bean
    public NewTopic imagesTopic() {
        return TopicBuilder
                .name("images")
                .build();
    }
}
