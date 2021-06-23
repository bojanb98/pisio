package net.etfbl.pisio.audiocutservice;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@AllArgsConstructor
public class AudioCutKafkaHandler {

    private final KafkaTemplate<String, ImageJobData> kafkaTemplate;

    @KafkaListener(topics = "mcut")
    public void listen(ImageJobData jobData) {
        System.out.println(jobData);
        kafkaTemplate.send("done", jobData);
    }
}
