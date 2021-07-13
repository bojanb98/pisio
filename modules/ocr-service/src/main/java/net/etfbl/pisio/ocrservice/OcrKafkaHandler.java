package net.etfbl.pisio.ocrservice;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import net.etfbl.pisio.kafkaconfiguration.model.JobStatusData;
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
    private final KafkaTemplate<String, StringJobData> pdfKafkaTemplate;
    private final KafkaTemplate<String, JobStatusData> statusKafkaTemplate;

    @KafkaListener(topics = "images", groupId = "ocr")
    public void handleImages(ImageJobData imageJobData) {
        JobStatusData jobStatusData = JobStatusData.builder()
                .jobId(imageJobData.getJobId())
                .jobPart(JobStatusData.JobPart.OCR)
                .partStatus(JobStatusData.PartStatus.IN_PROGRESS)
                .build();
        statusKafkaTemplate.send("jobStatus", jobStatusData);
        List<String> result = ocrService.doOcr(imageJobData.getImagesBytes());
        StringJobData stringJobData = StringJobData.builder()
                .jobId(imageJobData.getJobId())
                .imagesText(result)
                .build();
        pdfKafkaTemplate.send("pdf", stringJobData);
        jobStatusData = JobStatusData.builder()
                .jobId(imageJobData.getJobId())
                .jobPart(JobStatusData.JobPart.OCR)
                .partStatus(JobStatusData.PartStatus.DONE)
                .build();
        statusKafkaTemplate.send("jobStatus", jobStatusData);
    }

    @Bean
    public NewTopic imagesTopic() {
        return TopicBuilder
                .name("images")
                .build();
    }
}
