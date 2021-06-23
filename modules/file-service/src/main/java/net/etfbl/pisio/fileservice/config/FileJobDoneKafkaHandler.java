package net.etfbl.pisio.fileservice.config;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;
import java.nio.file.Files;

@Configuration
@AllArgsConstructor
public class FileJobDoneKafkaHandler {

    private final FileProperties fileProperties;

    @KafkaListener(topics = "done")
    public void saveDoneJob(ImageJobData doneJob) throws IOException {
        Files.write(fileProperties.getPath().resolve(doneJob.getJobId()), doneJob.getFileBytes());
    }
}
