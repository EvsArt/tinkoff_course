package edu.project4.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import static java.lang.Math.*;

public final class Transformations {

    private Transformations() {
    }

    private static final Map<Transformation, Color> transformationToColorMap = new HashMap<>();

    private static final Transformation sinTrans = (it) -> new Point(Math.sin(it.x()), Math.sin(it.y()));
    private static final Transformation sphereTrans = (it) -> {
        double x = it.x();
        double y = it.y();
        double r2 = x * x + y * y;
        return new Point(x / r2, y / r2);
    };

    private static final Transformation polarTrans = (it) -> {
        double x = it.x();
        double y = it.y();
        double newX = Math.atan(y / x) / Math.PI;
        double newY = Math.sqrt(x * x + y * y) - 1;
        return new Point(newX, newY);
    };

    private static final Transformation heartTrans = (it) -> {
        double x = it.x();
        double y = it.y();
        double r = Math.sqrt(x*x + y*y);
        double sqrt = Math.sqrt(r);
        double ratan = r * Math.atan(y/x);
        double newX = sqrt * Math.sin(ratan);
        double newY = -sqrt * Math.cos(ratan);
        return new Point(newX, newY);
    };

    private static final Transformation diskTrans = (it) -> {
        double x = it.x();
        double y = it.y();
        double k = Math.atan(y/x)/ Math.PI;
        double pir = Math.PI * Math.sqrt(x*x + y*y);
        return new Point(k * Math.sin(pir), k * Math.cos(pir));
    };

    public static List<Transformation> getTransList = List.of(
        sinTrans,
        sphereTrans,
        polarTrans,
        heartTrans,
        diskTrans
    );

    public static Transformation getRandomTrans() {
        Random random = new Random();
        return getTransList.get(random.nextInt(getTransList.size()));
    }

    public static Map<Transformation, Color> getTransColors() {
        if(transformationToColorMap.isEmpty()) {
            transformationToColorMap.put(sinTrans, Color.of(255, 255, 255));
            transformationToColorMap.put(sphereTrans, Color.of(0, 255, 0));
            transformationToColorMap.put(polarTrans, Color.of(0, 0, 255));
            transformationToColorMap.put(heartTrans, Color.of(255, 0, 0));
            transformationToColorMap.put(diskTrans, Color.of(255, 0, 255));
        }
        return transformationToColorMap;
    }

}
