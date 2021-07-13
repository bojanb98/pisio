package net.etfbl.pisio.ocrservice;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OcrService {

    private final Tesseract tesseract;

    public OcrService(TesseractProperties tesseractProperties) {
        tesseract = new Tesseract();
        tesseract.setDatapath(tesseractProperties.getDatapath());
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
    }

    public List<String> doOcr(List<byte[]> images) {
        return images.stream()
                .map(this::doSingleImageOcr)
                .collect(Collectors.toList());
    }

    private String doSingleImageOcr(byte[] imageBytes) {
        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes)) {
            return tesseract.doOCR(ImageIO.read(inputStream));
        } catch (IOException | TesseractException ex) {
            throw new OcrProcessingException(ex.getMessage(), ex);
        }
    }
}
