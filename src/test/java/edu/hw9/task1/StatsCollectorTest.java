package edu.hw9.task1;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StatsCollectorTest {

    StatsCollector collector = new StatsCollector();

    @Test
    void pushTest() throws InterruptedException {
        int metricsSize = 32;
        int threadsCount = 16;

        try (ExecutorService service = Executors.newFixedThreadPool(threadsCount)) {
            Stream.generate(() -> CompletableFuture.runAsync(
                    () -> collector.push("myMetr", new double[] {0.1, 0.4, 0.9}), service
                ))
                .limit(metricsSize)
                .toArray();
        }
        collector.waitUntilPushEnds();

        List<StatsCollector.Stat> stats = collector.stats();

        assertThat(stats.size()).isEqualTo(32);
    }
}
