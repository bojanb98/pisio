package net.etfbl.pisio.fileservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class FileKafkaConfiguration {

//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, FileWriteData>> kafkaFileWriteListenerContainerFactory(KafkaConsumerConfig kafkaConsumerConfig) {
//        ConcurrentKafkaListenerContainerFactory<String, FileWriteData> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        ConsumerFactory<String, FileWriteData> consumerFactory = new DefaultKafkaConsumerFactory<>(
//                kafkaConsumerConfig.getConfigMap(),
//                new StringDeserializer(),
//                new JsonDeserializer<>(FileWriteData.class));
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
//
//    @Bean
//    public KafkaTemplate<String, ImageJobData> kafkaImageJobTemplate(KafkaProducerConfig kafkaProducerConfig) {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProducerConfig.getConfigMap()));
//    }
}
