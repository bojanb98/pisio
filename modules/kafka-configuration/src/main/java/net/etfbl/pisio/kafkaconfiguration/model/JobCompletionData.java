package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class JobCompletionData {

    private String jobId;

    private JobPart completedPart;

    public enum JobPart {
        OCR, PDF, GIF
    }
}
