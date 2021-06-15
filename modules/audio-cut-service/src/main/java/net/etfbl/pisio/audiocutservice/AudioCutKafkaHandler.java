package net.etfbl.pisio.audiocutservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class AudioCutKafkaHandler {

    @KafkaListener(topics = "mcut", groupId = "group_id")
    public void listen(String in) {
        System.out.println(in);
    }
}
