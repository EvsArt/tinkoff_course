package edu.hw9.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

public class StatsCollector {

    private static final int OPERATION_COUNT = 4;
    private static final int WAITING_PUSH_END_TIME_SECONDS = 10;

    private final List<Stat> stats = new ArrayList<>();
    private final ReadWriteLock statsLock = new ReentrantReadWriteLock(true);

    // ThreadPool for pushing data
    private final ExecutorService pushService = Executors.newFixedThreadPool(16);

    public void push(String metricName, double[] data) {
        CompletableFuture.runAsync(() -> {
            ExecutorService service = Executors.newFixedThreadPool(OPERATION_COUNT);
            CompletableFuture<Double>[] futures = Stream.builder()
                .add(CompletableFuture.supplyAsync(() -> getSum(data), service))
                .add(CompletableFuture.supplyAsync(() -> getAverage(data), service))
                .add(CompletableFuture.supplyAsync(() -> getMax(data), service))
                .add(CompletableFuture.supplyAsync(() -> getMin(data), service))
                .build()
                .map((it) -> (CompletableFuture<Double>) it)
                .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).join();
            var results = Arrays.stream(futures)
                .map(it -> {
                    try {
                        return it.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .mapToDouble(d -> d)
                .toArray();

            statsLock.writeLock().lock();
            try {
                stats.add(Stat.getStat(metricName, results));
            } finally {
                statsLock.writeLock().unlock();
            }

        }, pushService);
    }

    public void waitUntilPushEnds() throws InterruptedException {
        pushService.shutdown();
        pushService.awaitTermination(WAITING_PUSH_END_TIME_SECONDS, TimeUnit.SECONDS);
    }

    public List<Stat> stats() {
        statsLock.readLock().lock();
        try {
            return stats;
        } finally {
            statsLock.readLock().unlock();
        }
    }

    private double getSum(double[] data) {
        return Arrays.stream(data).sum();
    }

    private double getAverage(double[] data) {
        return Arrays.stream(data).average().orElse(Double.NaN);
    }

    private double getMax(double[] data) {
        return Arrays.stream(data).max().orElse(Double.NaN);
    }

    private double getMin(double[] data) {
        return Arrays.stream(data).min().orElse(Double.NaN);
    }

    record Stat(
        String metricName,
        double sum,
        double average,
        double max,
        double min
    ) {

        @SuppressWarnings("MagicNumber")
        public static Stat getStat(String metric, double[] data) {
            if (data.length != OPERATION_COUNT) {
                return null;
            }
            return new Stat(metric, data[0], data[1], data[2], data[3]);
        }

    }

}
