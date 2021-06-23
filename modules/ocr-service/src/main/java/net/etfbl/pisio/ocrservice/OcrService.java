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

    private static final String OCR_PARAMS_PATH = "src/main/resources/eng.traineddata";

    private final Tesseract tesseract;

    public OcrService() {
        tesseract = new Tesseract();
        tesseract.setDatapath(OCR_PARAMS_PATH);
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
    }

    public String doOcr(List<byte[]> images) {
        return images.stream()
                .map(this::doSingleImageOcr)
                .collect(Collectors.joining("\n"));
    }

    private String doSingleImageOcr(byte[] imageBytes) {
        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes)) {
            return tesseract.doOCR(ImageIO.read(inputStream));
        } catch (IOException | TesseractException ex) {
            throw new OcrProcessingException(ex.getMessage(), ex);
        }
    }
}
