package net.etfbl.pisio.fileservice.config;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.FileWriteData;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@AllArgsConstructor
public class FileJobDoneKafkaHandler {

    private final FileProperties fileProperties;

    @KafkaListener(topics = "destFile", groupId = "writer")
    public void saveDoneJob(FileWriteData doneJob) throws IOException {
        String jobId = doneJob.getJobId();
        Path targetDir = fileProperties.getPath().resolve(jobId);
        if (Files.exists(targetDir)) {
            Files.createDirectory(targetDir);
        }
        Files.write(targetDir.resolve(doneJob.getFileName()), doneJob.getData());
    }
}
