package net.etfbl.pisio.fileservice.service;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.config.FileProperties;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileAccessService {

    private final FileProperties fileProperties;
    private final KafkaTemplate<String, ImageJobData> kafkaTemplate;

    public Resource getFileResource(String jobId) {
        return new FileSystemResource(fileProperties.getPath().resolve(jobId));
    }

    public String uploadFile(MultipartFile file) {
        String jobId = UUID.randomUUID().toString();
        try {
            ImageJobData jobData = new ImageJobData(jobId, file.getBytes());
            kafkaTemplate.send("mcut", jobData);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jobId;
    }
}
