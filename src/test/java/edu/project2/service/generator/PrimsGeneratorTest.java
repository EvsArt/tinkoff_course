package edu.project2.service.generator;

import edu.project2.exceptions.CellDoesNotHaveMarkedNeighborsException;
import edu.project2.model.*;
import edu.project2.service.solver.BFSSolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class PrimsGeneratorTest {

    PrimsGenerator generator = new PrimsGenerator();

    @Test
    @DisplayName("Test generator")
    void generate() {

        int height = 5;
        int width = 5;

        Maze maze = new Maze(height, width, generator);
        Position start = new Position(0, 0);
        Position end = new Position(4, 4);

        BFSSolver solver = new BFSSolver();
        List<Position> res = solver.solve(maze, start, end);

        assertThat(res.contains(start)).isTrue();
        assertThat(res.contains(end)).isTrue();

    }

    @Test
    @DisplayName("Test getting random marked neighbor")
    void getRandomMarkedNeighbor() {

        int height = 5;
        int width = 5;

        Field field = new DefaultField(height, width);
        Position position = new Position(2, 2);
        field.getCell(position.getNewPosAfterMoving(SideEnum.RIGHT)).mark(true);
        field.getCell(position.getNewPosAfterMoving(SideEnum.LEFT)).mark(true);

        List<Neighbor> expectedResults = List.of(
            new Neighbor(
                SideEnum.LEFT,
                field.getCell(position.getNewPosAfterMoving(SideEnum.LEFT)),
                position.getNewPosAfterMoving(SideEnum.LEFT)
            ),
            new Neighbor(
                SideEnum.RIGHT,
                field.getCell(position.getNewPosAfterMoving(SideEnum.RIGHT)),
                position.getNewPosAfterMoving(SideEnum.RIGHT)
            )
        );

        Neighbor res =
            generator.getRandomMarkedNeighbor(field, new CellWithPosition(field.getCell(position), position));

        assertThat(res).isIn(expectedResults);

    }

    @Test
    @DisplayName("Test getting random marked neighbor where all neighbors were unmarked")
    void getEmptyRandomMarkedNeighbor() {

        Field field = new DefaultField(5, 5);
        Position position = new Position(2, 2);

        assertThatExceptionOfType(CellDoesNotHaveMarkedNeighborsException.class)
            .isThrownBy(
                () -> generator.getRandomMarkedNeighbor(field, new CellWithPosition(field.getCell(position), position))
            );

    }

}
