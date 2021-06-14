package net.etfbl.pisio.fileservice.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileJob {

    @NonNull
    @NotBlank
    private String jobId;
}
