package edu.project4.utils;

import edu.project4.model.FractalImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static void multiThreadSave(FractalImage data, Path path, int threadsCount) throws IOException {
        BufferedImage image = new BufferedImage(data.width(), data.height(), BufferedImage.TYPE_4BYTE_ABGR);

        ExecutorService threadPool = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < data.height(); i++) {
            int finalI = i;
            threadPool.execute(() -> {
                for (int j = 0; j < data.width(); j++) {
                    Color color;
                    if (data.isNull(j, finalI)) {
                        color = new Color(0, 0, 0);
                    } else {
                        color = data.getPixel(j, finalI).getColor();
                    }
                    image.setRGB(j, finalI, color.getRGB());
                }
            });
        }
        threadPool.close();
        String fileName = path.getFileName().toString();
        ImageIO.write(image, fileName.substring(fileName.lastIndexOf(".") + 1), path.toFile());
    }

    public static void save(FractalImage data, Path path, int threadsCount) throws IOException {
        BufferedImage image = new BufferedImage(data.width(), data.height(), BufferedImage.TYPE_4BYTE_ABGR);

        for (int i = 0; i < data.height(); i++) {
            for (int j = 0; j < data.width(); j++) {
                Color color;
                if (data.isNull(j, i)) {
                    color = new Color(0, 0, 0);
                } else {
                    color = data.getPixel(j, i).getColor();
                }
                image.setRGB(j, i, color.getRGB());
            }
        }
        String fileName = path.getFileName().toString();
        ImageIO.write(image, fileName.substring(fileName.lastIndexOf(".") + 1), path.toFile());
    }
}
