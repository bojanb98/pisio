package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StringJobData {

    private String jobId;

    private List<String> imagesText;
}
