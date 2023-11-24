package edu.project2.service;

import edu.project2.exceptions.WrongPositionsMapException;
import edu.project2.model.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class SolutionServiceTest {

    @Test
    @DisplayName("Test solution restorer")
    void restoreSolution() {

        Position start = new Position(0, 0);
        Position end = new Position(4, 4);

        Map<Position, Position> positionMap = new HashMap<>();
        positionMap.put(new Position(4, 4), new Position(3,4));
        positionMap.put(new Position(3, 4), new Position(2,4));
        positionMap.put(new Position(2, 4), new Position(1,4));
        positionMap.put(new Position(1, 4), new Position(0,4));
        positionMap.put(new Position(0, 4), new Position(0,3));
        positionMap.put(new Position(0, 3), new Position(0,2));
        positionMap.put(new Position(0, 2), new Position(0,1));
        positionMap.put(new Position(0, 1), new Position(0,0));

        List<Position> expectedRes = List.of(
            new Position(0,0),
            new Position(0, 1),
            new Position(0,2),
            new Position(0,3),
            new Position(0,4),
            new Position(1,4),
            new Position(2,4),
            new Position(3,4),
            new Position(4,4)
        );

        List<Position> res = SolutionService.restoreSolution(positionMap, start, end);

        assertThat(res).isEqualTo(expectedRes);

    }

    @Test
    @DisplayName("Test solution restorer with wrong args")
    void badRestoreSolution() {

        Position start = new Position(0, 0);
        Position end = new Position(4, 4);

        Map<Position, Position> positionMap = new HashMap<>();

        assertThatExceptionOfType(WrongPositionsMapException.class)
            .isThrownBy(
                () -> SolutionService.restoreSolution(positionMap, start, end)
            );

    }
}
