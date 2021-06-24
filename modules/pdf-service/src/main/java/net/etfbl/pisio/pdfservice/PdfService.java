package net.etfbl.pisio.pdfservice;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] createPdfFromStrings(List<String> stringList) throws DocumentException {
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new ByteArrayOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        for (String imageText : stringList) {
            Chunk chunk = new Chunk(imageText, font);
            document.add(chunk);
        }
        document.close();
        return pdfWriter.getDirectContentUnder().getInternalBuffer().getBuffer();
    }
}
