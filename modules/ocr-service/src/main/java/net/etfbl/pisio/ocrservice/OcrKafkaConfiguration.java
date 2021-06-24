package net.etfbl.pisio.ocrservice;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OcrKafkaConfiguration {

//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ImageJobData>> kafkaImageListenerContainerFactory(KafkaConsumerConfig kafkaConsumerConfig) {
//        System.out.println(kafkaConsumerConfig);
//        ConcurrentKafkaListenerContainerFactory<String, ImageJobData> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        ConsumerFactory<String, ImageJobData> consumerFactory = new DefaultKafkaConsumerFactory<>(
//                kafkaConsumerConfig.getConfigMap(),
//                new StringDeserializer(),
//                new JsonDeserializer<>(ImageJobData.class).ignoreTypeHeaders());
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
//
//    @Bean
//    public KafkaTemplate<String, StringJobData> kafkaStringJobTemplate(KafkaProducerConfig kafkaProducerConfig) {
//        System.out.println(kafkaProducerConfig);
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProducerConfig.getConfigMap()));
//    }
}
