package net.etfbl.pisio.authfilter;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RemoteConfiguration {

    @Bean
    @Profile("default")
    public RemoteProperties getDefaultRemote() {
        String remotePath = "http://localhost:10000/auth";
        return new RemoteProperties(remotePath);
    }

    @Bean
    @Profile("docker")
    public RemoteProperties dockerRemote() {
        String remotePath = "http://auth-service:10000/auth";
        return new RemoteProperties(remotePath);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
        return templateBuilder
                .setConnectTimeout(Duration.ofMinutes(5))
                .setReadTimeout(Duration.ofMinutes(5))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
