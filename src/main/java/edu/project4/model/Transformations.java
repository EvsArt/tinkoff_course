package edu.project4.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class Transformations {

    private Transformations() {
    }

    public static final int MAX_COLOR_VALUE = 255;

    private static final Map<Transformation, Color> TRANSFORMATION_TO_COLOR_MAP = new HashMap<>();

    private static final Transformation SIN_TRANS = (it) -> new Point(Math.sin(it.x()), Math.sin(it.y()));
    private static final Transformation SPHERE_TRANS = (it) -> {
        double x = it.x();
        double y = it.y();
        double r2 = x * x + y * y;
        return new Point(x / r2, y / r2);
    };

    private static final Transformation POLAR_TRANS = (it) -> {
        double x = it.x();
        double y = it.y();
        double newX = Math.atan(y / x) / Math.PI;
        double newY = Math.sqrt(x * x + y * y) - 1;
        return new Point(newX, newY);
    };

    private static final Transformation HEART_TRANS = (it) -> {
        double x = it.x();
        double y = it.y();
        double r = Math.sqrt(x * x + y * y);
        double sqrt = Math.sqrt(r);
        double ratan = r * Math.atan(y / x);
        double newX = sqrt * Math.sin(ratan);
        double newY = -sqrt * Math.cos(ratan);
        return new Point(newX, newY);
    };

    private static final Transformation DISK_TRANS = (it) -> {
        double x = it.x();
        double y = it.y();
        double k = Math.atan(y / x) / Math.PI;
        double pir = Math.PI * Math.sqrt(x * x + y * y);
        return new Point(k * Math.sin(pir), k * Math.cos(pir));
    };

    public static List<Transformation> getTransList = List.of(
        SIN_TRANS,
        SPHERE_TRANS,
        POLAR_TRANS,
        HEART_TRANS,
        DISK_TRANS
    );

    public static Transformation getRandomTrans() {
        Random random = new Random();
        return getTransList.get(random.nextInt(getTransList.size()));
    }

    public static Map<Transformation, Color> getTransColors() {
        if (TRANSFORMATION_TO_COLOR_MAP.isEmpty()) {
            TRANSFORMATION_TO_COLOR_MAP.put(SIN_TRANS, Color.of(MAX_COLOR_VALUE, MAX_COLOR_VALUE, MAX_COLOR_VALUE));
            TRANSFORMATION_TO_COLOR_MAP.put(SPHERE_TRANS, Color.of(0, MAX_COLOR_VALUE, 0));
            TRANSFORMATION_TO_COLOR_MAP.put(POLAR_TRANS, Color.of(0, 0, MAX_COLOR_VALUE));
            TRANSFORMATION_TO_COLOR_MAP.put(HEART_TRANS, Color.of(MAX_COLOR_VALUE, 0, 0));
            TRANSFORMATION_TO_COLOR_MAP.put(DISK_TRANS, Color.of(MAX_COLOR_VALUE, 0, MAX_COLOR_VALUE));
        }
        return TRANSFORMATION_TO_COLOR_MAP;
    }

}
