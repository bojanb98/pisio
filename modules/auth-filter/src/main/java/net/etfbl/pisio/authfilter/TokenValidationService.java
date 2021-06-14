package net.etfbl.pisio.authfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@AllArgsConstructor
public class TokenValidationService {

    private final RemoteProperties remoteProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public boolean isTokenValid(String token) throws IOException {
        TokenValidityRequest tokenValidityRequest = new TokenValidityRequest(token);
        HttpEntity<String> requestBody = new HttpEntity<>(objectMapper.writeValueAsString(tokenValidityRequest));

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(remoteProperties.getPath(), requestBody, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return false;
        }

        TokenValidityResponse validityResponse = objectMapper.readValue(responseEntity.getBody(), TokenValidityResponse.class);

        return validityResponse.isTokenValid();
    }
}
