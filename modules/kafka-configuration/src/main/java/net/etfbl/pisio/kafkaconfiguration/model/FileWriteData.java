package net.etfbl.pisio.kafkaconfiguration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class FileWriteData {

    private String jobId;

    private String fileName;

    private byte[] data;
}
