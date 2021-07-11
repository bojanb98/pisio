package net.etfbl.pisio.fileservice.config;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.model.FileWriteStatus;
import net.etfbl.pisio.fileservice.service.FileWriteStatusService;
import net.etfbl.pisio.kafkaconfiguration.model.FileWriteData;
import net.etfbl.pisio.kafkaconfiguration.model.JobStatusData;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Configuration
@AllArgsConstructor
@EnableKafka
public class FileJobDoneKafkaHandler {

    private final FileProperties fileProperties;
    private final FileWriteStatusService fileWriteStatusService;
    private final KafkaTemplate<String, JobStatusData> statusKafkaTemplate;

    @KafkaListener(topics = "destFile", groupId = "writer")
    public void saveDoneJob(FileWriteData doneJob) throws IOException {
        String jobId = doneJob.getJobId();
        FileWriteStatus writeStatus = fileWriteStatusService.getWriteStatusByJob(jobId);
        if (writeStatus == null) {
            writeStatus = FileWriteStatus
                    .builder()
                    .jobId(jobId)
                    .numFilesWritten(0)
                    .build();
        }
        Path targetDir = fileProperties.getPath().resolve(jobId);
        if (!Files.exists(targetDir)) {
            Files.createDirectory(targetDir);
        }
        Files.write(targetDir.resolve(doneJob.getFileName()), doneJob.getData(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        writeStatus.setNumFilesWritten(writeStatus.getNumFilesWritten() + 1);
        fileWriteStatusService.persistWriteStatus(writeStatus);
        JobStatusData jobStatusData = JobStatusData.builder()
                .jobId(doneJob.getJobId())
                .jobPart(convertWriteToStatusPart(doneJob.getJobPart()))
                .partStatus(JobStatusData.PartStatus.DONE)
                .build();
        statusKafkaTemplate.send("jobStatus", jobStatusData);
    }

    private JobStatusData.JobPart convertWriteToStatusPart(FileWriteData.JobPart jobPart) {
        if (jobPart == FileWriteData.JobPart.GIF) {
            return JobStatusData.JobPart.GIF;
        }
        return JobStatusData.JobPart.PDF;
    }

    @Bean
    public NewTopic fileTopic() {
        return TopicBuilder
                .name("destFile")
                .build();
    }
}
