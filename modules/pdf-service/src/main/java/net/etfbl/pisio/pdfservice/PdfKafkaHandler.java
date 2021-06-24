package net.etfbl.pisio.pdfservice;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import net.etfbl.pisio.kafkaconfiguration.model.FileWriteData;
import net.etfbl.pisio.kafkaconfiguration.model.StringJobData;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@AllArgsConstructor
@EnableKafka
public class PdfKafkaHandler {

    private final PdfService pdfService;
    private final KafkaTemplate<String, FileWriteData> kafkaTemplate;

    @KafkaListener(topics = "pdf", groupId = "pdf_creator")
    public void handleString(StringJobData stringJobData) throws DocumentException {
        byte[] pdfBytes = pdfService.createPdfFromStrings(stringJobData.getImagesText());
        FileWriteData fileWriteData = new FileWriteData(stringJobData.getJobId(), "images.pdf", pdfBytes);
        kafkaTemplate.send("destFile", fileWriteData);
    }
}
