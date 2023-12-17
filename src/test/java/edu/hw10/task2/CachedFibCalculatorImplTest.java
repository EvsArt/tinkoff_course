package edu.hw10.task2;

import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CachedFibCalculatorImplTest {

    @BeforeEach
    void clearEach() throws IOException {
        clear();
    }

    @AfterAll
    static void clear() throws IOException {
        Files.list(Constants.STORAGE_PATH).forEach(it -> {
            try {
                Files.delete(it);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void persistCacheTest() {
        // given
        CachedFibCalculator cachedFibCalculator = new FibCalculatorImpl();
        CachedFibCalculator proxy = CacheProxy.create(cachedFibCalculator);

        // when
        long time1 = System.nanoTime();
        proxy.fib(35);
        proxy.fib(36);
        time1 = System.nanoTime() - time1;

        long time2 = System.nanoTime();
        proxy.fib(37);
        proxy.fib(38);
        proxy.fib(39);
        proxy.fib(40);
        time2 = System.nanoTime() - time2;

        // then
        assertThat(time1).isGreaterThan(time2);

    }
}
