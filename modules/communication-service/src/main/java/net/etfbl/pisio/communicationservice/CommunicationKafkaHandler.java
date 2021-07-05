package net.etfbl.pisio.communicationservice;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.JobStatusData;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
@AllArgsConstructor
public class CommunicationKafkaHandler {

    private final UsernameSessionService usernameSessionService;

    @KafkaListener(topics = "jobStatus", groupId = "socket")
    public void handleJobStatusMessage(JobStatusData jobStatusData) {
        System.out.println(jobStatusData);
    }


    @Bean
    public NewTopic fileTopic() {
        return TopicBuilder
                .name("jobStatus")
                .build();
    }
}
