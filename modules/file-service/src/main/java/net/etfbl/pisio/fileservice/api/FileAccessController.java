package net.etfbl.pisio.fileservice.api;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.model.FileJob;
import net.etfbl.pisio.fileservice.model.FileWriteStatus;
import net.etfbl.pisio.fileservice.service.FileAccessService;
import net.etfbl.pisio.fileservice.service.FileWriteStatusService;
import net.etfbl.pisio.fileservice.service.FileZipService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class FileAccessController {

    private static final int NUM_TOTAL_JOB = 2;

    private final FileAccessService fileAccessService;
    private final FileZipService fileZipService;
    private final FileWriteStatusService fileWriteStatusService;

    @PostMapping("/download")
    @ResponseBody
    public Resource downloadFile(@RequestBody @Validated FileJob fileJob) {
        FileWriteStatus writeStatus = fileWriteStatusService.getWriteStatusByJob(fileJob.getJobId());
        if (writeStatus == null || writeStatus.getNumFilesWritten() < NUM_TOTAL_JOB) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        Path zipFilePath = fileZipService.getZippedResults(fileJob.getJobId());
        return new FileSystemResource(zipFilePath);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public FileJob uploadFile(@RequestParam("files") MultipartFile[] files) {
        String jobId = fileAccessService.uploadFiles(files);
        if (jobId == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new FileJob(jobId);
    }
}
