package edu.project2.service.solver;

import edu.project2.model.Maze;
import edu.project2.model.Position;
import edu.project2.service.generator.RecursiveBacktrackingGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DFSSolverTest {

    DFSSolver solver = new DFSSolver();

    @Test
    @DisplayName("Test solver")
    void solve() {

        int height = 5;
        int width = 5;

        RecursiveBacktrackingGenerator generator = new RecursiveBacktrackingGenerator();
        Maze maze = new Maze(height, width, generator);
        Position start = new Position(0, 0);
        Position end = new Position(height - 1, width - 1);

        List<Position> res = solver.solve(maze, start, end);

        assertThat(res.size() >= height + width - 1).isTrue();
        assertThat(res.contains(start)).isTrue();
        assertThat(res.contains(end)).isTrue();

    }

}
