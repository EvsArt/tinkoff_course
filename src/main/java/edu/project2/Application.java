package edu.project2;

import edu.project2.constraints.Constraints;
import edu.project2.model.DefaultSolver;
import edu.project2.model.Field;
import edu.project2.model.Maze;
import edu.project2.model.Position;
import edu.project2.model.RecursiveBacktrackingGenerator;
import edu.project2.service.Generator;
import edu.project2.service.Solver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private final Logger logger = LogManager.getLogger();
    private final Generator generator = new RecursiveBacktrackingGenerator();
//    private final Solver solver = new DefaultSolver();

    int height = Constraints.MAZESIZE;
    int width = Constraints.MAZESIZE;

    public void run() {

        Maze unsolvedMaze = new Maze(height, width, generator);

        Field field = unsolvedMaze.getField();

        Position startPos = new Position(0, 0);
        Position endPos = new Position(width - 1, height - 1);

//        Maze solvedMaze = solver.solve(unsolvedMaze, startPos, endPos);

        logger.info(unsolvedMaze);
//        logger.info(solvedMaze);

    }

}
