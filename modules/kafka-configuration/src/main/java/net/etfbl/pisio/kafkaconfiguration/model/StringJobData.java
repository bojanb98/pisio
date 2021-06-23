package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class StringJobData {

    private String jobId;

    private String[] imageTextArray;
}
