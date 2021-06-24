package net.etfbl.pisio.fileservice.service;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.config.FileProperties;
import org.springframework.stereotype.Service;

import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
@AllArgsConstructor
public class FileZipService {

    private final FileProperties fileProperties;

    public ZipInputStream getZippedResults(String jobId) {
        return null;
    }
}
