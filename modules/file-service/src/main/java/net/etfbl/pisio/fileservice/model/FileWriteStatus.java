package net.etfbl.pisio.fileservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileWriteStatus {

    @Id
    private String jobId;

    private int numFilesWritten;
}
