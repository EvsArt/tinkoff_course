package edu.hw10.task2;

public class FibCalculatorImpl implements FibCalculator {

    @Override
    public long fib(int number) {
        if (number == 0 || number == 1) {
            return 1;
        }
        return fib(number - 2) + fib(number - 1);
    }
}
