package net.etfbl.pisio.fileservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "pisio:write:status", timeToLive = 60 * 60)
public class FileWriteStatus {

    @Id
    private String jobId;

    private int numFilesWritten;
}
