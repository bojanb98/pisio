package net.etfbl.pisio.fileservice.api;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.service.FileAccessService;
import net.etfbl.pisio.fileservice.model.FileJob;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileAccessController {

    private final FileAccessService fileAccessService;


    @PostMapping("/download")
    @ResponseBody
    public Resource downloadFile(@RequestBody @Validated FileJob fileJob) {
        return fileAccessService.getFileResource(fileJob.getJobId());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public FileJob uploadFile(@RequestParam("file") MultipartFile file) {
        String jobId = fileAccessService.uploadFile(file);
        if (jobId == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new FileJob(jobId);
    }
}
