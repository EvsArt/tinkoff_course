package edu.hw10.task2;

public interface CachedFibCalculator {
    @Cache(persist = true) long fib(int number);
}
