package edu.hw8.task2;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FixedThreadPoolTest {

    @Test
    void testMoreThreadsThanPoolHas() {

        FixedThreadPool pool = FixedThreadPool.create(4);

        for (int i = 0; i < 8; i++) {
            var k = i;
            pool.execute(() -> System.out.println(getFibonacci(k)));
        }

        assertDoesNotThrow(pool::start);

    }

    @Test
    void testLessThreadsThanPoolHas() {

        FixedThreadPool pool = FixedThreadPool.create(10);

        for (int i = 0; i < 8; i++) {
            var k = i;
            pool.execute(() -> System.out.println(getFibonacci(k)));
        }

        assertDoesNotThrow(pool::start);

    }

    @Test
    void testMultiThreadsWorksFaster() {

        int threadsCount = 16;

        FixedThreadPool multiPool = FixedThreadPool.create(threadsCount);
        FixedThreadPool oneThreadPool = FixedThreadPool.create(1);

        for (int i = 0; i < threadsCount; i++) {
            var k = i;
            multiPool.execute(() -> System.out.println(getFibonacci(k)));
            oneThreadPool.execute(() -> System.out.println(getFibonacci(k)));
        }

        long time1 = System.nanoTime();
        multiPool.start();
        time1 = System.nanoTime() - time1;

        long time2 = System.nanoTime();
        oneThreadPool.start();
        time2 = System.nanoTime() - time2;

        assertThat(time1).isLessThan(time2);
    }

    private long getFibonacci(int num) {
        if (num < 1) {
            return 0;
        }

        long[] dp = new long[num + 1];
        dp[0] = 0;
        if (num > 1) {
            dp[1] = 1;
        }

        for (int i = 2; i <= num; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[num];

    }

}
