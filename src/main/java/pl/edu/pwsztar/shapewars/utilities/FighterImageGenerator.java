package pl.edu.pwsztar.shapewars.utilities;

import pl.edu.pwsztar.shapewars.entities.ColorMap;
import pl.edu.pwsztar.shapewars.entities.Shape;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FighterImageGenerator {

    public static byte[] generateImageFrom(Shape shape, ColorMap colorMap){
        String imageString = "";
        BufferedImage image = createImageFromBytes(decodePNGData(shape.getImage()));
        BufferedImage overlay = createImageFromBytes(decodePNGData(colorMap.getColorMap()));
// create the new image, canvas size is the max. of both image sizes
        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

// paint both images, preserving the alpha channels
        Graphics2D g = (Graphics2D)combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
        g.drawImage(overlay, 0, 0, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(combined, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            imageString = new String(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(imageInByte));
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return ("data:image/png;base64,"+imageString).getBytes();
    }

    private static BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static byte[] decodePNGData(byte[] encodedPNGImageData){
        String data = new String(encodedPNGImageData);
        String header = "data:image/png;base64";
        String encodedImage = data.substring(header.length()+1); //+1 to include comma
        return Base64.getDecoder().decode(encodedImage);
    }
}
