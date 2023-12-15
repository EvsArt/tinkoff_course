package edu.project4.utils;

import edu.project4.model.DecartPoint;
import edu.project4.model.FractalImage;
import edu.project4.model.PolarPoint;
import edu.project4.utils.Renderer;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RendererTest {

    Renderer renderer = new Renderer();

    @Test
    void positiveDecartPointToPolarPoint() {
        // given
        DecartPoint point = new DecartPoint(0.1341, 0.4517);

        // when
        PolarPoint polarPoint = renderer.decartPointToPolarPoint(point);

        // then
        assertThat(polarPoint.angle()).isCloseTo(1.2822, Percentage.withPercentage(0.01));
        assertThat(polarPoint.length()).isCloseTo(0.4712, Percentage.withPercentage(0.01));
    }

    @Test
    void negativeDecartPointToPolarPoint() {
        // given
        DecartPoint point = new DecartPoint(0.1341, -0.4517);

        // when
        PolarPoint polarPoint = renderer.decartPointToPolarPoint(point);

        // then
        assertThat(polarPoint.angle()).isCloseTo(1.8594, Percentage.withPercentage(0.01));
        assertThat(polarPoint.length()).isCloseTo(0.4712, Percentage.withPercentage(0.01));
    }

    @Test
    void zeroDecartPointToPolarPoint() {
        // given
        DecartPoint point = new DecartPoint(1, 0);

        // when
        PolarPoint polarPoint = renderer.decartPointToPolarPoint(point);

        // then
        assertThat(polarPoint.angle()).isCloseTo(0, Percentage.withPercentage(0.01));
        assertThat(polarPoint.length()).isCloseTo(1, Percentage.withPercentage(0.01));
    }

    @Test
    void positivePolarPointToDecartPoint() {
        // given
        PolarPoint point = new PolarPoint(1.2822, 0.4712);

        // when
        DecartPoint decartPoint = renderer.polarPointToDecartPoint(point);

        // then
        assertThat(decartPoint.x()).isCloseTo(0.1341, Percentage.withPercentage(0.01));
        assertThat(decartPoint.y()).isCloseTo(0.4517, Percentage.withPercentage(0.01));
    }

    @Test
    void negativePolarPointToDecartPoint() {
        // given
        PolarPoint point = new PolarPoint(1.8594, 0.4712);

        // when
        DecartPoint decartPoint = renderer.polarPointToDecartPoint(point);

        // then
        assertThat(decartPoint.x()).isCloseTo(-0.1341, Percentage.withPercentage(0.01));
        assertThat(decartPoint.y()).isCloseTo(0.4517, Percentage.withPercentage(0.01));
    }

    @Test
    void zeroPolarPointToDecartPoint() {
        // given
        PolarPoint point = new PolarPoint(0, 1);

        // when
        DecartPoint decartPoint = renderer.polarPointToDecartPoint(point);

        // then
        assertThat(decartPoint.x()).isCloseTo(1, Percentage.withPercentage(0.01));
        assertThat(decartPoint.y()).isCloseTo(0, Percentage.withPercentage(0.01));
    }

    @Test
    void zeroReflectOfRadianAngle() {

        // given
        PolarPoint point = new PolarPoint(0, 1);

        // when
        PolarPoint newPoint = renderer.reflectOfRadianAngle(point, Math.PI / 2);

        // then
        assertThat(newPoint.angle()).isCloseTo(Math.PI, Percentage.withPercentage(0.01));

    }

    @Test
    void piAt2ReflectOfRadianAngle() {

        // given
        PolarPoint point = new PolarPoint(Math.PI / 2, 0.5);

        // when
        PolarPoint newPoint = renderer.reflectOfRadianAngle(point, 3 * Math.PI / 4);

        // then
        assertThat(newPoint.angle()).isCloseTo(Math.PI, Percentage.withPercentage(0.01));

    }

    @Test
    void testOneTreadRenderer() {
        assertDoesNotThrow(() -> {
            FractalImage image = FractalImage.create(400, 400);
            renderer.render(100000, 2, 20, 2, image);
            renderer.correction(image);
        });
    }

    @Test
    void testMultiTreadRenderer() {
        assertDoesNotThrow(() -> {
            FractalImage image = FractalImage.create(400, 400);
            renderer.render(100000, 2, 20, 2, image);
            renderer.correction(image);
        });
    }

}
