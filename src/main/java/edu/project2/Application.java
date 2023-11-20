package edu.project2;

import edu.project2.constraints.Constraints;
import edu.project2.model.Maze;
import edu.project2.model.Position;
import edu.project2.service.generator.PrimsGenerator;
import edu.project2.service.generator.RecursiveBacktrackingGenerator;
import edu.project2.service.solver.BFSSolver;
import edu.project2.service.solver.DFSSolver;
import edu.project2.service.solver.Solver;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    Logger log = LogManager.getLogger();
    private final Solver solver1 = new BFSSolver();
    private final Solver solver2 = new DFSSolver();
    int height = Constraints.MAZE_HEIGHT;
    int width = Constraints.MAZE_WIDTH;

    public void run() {

        Position startPos = new Position(0, 0);
        Position endPos = new Position(width - 1, height - 1);

        log.info("Please switch line height to minimum for correct output");
        Maze maze1 = new Maze(height, width, new RecursiveBacktrackingGenerator());
        log.info("RecursiveBacktracking-generated maze:");
        log.info(maze1.toString());

        log.info("BFS Solution of maze 1: ");
        List<Position> solution1 = solver1.solve(maze1, startPos, endPos);
        log.info(maze1.toString());
        log.info(Position.positionsListToString(solution1));

        Maze maze2 = new Maze(height, width, new PrimsGenerator());
        log.info("Prims-generated maze");
        log.info(maze2.toString());

        log.info("DFS Solution of maze 2: ");
        List<Position> solution2 = solver2.solve(maze2, startPos, endPos);
        log.info(maze2.toString());
        log.info(Position.positionsListToString(solution2));

    }

}
