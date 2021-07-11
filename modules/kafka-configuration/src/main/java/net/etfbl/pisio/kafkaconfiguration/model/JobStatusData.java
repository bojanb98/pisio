package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobStatusData {

    private String jobId;

    private JobPart jobPart;

    private PartStatus partStatus;

    public enum JobPart {
        OCR, PDF, GIF
    }

    public enum PartStatus {
        IN_PROGRESS, DONE
    }
}
