package net.etfbl.pisio.fileservice.service;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.model.FileWriteStatus;
import net.etfbl.pisio.fileservice.repository.FileWriteStatusRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileWriteStatusService {

    private final FileWriteStatusRepository fileWriteStatusRepository;

    public FileWriteStatus getWriteStatusByJob(String jobId) {
        return fileWriteStatusRepository.findById(jobId).orElse(null);
    }

    public void persistWriteStatus(FileWriteStatus fileWriteStatus) {
        fileWriteStatusRepository.save(fileWriteStatus);
    }
}
