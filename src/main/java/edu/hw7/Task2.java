package edu.hw7;

import java.math.BigInteger;
import java.util.stream.Stream;

public class Task2 {

    public BigInteger getFactorial(int value) {
        return Stream.iterate(1, it -> it + 1).limit(value)
            .parallel()
            .map(BigInteger::valueOf)
            .reduce(BigInteger.ONE, BigInteger::multiply);
    }

}
