package net.etfbl.pisio.communicationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.etfbl.pisio.kafkaconfiguration.model.JobStatusData;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@EnableKafka
@Configuration
@AllArgsConstructor
@Slf4j
public class CommunicationKafkaHandler {

    private final UsernameSessionService usernameSessionService;
    private final UserJobService userJobService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "jobStatus", groupId = "socket")
    public void handleJobStatusMessage(JobStatusData jobStatusData) {
        UserJob userJob = userJobService.getByJobId(jobStatusData.getJobId());
        if (userJob != null) {
            String username = userJob.getUsername();
            WebSocketSession socketSession = usernameSessionService.getUserSession(username);
            if (socketSession != null && socketSession.isOpen()) {
                try {
                    socketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(jobStatusData)));
                } catch (IOException ex) {
                    log.warn("Error during sending a websocket message", ex);
                }
            }
        }
    }


    @Bean
    public NewTopic fileTopic() {
        return TopicBuilder
                .name("jobStatus")
                .build();
    }
}
