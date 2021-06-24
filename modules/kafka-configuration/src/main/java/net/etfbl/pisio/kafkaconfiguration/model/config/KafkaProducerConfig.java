package net.etfbl.pisio.kafkaconfiguration.model.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class KafkaProducerConfig {

    private final Map<String, Object> configMap;
}
