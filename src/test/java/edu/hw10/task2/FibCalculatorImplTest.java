package edu.hw10.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FibCalculatorImplTest {

    @Test
    void fib() {
        FibCalculator fibCalculator = new FibCalculatorImpl();
        FibCalculator proxy = CacheProxy.create(fibCalculator);

        System.out.println(proxy.fib(45));
        System.out.println(proxy.fib(45));
        System.out.println(proxy.fib(45));
        System.out.println(proxy.fib(45));


    }
}
