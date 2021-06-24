package net.etfbl.pisio.ocrservice;

import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import net.etfbl.pisio.kafkaconfiguration.model.StringJobData;
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
public class OcrKafkaConfiguration {

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ImageJobData>> kafkaImageListenerContainerFactory(KafkaConsumerConfig kafkaConsumerConfig) {
        ConcurrentKafkaListenerContainerFactory<String, ImageJobData> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, ImageJobData> consumerFactory = new DefaultKafkaConsumerFactory<>(
                kafkaConsumerConfig.getConfigMap(),
                new StringDeserializer(),
                new JsonDeserializer<>(ImageJobData.class));
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public KafkaTemplate<String, StringJobData> kafkaStringJobTemplate(KafkaProducerConfig kafkaProducerConfig) {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProducerConfig.getConfigMap()));
    }
}
