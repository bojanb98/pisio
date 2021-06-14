package net.etfbl.pisio.fileservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "files")
public class FileProperties {

    @NotNull
    private Path path;
}
