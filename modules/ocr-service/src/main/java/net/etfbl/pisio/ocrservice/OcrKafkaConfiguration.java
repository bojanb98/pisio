package net.etfbl.pisio.ocrservice;

import net.etfbl.pisio.kafkaconfiguration.model.ImageJobData;
import net.etfbl.pisio.kafkaconfiguration.model.StringJobData;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
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

import java.util.Map;

@Configuration
public class OcrKafkaConfiguration {

    private final Map<String, Object> consumerConfigs;
    private final Map<String, Object> producerConfigs;

    public OcrKafkaConfiguration(@Qualifier("kafkaConsumerConfigs") Map<String, Object> consumerConfigs,
                                 @Qualifier("kafkaProducerConfigs") Map<String, Object> producerConfigs) {
        this.consumerConfigs = consumerConfigs;
        this.producerConfigs = producerConfigs;
    }

    @Bean
    public ConsumerFactory<String, ImageJobData> imageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs,
                new StringDeserializer(),
                new JsonDeserializer<>(ImageJobData.class));
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ImageJobData>>
    kafkaImageListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String , ImageJobData> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(imageConsumerFactory());
        return factory;
    }

    @Bean
    public KafkaTemplate<String, StringJobData> kafkaStringJobTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs));
    }
}
