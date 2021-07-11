package net.etfbl.pisio.gifservice;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.FileWriteData;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import net.etfbl.pisio.kafkaconfiguration.model.JobStatusData;
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
    private final KafkaTemplate<String, FileWriteData> writeKafkaTemplate;
    private final KafkaTemplate<String, JobStatusData> statusKafkaTemplate;

    @KafkaListener(topics = "images", groupId = "gif")
    public void handleImages(ImageJobData imageJobData) {
        JobStatusData jobStatusData = JobStatusData.builder()
                .jobId(imageJobData.getJobId())
                .jobPart(JobStatusData.JobPart.GIF)
                .partStatus(JobStatusData.PartStatus.IN_PROGRESS)
                .build();
        statusKafkaTemplate.send("jobStatus", jobStatusData);
        byte[] gifData = gifService.createGif(imageJobData.getImagesBytes());
        FileWriteData writeData = FileWriteData.builder()
                .jobId(imageJobData.getJobId())
                .data(gifData)
                .fileName("images.gif")
                .jobPart(FileWriteData.JobPart.GIF)
                .build();
        writeKafkaTemplate.send("destFile", writeData);
    }

    @Bean
    public NewTopic imagesTopic() {
        return TopicBuilder
                .name("images")
                .build();
    }
}
