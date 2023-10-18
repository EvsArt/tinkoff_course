package edu.hw2;

import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Квадрат и прямоугольник")
class Task2Test {

    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("Прямоугольники")
    void rectangleArea(Rectangle rect) {
        rect.setWidth(20);
        rect.setHeight(10);

        assertThat(rect.area()).isEqualTo(200.0);

    }

    @Test
    @DisplayName("Квадраты как прямоугольники")
    void fakeSquareArea() {
        Square square = new Square();
        Rectangle fakeSquare;

        fakeSquare = square.setWidth(20);
        fakeSquare.setHeight(10);   // При изменении какой-то стороны квадрат становится прямоугольником

        assertThat(fakeSquare.area()).isEqualTo(200.0);

    }

    @Test
    @DisplayName("Квадраты")
    void squareArea() {
        Square square = new Square();

        square = square.setSide(20);

        assertThat(square.area()).isEqualTo(400.0);

    }

}
