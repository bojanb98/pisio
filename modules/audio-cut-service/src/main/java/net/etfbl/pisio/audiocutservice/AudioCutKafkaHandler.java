package net.etfbl.pisio.audiocutservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class AudioCutKafkaHandler {

    @KafkaListener(topics = "mcut")
    public void listen(String in) {
        System.out.println(in);

    }
}
