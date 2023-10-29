package edu.project2;

import edu.project2.constraints.Constraints;
import edu.project2.model.Maze;
import edu.project2.model.Position;
import edu.project2.service.generator.Generator;
import edu.project2.service.generator.PrimsGenerator;
import edu.project2.service.generator.RecursiveBacktrackingGenerator;
import edu.project2.service.solver.BFSSolver;
import edu.project2.service.solver.DFSSolver;
import edu.project2.service.solver.Solver;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private final Logger logger = LogManager.getLogger();
    private final Generator generator1 = new RecursiveBacktrackingGenerator();
    private final Generator generator2 = new PrimsGenerator();
    private final Solver solver1 = new BFSSolver();
    private final Solver solver2 = new DFSSolver();

    int height = Constraints.MAZEHEGHT;
    int width = Constraints.MAZEWIDTH;

    public void run() {

        Position startPos = new Position(0, 0);
        Position endPos = new Position(width - 1, height - 1);

        logger.info("Please switch line height to minimum for correct output");

        Maze maze1 = new Maze(height, width, generator1);
        logger.info("RecursiveBacktracking-generated maze:");
        logger.info(maze1);

        logger.info("BFS Solution of maze 1: ");
        List<Position> solution1 = solver1.solve(maze1, startPos, endPos);
        logger.info(maze1);
        logger.info(Position.positionsListToString(solution1));


        Maze maze2 = new Maze(height, width, generator2);
        logger.info("Prims-generated maze");
        logger.info(maze2);

        logger.info("DFS Solution of maze 2: ");
        List<Position> solution2 = solver2.solve(maze2, startPos, endPos);
        logger.info(maze2);
        logger.info(Position.positionsListToString(solution2));

    }

}
