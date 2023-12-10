package edu.hw7;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task4Test {

    Task4 task4 = new Task4();

    @Test
    void calculatePiTest() {

        double pi = task4.getPi(1_000_000_000L, 16);

        double expPi = Math.PI;

        assertThat(pi).isCloseTo(expPi, Percentage.withPercentage(0.01));
    }

    @Test
    void printMetrics() {
        long[] simulations = new long[] {10_000_000L, 100_000_000L, 1_000_000_000L};
        for (long sim : simulations) {
            System.out.printf("%d симуляций:%n", sim);
            for (int i = 1; i < 10000; i *= 2) {

                long time = System.nanoTime();
                double res = task4.getPi(sim, i);
                time = System.nanoTime() - time;

                double inaccuracy = Math.abs(res - Math.PI) / Math.PI * 100;

                System.out.printf(
                    "%d поток(а/ов): %d ms res: %f, погрешность: %f%%%n",
                    i,
                    time / 1000000,
                    res,
                    inaccuracy
                );
            }
        }
    }
}
