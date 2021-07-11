package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageJobData {

    private String jobId;

    private List<byte[]> imagesBytes;
}
