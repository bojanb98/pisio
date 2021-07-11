package net.etfbl.pisio.pdfservice;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.FileWriteData;
import net.etfbl.pisio.kafkaconfiguration.model.JobStatusData;
import net.etfbl.pisio.kafkaconfiguration.model.StringJobData;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@AllArgsConstructor
@EnableKafka
public class PdfKafkaHandler {

    private final PdfService pdfService;
    private final KafkaTemplate<String, FileWriteData> writeKafkaTemplate;
    private final KafkaTemplate<String, JobStatusData> statusKafkaTemplate;

    @KafkaListener(topics = "pdf", groupId = "pdf_creator")
    public void handleString(StringJobData stringJobData) throws DocumentException {
        JobStatusData jobStatusData = JobStatusData.builder()
                .jobId(stringJobData.getJobId())
                .jobPart(JobStatusData.JobPart.PDF)
                .partStatus(JobStatusData.PartStatus.IN_PROGRESS)
                .build();
        statusKafkaTemplate.send("jobStatus", jobStatusData);
        byte[] pdfBytes = pdfService.createPdfFromStrings(stringJobData.getImagesText());
        FileWriteData fileWriteData = FileWriteData.builder()
                .jobId(stringJobData.getJobId())
                .fileName("images.pdf")
                .data(pdfBytes)
                .jobPart(FileWriteData.JobPart.PDF)
                .build();
        writeKafkaTemplate.send("destFile", fileWriteData);
    }

    @Bean
    public NewTopic pdfTopic() {
        return TopicBuilder
                .name("pdf")
                .build();
    }
}
