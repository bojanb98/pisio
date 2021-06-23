package net.etfbl.pisio.kafkaconfiguration;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.KafkaProperties;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class KafkaAdminAutoConfiguration {

    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getKafkaUrl());
        return new KafkaAdmin(configs);
    }
}
