package net.etfbl.pisio.pdfservice;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] createPdfFromStrings(List<String> content) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, bos);
        document.open();
        this.addContent(document, content);
        document.close();
        return bos.toByteArray();
    }

    private void addContent(Document document, List<String> content) throws DocumentException {
        Font chapterFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
        Font textFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
        int i = 0;
        for (String imageText : content) {
            Chapter chapter = createNewChapter(++i, chapterFont);
            String[] textParts = imageText.split("\n");
            for (String part : textParts) {
                Paragraph paragraph = new Paragraph(part, textFont);
                chapter.add(paragraph);
            }
            document.add(chapter);
        }
    }

    private Chapter createNewChapter(int chapterNum, Font chapterFont) {
        Anchor anchor = new Anchor("Image", chapterFont);
        anchor.setName("Image " + chapterNum);
        return new Chapter(new Paragraph(anchor), chapterNum);
    }
}
