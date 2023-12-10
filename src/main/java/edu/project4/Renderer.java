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
import java.util.Random;

public class Renderer {

    public static final int RES = 1080;

    public static final int FINDING_START_ITERATIONS = 20;

    private final Random random = new Random();

    public void render(int n, int eqCount, int it, FractalImage image) {

        double xMin = (double) -image.width() / RES;
        double xMax = (double) image.width() / RES;
        double yMin = (double) -image.height() / RES;
        double yMax = (double) image.height() / RES;

        for (int num = 0; num < n; num++) {
            double newX = random.nextDouble(xMax - xMin) + xMin;
            double newY = random.nextDouble(yMax - yMin) + yMin;
            Point newPoint = new Point(newX, newY);

            for (int step = -FINDING_START_ITERATIONS; step < it; step++) {
                Transformation trans = Transformations.getRandomTrans();
                Point nextPoint = trans.apply(newPoint);

                if (step >= 0 && nextPoint.x() > xMin && nextPoint.x() < xMax
                    && nextPoint.y() > yMin && nextPoint.y() < yMax) {

                    int x1 = image.width() - (int) Math.round(((xMax - nextPoint.x()) / (xMax - xMin)) * image.width());
                    int y1 =
                        image.height() - (int) Math.round(((yMax - nextPoint.y()) / (yMax - yMin)) * image.height());

                    if (x1 < image.width() && y1 < image.height()) {
                        Pixel pixel = image.getPixel(x1, y1);
                        Color newColor;
                        Color transColor = Transformations.getTransColors().get(trans);
                        if (pixel.getHitsCount() == 0) {
                            newColor = transColor.copy();
                        } else {
                            newColor = Color.of(
                                (pixel.getColor().getRed() + transColor.getRed()) / 2,
                                (pixel.getColor().getGreen() + transColor.getGreen()) / 2,
                                (pixel.getColor().getBlue() + transColor.getBlue()) / 2
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
//        Arrays.stream(image.data()).forEach(row -> System.out.println(Arrays.toString(row)));

    }

}
