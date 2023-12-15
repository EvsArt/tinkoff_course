package edu.project4.utils;

import edu.project4.Constants;
import edu.project4.model.DecartPoint;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.PolarPoint;
import edu.project4.model.Transformations;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Renderer {

    public static final int FINDING_START_ITERATIONS = 20;

    private final Random random = new Random();

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
            double newX = random.nextDouble(xMax - xMin) + xMin;
            double newY = random.nextDouble(yMax - yMin) + yMin;
            DecartPoint newPoint = new DecartPoint(newX, newY);

            for (int step = -FINDING_START_ITERATIONS; step < it; step++) {
                Transformations trans = randomTrans.get(random.nextInt(eqCount));
                newPoint = trans.getFunc().apply(newPoint);

                if (step >= 0 && newPoint.x() > xMin && newPoint.x() < xMax
                    && newPoint.y() > yMin && newPoint.y() < yMax
                    && decartPointToPolarPoint(newPoint).angle() < 2 * PI / symmetricValue) {

                    List<DecartPoint> points = getAllSymmetricPoints(newPoint, symmetricValue);
                    for (DecartPoint symPoint : points) {

                        int x1 =
                            image.width() - (int) Math.round(((xMax - symPoint.x()) / (xMax - xMin)) * image.width());
                        int y1 =
                            image.height() - (int) Math.round(((yMax - symPoint.y()) / (yMax - yMin)) * image.height());

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
        }
    }

    protected PolarPoint decartPointToPolarPoint(DecartPoint point) {
        double angle = Math.atan(point.y() / point.x());
        if (point.y() < 0) {
            angle += PI;
        }
        double length = sqrt(point.x() * point.x() + point.y() * point.y());
        return new PolarPoint(angle, length);
    }

    protected DecartPoint polarPointToDecartPoint(PolarPoint polarPoint) {
        double y = polarPoint.length() * sin(polarPoint.angle());
        double x = polarPoint.length() * cos(polarPoint.angle());
        return new DecartPoint(x, y);
    }

    protected PolarPoint reflectOfRadianAngle(PolarPoint point, double angle) {
        return new PolarPoint(2 * angle - point.angle(), point.length());
    }

    protected List<DecartPoint> getAllSymmetricPoints(DecartPoint point, int symmetricValue) {
        if (symmetricValue == 1) {
            return List.of(point);
        }
        List<DecartPoint> points = new ArrayList<>(symmetricValue);

        double polarAreaAngle = 2 * PI / symmetricValue;
        PolarPoint polarPoint = decartPointToPolarPoint(point);

        double angleInTheFirstTwoSectors = polarPoint.angle() % 2 * polarAreaAngle;
        // points angles for the first two sectors
        double point1Angle = new PolarPoint(angleInTheFirstTwoSectors, polarPoint.length()).angle();
        double point2Angle =
            reflectOfRadianAngle(
                new PolarPoint(angleInTheFirstTwoSectors, polarPoint.length()),
                polarAreaAngle
            ).angle();

        while (point1Angle < 2 * PI || point2Angle < 2 * PI) {
            points.add(polarPointToDecartPoint(new PolarPoint(point1Angle, polarPoint.length())));
            points.add(polarPointToDecartPoint(new PolarPoint(point2Angle, polarPoint.length())));
            point1Angle += polarAreaAngle;
            point2Angle += polarAreaAngle;
        }

        return points;

    }

    public void correction(FractalImage image) {
        double gamma = Constants.GAMMA_CORRECTION;

        double max = 0;
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.getPixel(x, y);
                if (pixel.getHitsCount() != 0) {
                    pixel.setNormal(log10(pixel.getHitsCount()));
                    if (pixel.getNormal() > max) {
                        max = pixel.getNormal();
                    }
                }
            }
        }

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.getPixel(x, y);
                pixel.setNormal(pixel.getNormal() / max);
                pixel.setColor(new Color(
                    (int) (pixel.getColor().getRed() * pow(pixel.getNormal(), (1.0 / gamma))),
                    (int) (pixel.getColor().getGreen() * pow(pixel.getNormal(), (1.0 / gamma))),
                    (int) (pixel.getColor().getBlue() * pow(pixel.getNormal(), (1.0 / gamma)))
                ));
            }
        }

    }

}
