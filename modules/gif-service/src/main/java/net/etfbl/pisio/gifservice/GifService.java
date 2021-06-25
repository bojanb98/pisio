package net.etfbl.pisio.gifservice;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GifService {

    public byte[] createGif(List<byte[]> images) {
        AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
        gifEncoder.setDelay(1000);
        gifEncoder.setRepeat(0);
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            gifEncoder.start(bos);
            for (byte[] image : images) {
                gifEncoder.addFrame(ImageIO.read(new ByteArrayInputStream(image)));
            }
            boolean isSuccess = gifEncoder.finish();
            if (!isSuccess) {
                throw new GifProcessingException("Failed to create GIF");
            }
            return bos.toByteArray();
        } catch (IOException ex) {
            throw new GifProcessingException(ex.getMessage(), ex);
        }
    }

}
