package net.etfbl.pisio.pdfservice;

import org.springframework.context.annotation.Configuration;

@Configuration
public class PdfKafkaConfiguration {

//    @Bean
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, StringJobData>> kafkaStringListenerContainerFactory(KafkaConsumerConfig kafkaConsumerConfig) {
//        ConcurrentKafkaListenerContainerFactory<String, StringJobData> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        ConsumerFactory<String, StringJobData> consumerFactory = new DefaultKafkaConsumerFactory<>(
//                kafkaConsumerConfig.getConfigMap(),
//                new StringDeserializer(),
//                new JsonDeserializer<>(StringJobData.class));
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
//
//    @Bean
//    public KafkaTemplate<String, FileWriteData> kafkaFileWriteTemplate(KafkaProducerConfig kafkaProducerConfig) {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProducerConfig.getConfigMap()));
//    }
}
