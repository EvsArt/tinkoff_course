package edu.project2.service.generator;

import edu.project2.model.*;
import edu.project2.service.solver.BFSSolver;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RecursiveBacktrackingGeneratorTest {

    RecursiveBacktrackingGenerator generator = new RecursiveBacktrackingGenerator();

    @Test
    @DisplayName("Test generator")
    void generate() {

        int height = 5;
        int width = 5;

        Maze maze = new Maze(height, width, generator);
        Position start = new Position(0, 0);
        Position end = new Position(height - 1, width - 1);

        BFSSolver solver = new BFSSolver();
        List<Position> res = solver.solve(maze, start, end);

        assertThat(res.size() >= height + width - 1).isTrue();
        assertThat(res.contains(start)).isTrue();
        assertThat(res.contains(end)).isTrue();

    }

    @Test
    @DisplayName("Test getting unmarked neighbors")
    void getUnvisitedNeighboringCells() {

        int height = 5;
        int width = 5;

        Field field = new DefaultField(height, width);
        Position position = new Position(2, 2);
        field.getCell(position.getNewPosAfterMoving(SideEnum.RIGHT)).mark(true);

        List<Neighbor> expectedRes = List.of(
            new Neighbor(
                SideEnum.TOP,
                field.getCell(position.getNewPosAfterMoving(SideEnum.TOP)),
                position.getNewPosAfterMoving(SideEnum.TOP)
            ),
            new Neighbor(
                SideEnum.LEFT,
                field.getCell(position.getNewPosAfterMoving(SideEnum.LEFT)),
                position.getNewPosAfterMoving(SideEnum.LEFT)
            ),
            new Neighbor(
                SideEnum.BOTTOM,
                field.getCell(position.getNewPosAfterMoving(SideEnum.BOTTOM)),
                position.getNewPosAfterMoving(SideEnum.BOTTOM)
            )
        );

        List<Neighbor> res = generator.getUnvisitedNeighboringCells(field, new Position(2, 2));

        assertThat(Sets.newHashSet(res)).isEqualTo(Sets.newHashSet(expectedRes));

    }

    @Test
    @DisplayName("Test getting unmarked neighbors where all neighbors were marked")
    void getEmptyUnvisitedNeighboringCells() {

        int height = 5;
        int width = 5;

        Field field = new DefaultField(height, width);
        Position position = new Position(2, 2);
        field.getCell(position.getNewPosAfterMoving(SideEnum.RIGHT)).mark(true);
        field.getCell(position.getNewPosAfterMoving(SideEnum.LEFT)).mark(true);
        field.getCell(position.getNewPosAfterMoving(SideEnum.TOP)).mark(true);
        field.getCell(position.getNewPosAfterMoving(SideEnum.BOTTOM)).mark(true);

        List<Neighbor> expectedRes = List.of();

        List<Neighbor> res = generator.getUnvisitedNeighboringCells(field, new Position(2, 2));

        assertThat(Sets.newHashSet(res)).isEqualTo(Sets.newHashSet(expectedRes));

    }

}
