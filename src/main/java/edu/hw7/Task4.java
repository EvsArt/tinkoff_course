package edu.hw7;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task4 {

    private long totalCount = 0;
    private final AtomicLong circleCount = new AtomicLong(0);
    CountDownLatch count;

    public double getPi(Long simulationsCount, int threadCount) {
        circleCount.set(0);
        circleCount.addAndGet(pointsInCircleOfNPoints(simulationsCount % threadCount));
        totalCount = simulationsCount;

        count = new CountDownLatch(threadCount);
        Runnable runnable = () -> {
            long pointsInCircle = pointsInCircleOfNPoints(simulationsCount / threadCount);
            circleCount.addAndGet(pointsInCircle);
            count.countDown();
        };

        List<Thread> threads = Stream.generate(() -> new Thread(runnable))
            .limit(threadCount)
            .toList();

        threads.forEach(Thread::start);
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return calculatePi();
    }

    @SuppressWarnings("MagicNumber")
    private double calculatePi() {
        return 4.0 * circleCount.get() / totalCount;
    }

    private long pointsInCircleOfNPoints(long n) {
        long pointsInCircle = 0;
        for (int i = 0; i < n; i++) {
            if (isRandomPointInCircle()) {
                pointsInCircle++;
            }
        }
        return pointsInCircle;
    }

    private boolean isRandomPointInCircle() {
        return isPointInCircle(getRandomPointInSquare());
    }

    // circle: (x)^2 + (y)^2 = 1
    private boolean isPointInCircle(Point point) {
        return point.x * point.x + point.y * point.y < 1;
    }

    // square: x = +-1; y = +-1]
    private Point getRandomPointInSquare() {
        Random random = ThreadLocalRandom.current();
        return new Point(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1);
    }

    private record Point(double x, double y) {
    }

}
