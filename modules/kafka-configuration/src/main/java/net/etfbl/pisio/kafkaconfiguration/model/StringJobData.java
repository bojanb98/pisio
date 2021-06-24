package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class StringJobData {

    private String jobId;

    private List<String> imagesText;
}
