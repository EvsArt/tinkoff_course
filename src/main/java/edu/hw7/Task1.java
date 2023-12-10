package edu.hw7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task1 {

    AtomicLong counter = new AtomicLong(0);
    CountDownLatch countDownLatch;

    private final Consumer<Integer> incNTimes = (Integer n) -> {
        for (int i = 0; i < n; i++) {
            counter.incrementAndGet();
        }
    };

    public long addValueToCounterAtEachOfNThreads(int value, int threadsNum) {
        countDownLatch = new CountDownLatch(threadsNum);

        if (value % threadsNum != 0) {
            counter.addAndGet(value % threadsNum);
        }   // needs for threads similarity

        Runnable addingValue = () -> {
            incNTimes.accept(value / threadsNum);
            countDownLatch.countDown();
        };

        Stream.generate(() -> new Thread(addingValue))
            .limit(threadsNum)
            .forEach(Thread::start);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("error with waiting end of the threads!");
            throw new RuntimeException(e);
        }

        return counter.get();

    }

}
