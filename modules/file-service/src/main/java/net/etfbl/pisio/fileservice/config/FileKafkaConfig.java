package net.etfbl.pisio.fileservice.config;

import net.etfbl.pisio.kafkaconfiguration.model.FileWriteData;
import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import net.etfbl.pisio.kafkaconfiguration.model.config.KafkaConsumerConfig;
import net.etfbl.pisio.kafkaconfiguration.model.config.KafkaProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class FileKafkaConfig {

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, FileWriteData>> kafkaFileWriteListenerContainerFactory(KafkaConsumerConfig kafkaConsumerConfig) {
        ConcurrentKafkaListenerContainerFactory<String, FileWriteData> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, FileWriteData> consumerFactory = new DefaultKafkaConsumerFactory<>(
                kafkaConsumerConfig.getConfigMap(),
                new StringDeserializer(),
                new JsonDeserializer<>(FileWriteData.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public KafkaTemplate<String, ImageJobData> kafkaImageJobTemplate(KafkaProducerConfig kafkaProducerConfig) {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProducerConfig.getConfigMap()));
    }
}
