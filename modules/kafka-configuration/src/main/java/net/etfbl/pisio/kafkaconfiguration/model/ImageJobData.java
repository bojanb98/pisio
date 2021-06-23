package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ImageJobData {

    private String jobId;

    private List<byte[]> imagesBytes;
}
