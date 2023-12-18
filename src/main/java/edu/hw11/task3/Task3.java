package edu.hw11.task3;

import java.lang.reflect.Modifier;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.Implementation;

@Slf4j
public class Task3 {

    public Class<?> getFibCalcClass() {

        Class<?> fibClass = new ByteBuddy()
            .subclass(Object.class)
            .name("FibCalculator")
            .defineMethod("fib", int.class, Modifier.PUBLIC).withParameters(int.class)
            .intercept(new Implementation.Simple(new FibByteCodeAppender()))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();

        return fibClass;

    }

}
