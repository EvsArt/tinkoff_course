package edu.hw2;

import edu.hw2.Task1.Expr.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Калькулятор выражений")
class Task1Test {

    @Test
    @DisplayName("Стандартный случай")
    void defaultTest() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, new Constant(2));
        var res = new Addition(exp, new Constant(1));

        double finalRes = res.evaluate();

        assertThat(finalRes).isEqualTo(37);
    }

    @Test
    @DisplayName("Умножение на 0")
    void multiplicationByZeroTest() {
        var two = new Constant(999999999);
        var zero = new Constant(0);
        var res = new Multiplication(two, zero);

        double finalRes = res.evaluate();

        assertThat(finalRes).isEqualTo(0);
    }

    @Test
    @DisplayName("Вычитание противоположного числа")
    void SummingToZeroTest() {
        var two = new Constant(2.3214);
        var negTwo = new Negate(two);
        var res = new Addition(two, negTwo);

        double finalRes = res.evaluate();

        assertThat(finalRes).isEqualTo(0);
    }

}
