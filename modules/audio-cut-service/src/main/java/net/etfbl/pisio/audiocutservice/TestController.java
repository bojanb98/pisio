package net.etfbl.pisio.audiocutservice;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class TestController {

    private final KafkaTemplate<String, String> kafkaTemplate;


    @GetMapping
    public void test() {
        kafkaTemplate.send("mcut", "test1234");
    }
}
