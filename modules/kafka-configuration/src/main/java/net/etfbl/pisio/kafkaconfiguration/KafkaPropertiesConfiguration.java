package net.etfbl.pisio.kafkaconfiguration;

import net.etfbl.pisio.kafkaconfiguration.model.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class KafkaPropertiesConfiguration {

    @Bean
    @Profile("docker")
    public KafkaProperties kubernetesKafkaProperties() {
        return new KafkaProperties("my-cluster-kafka-bootstrap.kafka:9092");
    }

    @Bean
    @Profile("default")
    public KafkaProperties localKafkaProperties() {
        return new KafkaProperties("pop-os.localdomain:9092");
    }
}
