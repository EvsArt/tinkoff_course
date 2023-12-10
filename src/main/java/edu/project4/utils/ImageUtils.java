package edu.project4.utils;

import edu.project4.model.FractalImage;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static void save(FractalImage data, Path path) throws IOException {
        BufferedImage image = new BufferedImage(data.width(), data.height(), BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < data.height(); i++) {
            for (int j = 0; j < data.width(); j++) {
                edu.project4.model.Color color;
                if(data.isNull(j, i)) {
                    color = edu.project4.model.Color.of(0,0,0);
                } else {
                    color = data.getPixel(j, i).getColor();
                }
                Color awtColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getBrightness());
                image.setRGB(j, i, awtColor.getRGB());
                ImageIO.write(image, "png", path.toFile());
            }
        }
    }
}
