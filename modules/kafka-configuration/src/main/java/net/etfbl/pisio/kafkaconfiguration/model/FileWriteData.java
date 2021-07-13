package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileWriteData {

    private String jobId;

    private String fileName;

    private byte[] data;

    private JobPart jobPart;

    public enum JobPart {
        PDF, GIF
    }
}
