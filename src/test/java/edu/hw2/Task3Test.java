package edu.hw2;

import edu.hw2.Task3.ConnectionManager;
import edu.hw2.Task3.PopularCommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.offset;

@DisplayName("Удаленный сервер")
class Task3Test {

    Task3 task3 = new Task3();

    @Test
    @DisplayName("Default manager chance check")
    void executorWithDefaultConnectionManager() {
        //given
        double exceptionInBadConnectionChance = task3.chanceOfExceptionInBadConnection;
        double faultyConnectionChance = task3.chanceOfFaultyConnection;

        int iterationsForStat = 10000;

        ConnectionManager manager = task3.new DefaultConnectionManager();
        int maxAttemptsForConnection = 3;

        //when
        int countBadConnections = getBadAttempts(iterationsForStat, manager, maxAttemptsForConnection);

        //  1 - (chanceToGetAllBadAttempts)^attempts = chance to get at least one good connection by n attempts
        double expectedSuccessChance =
            1 - Math.pow(
                exceptionInBadConnectionChance * faultyConnectionChance,
                maxAttemptsForConnection
            );

        double realSuccessChance = (double) (iterationsForStat - countBadConnections) / iterationsForStat;

        //then
        assertThat(realSuccessChance).isCloseTo(expectedSuccessChance, offset(100.0 / iterationsForStat));

    }

    @Test
    @DisplayName("Faulty manager chance check")
    void executorWithFaultyConnectionManager() {
        //given
        double exceptionInBadConnectionChance = task3.chanceOfExceptionInBadConnection;
        double faultyConnectionChance = 1;

        int iterationsForStat = 10000;

        ConnectionManager manager = task3.new FaultyConnectionManager();
        int maxAttemptsForConnection = 2;

        //when
        int countBadConnections = getBadAttempts(iterationsForStat, manager, maxAttemptsForConnection);

        //  1 - (chanceToGetAllBadAttempts)^attempts = chance to get at least one good connection by n attempts
        double expectedSuccessChance =
            1 - Math.pow(
                exceptionInBadConnectionChance * faultyConnectionChance,
                maxAttemptsForConnection
            );

        double realSuccessChance = (double) (iterationsForStat - countBadConnections) / iterationsForStat;

        //then
        assertThat(realSuccessChance).isCloseTo(expectedSuccessChance, offset(100.0 / iterationsForStat));

    }

    private int getBadAttempts(int iterations, ConnectionManager manager, int maxAttempts) {

        int countBadConnections = 0;

        for (int i = 0; i < iterations; i++) {
            try {
                PopularCommandExecutor executor = task3.new PopularCommandExecutor(manager, maxAttempts);
                executor.updatePackages();
            } catch (RuntimeException e) {
                countBadConnections++;
            }
        }
        return countBadConnections;
    }

}
