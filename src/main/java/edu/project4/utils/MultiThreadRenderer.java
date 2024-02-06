package edu.project4.utils;

import edu.project4.Constants;
import edu.project4.model.DecartPoint;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Transformations;
import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.PI;
import static java.lang.Math.log10;
import static java.lang.Math.pow;

public class MultiThreadRenderer extends Renderer {

    ExecutorService threadPool = Executors.newFixedThreadPool(Constants.THREAD_COUNT);

    @Override
    public void render(int n, int eqCount, int it, int symmetricValue, FractalImage image) {

        double xMin;
        double xMax;
        double yMin;
        double yMax;

        if (image.width() > image.height()) {
            xMin = (double) -image.width() / image.height();
            xMax = (double) image.width() / image.height();
            yMin = -1;
            yMax = 1;
        } else {
            yMin = (double) -image.height() / image.width();
            yMax = (double) image.height() / image.width();
            xMin = -1;
            xMax = 1;
        }

        List<Transformations> randomTrans = Transformations.getListOfRandomTrans(eqCount);
        for (int num = 0; num < n; num++) {
            threadPool.execute(() -> {
                double newX = ThreadLocalRandom.current().nextDouble(xMax - xMin) + xMin;
                double newY = ThreadLocalRandom.current().nextDouble(yMax - yMin) + yMin;
                DecartPoint newPoint = new DecartPoint(newX, newY);

                for (int step = -FINDING_START_ITERATIONS; step < it; step++) {
                    Transformations trans = randomTrans.get(ThreadLocalRandom.current().nextInt(eqCount));
                    newPoint = trans.getFunc().apply(newPoint);
                    if (step >= 0 && newPoint.x() > xMin && newPoint.x() < xMax
                        && newPoint.y() > yMin && newPoint.y() < yMax
                        && decartPointToPolarPoint(newPoint).angle() < 2 * PI / symmetricValue) {

                        List<DecartPoint> points = getAllSymmetricPoints(newPoint, symmetricValue);
                        for (DecartPoint symPoint : points) {
                            int x1 = image.width()
                                - (int) Math.round(((xMax - symPoint.x()) / (xMax - xMin)) * image.width());
                            int y1 = image.height()
                                - (int) Math.round(((yMax - symPoint.y()) / (yMax - yMin)) * image.height());

                            if (x1 > 0 && x1 < image.width() && y1 < image.height() && y1 > 0) {
                                Pixel pixel = image.getPixel(x1, y1);
                                Color newColor;
                                Color transColor = Transformations.getTransColors().get(trans);
                                if (pixel.getHitsCount() == 0) {
                                    newColor = new Color(transColor.getRGB());
                                } else {
                                    newColor = new Color(
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
            });
        }
        threadPool.close();
    }

    @Override
    public void correction(FractalImage image) {
        double gamma = Constants.GAMMA_CORRECTION;

        double max = Arrays.stream(image.data())
            .parallel()
            .map(this::getMaxLogNormalFromPixelArray)
            .max(Comparator.naturalOrder()).get();

        Arrays.stream(image.data())
            .parallel()
            .forEach(row -> Arrays.stream(row)
                .filter(Objects::nonNull)
                .forEach(pixel -> {
                    pixel.setNormal(pixel.getNormal() / max);
                    pixel.setColor(new Color(
                        (int) (pixel.getColor().getRed() * pow(pixel.getNormal(), (1.0 / gamma))),
                        (int) (pixel.getColor().getGreen() * pow(pixel.getNormal(), (1.0 / gamma))),
                        (int) (pixel.getColor().getBlue() * pow(pixel.getNormal(), (1.0 / gamma)))
                    ));
                }));

    }

    private double getMaxLogNormalFromPixelArray(Pixel[] arr) {
        return Arrays.stream(arr)
            .filter(Objects::nonNull)
            .filter(pixel -> pixel.getHitsCount() != 0)
            .map((pixel -> {
                pixel.setNormal(log10(pixel.getHitsCount()));
                return pixel.getNormal();
            }))
            .max(Comparator.naturalOrder()).orElse(0.0);
    }

}
