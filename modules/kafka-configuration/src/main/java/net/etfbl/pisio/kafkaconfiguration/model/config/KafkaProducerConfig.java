package net.etfbl.pisio.kafkaconfiguration.model.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class KafkaProducerConfig {

    private final Map<String, Object> configMap;
}
