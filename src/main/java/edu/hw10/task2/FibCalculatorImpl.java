package edu.hw10.task2;

public class FibCalculatorImpl
    implements CachedFibCalculator {

    @Override
    public long fib(int number) {
        if (number == 0 || number == 1) {
            return 1;
        }
        CachedFibCalculator proxy = CacheProxy.create(this);
        return proxy.fib(number - 2) + proxy.fib(number - 1);
    }
}
