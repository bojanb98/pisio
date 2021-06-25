package net.etfbl.pisio.fileservice.service;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.config.FileProperties;
import net.etfbl.pisio.fileservice.model.ZipException;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@AllArgsConstructor
public class FileZipService {

    private static final String TARGET_FILENAME = "images.zip";

    private final FileProperties fileProperties;

    public Path getZippedResults(String jobId) {
        Path targetDir = fileProperties.getPath().resolve(jobId);
        if (!Files.exists(targetDir.resolve(TARGET_FILENAME))) {
            this.createZipEntry(targetDir);
        }
        return targetDir.resolve(TARGET_FILENAME);
    }

    private void createZipEntry(Path targetDir) {
        String zipFilePath = targetDir.resolve(TARGET_FILENAME).toFile().getAbsolutePath();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            List<Path> files = Files.list(targetDir).collect(Collectors.toList());
            for (Path targetFile : files) {
                zos.putNextEntry(new ZipEntry(targetFile.toString()));
                byte[] bytes = Files.readAllBytes(targetFile);
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
            }
        } catch (IOException ex) {
            throw new ZipException(ex.getMessage(), ex);
        }
    }
}
