package edu.project4.model;

import edu.project4.Constants;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("MagicNumber")
public enum Transformations {
    LINEAR_TRANS((it) -> {
        int kx = 4;
        int ky = 4;
        int bx = -2;
        int by = -2;
        Random localRandom = new Random();
        double newX = (localRandom.nextDouble() * kx + bx) * it.x() + localRandom.nextDouble();
        double newY = (localRandom.nextDouble() * ky + by) * it.y() + localRandom.nextDouble();
        return new DecartPoint(newX, newY);
    }),
    HORSESHOE_TRANS((it) -> {
        double x = it.x();
        double y = it.y();
        double r = Math.sqrt(x * x + y * y);
        return new DecartPoint((x - y) * (x + y) / r, 2 * x * y / r);
    }),
    HANDKERCHIEF_TRANS((it) -> {
        double x = it.x();
        double y = it.y();
        double r = Math.sqrt(x * x + y * y);
        double atan = Math.atan(x / y);
        return new DecartPoint(r * Math.sin(atan + r), r * Math.cos(atan - r));
    }),
    HYPERBOLIC_TRANS((it) -> {
        double x = it.x();
        double y = it.y();
        double r = Math.sqrt(x * x + y * y);
        double atan = Math.atan(x / y);
        return new DecartPoint(Math.sin(atan) / r, r * Math.cos(atan));
    }),
    DIAMOND_TRANS((it) -> {
        double x = it.x();
        double y = it.y();
        double r = Math.sqrt(x * x + y * y);
        double atan = Math.atan(x / y);
        return new DecartPoint(Math.sin(atan) * Math.cos(r), Math.cos(atan) * Math.sin(r));
    }),
    SPHERE_TRANS((it) -> {
        double x = it.x();
        double y = it.y();
        double r2 = x * x + y * y;
        return new DecartPoint(x / r2, y / r2);
    }),
    POLAR_TRANS((it) -> {
        double x = it.x();
        double y = it.y();
        double newX = Math.atan(y / x) / Math.PI;
        double newY = Math.sqrt(x * x + y * y) - 1;
        return new DecartPoint(newX, newY);
    }),
    HEART_TRANS((it) -> {
        double x = it.x();
        double y = it.y();
        double r = Math.sqrt(x * x + y * y);
        double sqrt = Math.sqrt(r);
        double ratan = r * Math.atan(y / x);
        double newX = sqrt * Math.sin(ratan);
        double newY = -sqrt * Math.cos(ratan);
        return new DecartPoint(newX, newY);
    }),
    DISK_TRANS((it) -> {
        double x = it.x();
        double y = it.y();
        double k = Math.atan(y / x) / Math.PI;
        double pir = Math.PI * Math.sqrt(x * x + y * y);
        return new DecartPoint(k * Math.sin(pir), k * Math.cos(pir));
    });

    private static final Random RANDOM = new Random();
    private static final Map<Transformations, Color> TRANSFORMATION_TO_COLOR_MAP = new HashMap<>();

    private final Transformation trans;

    Transformations(Transformation trans) {
        this.trans = trans;
    }

    public Transformation getFunc() {
        return trans;
    }

    public static Transformations getRandomTrans() {
        int randomInd = RANDOM.nextInt(values().length);
        return Transformations.values()[randomInd];
    }

    public static List<Transformations> getListOfRandomTrans(int size) {
        List<Transformations> res = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            res.add(getRandomTrans());
        }
        return res;
    }

    public static Map<Transformations, Color> getTransColors() {
        if (TRANSFORMATION_TO_COLOR_MAP.isEmpty()) {
            Arrays.stream(Transformations.values())
                .forEach((trans) -> TRANSFORMATION_TO_COLOR_MAP.put(trans, new Color(
                    RANDOM.nextInt(Constants.MAX_COLOR_VALUE + 1),
                    RANDOM.nextInt(Constants.MAX_COLOR_VALUE + 1),
                    RANDOM.nextInt(Constants.MAX_COLOR_VALUE + 1)
                )));
        }
        return TRANSFORMATION_TO_COLOR_MAP;
    }

}
