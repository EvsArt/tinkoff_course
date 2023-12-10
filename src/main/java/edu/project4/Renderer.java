package edu.project4;

import edu.project4.model.Color;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Transformation;
import edu.project4.model.Transformations;
import edu.project4.utils.ImageUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.round;

public class Renderer {

    private final Random random = new Random();

    public void render(int n, int eqCount, int it, FractalImage image) {

        double XMin = -1.777;
        double XMax = 1.777;
        double YMin = -1;
        double YMax = 1;

        for (int num = 0; num < n; num++) {
            double newX = random.nextDouble(XMax - XMin) + XMin;
            double newY = random.nextDouble(YMax - YMin) + YMin;
            Point newPoint = new Point(newX, newY);

            for (int step = -20; step < it; step++) {
                Transformation trans = Transformations.getRandomTrans();
                Point nextPoint = trans.apply(newPoint);

                if (step >= 0 && nextPoint.x() > XMin && nextPoint.x() < XMax
                    && nextPoint.y() > YMin && nextPoint.y() < YMax) {

                    int x1 = image.width() - (int) Math.round(((XMax - nextPoint.x()) / (XMax - XMin)) * image.width());
                    int y1 = image.height() - (int) Math.round(((YMax - nextPoint.y()) / (YMax - YMin)) * image.height());

                    if(x1 < image.width() && y1 < image.height()) {
                        Pixel pixel = image.getPixel(x1, y1);
                        Color newColor;
                        Color transColor = Transformations.getTransColors().get(trans);
                        if(pixel.getHitsCount() == 0) {
                            newColor = transColor.clone();
                        } else {
                            newColor = Color.of(
                                (pixel.getColor().getRed() + transColor.getRed())/2,
                                (pixel.getColor().getGreen() + transColor.getGreen())/2,
                                (pixel.getColor().getBlue() + transColor.getBlue())/2
                            );
                        }
                        pixel.setColor(newColor);
                        pixel.incHitsCount();
                    }

                }

            }

        }
        try {
            ImageUtils.save(image, Path.of("./img.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Arrays.stream(image.data()).forEach(row -> System.out.println(Arrays.toString(row)));

    }

}
