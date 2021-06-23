package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ImageJobData {

    private String jobId;

    private byte[] fileBytes;
}
