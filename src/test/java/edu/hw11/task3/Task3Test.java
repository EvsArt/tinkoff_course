package edu.hw11.task3;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task3Test {

    @Test
    void getFibCalc() throws ReflectiveOperationException {
        // given
        Task3 task3 = new Task3();

        // when
        Class<?> fibCalcClass = task3.getFibCalcClass();
        Object findCalc = fibCalcClass.getDeclaredConstructors()[0].newInstance();
        Method method = fibCalcClass.getDeclaredMethod("fib", int.class);
        int res = (int) method.invoke(findCalc, 7);

        // then
        assertEquals(13, res);

    }

}
