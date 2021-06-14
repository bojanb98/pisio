package net.etfbl.pisio.fileservice.service;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.config.FileProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileAccessService {

    private final FileProperties fileProperties;

    public Resource getFileResource(String jobId) {
        return new FileSystemResource(fileProperties.getPath().resolve(jobId));
    }

    public String uploadFile(MultipartFile file) {
        String jobId = UUID.randomUUID().toString();
        try {
            Files.write(fileProperties.getPath().resolve(jobId), file.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jobId;
    }
}
