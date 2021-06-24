package net.etfbl.pisio.fileservice.service;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.model.ByteExtractionException;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileAccessService {

    private static final String IMAGES_TOPIC = "images";

    private final KafkaTemplate<String, ImageJobData> kafkaTemplate;


    public String uploadFiles(MultipartFile[] files) {
        String jobId = UUID.randomUUID().toString();
        List<byte[]> filesBytes = Arrays.stream(files).map(this::extractBytesFromFile).collect(Collectors.toList());
        ImageJobData jobData = new ImageJobData(jobId, filesBytes);
        kafkaTemplate.send(IMAGES_TOPIC, jobData);
        return jobId;
    }

    private byte[] extractBytesFromFile(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException ex) {
            throw new ByteExtractionException(ex.getMessage(), ex);
        }
    }
}
