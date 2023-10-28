package edu.project2;

import edu.project2.constraints.Constraints;
import edu.project2.model.Maze;
import edu.project2.model.Position;
import edu.project2.service.generator.Generator;
import edu.project2.service.generator.RecursiveBacktrackingGenerator;
import edu.project2.service.solver.BFSSolver;
import edu.project2.service.solver.Solver;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private final Logger logger = LogManager.getLogger();
    private final Generator generator = new RecursiveBacktrackingGenerator();
    private final Solver solver = new BFSSolver();

    int height = Constraints.MAZEHEGHT;
    int width = Constraints.MAZEWIDTH;

    public void run() {

        logger.info("Please switch line height to minimum for correct output");

        Maze maze = new Maze(height, width, generator);
        logger.info(maze);

        Position startPos = new Position(0, 0);
        Position endPos = new Position(width - 1, height - 1);

        List<Position> solution = solver.solve(maze, startPos, endPos);

        logger.info(maze);
        logger.info("");
        logger.info("Solution: ");

        logger.info(Position.positionsListToString(solution));

    }

//    private String positionsListToString(List<Position> positions) {
//
//        StringBuilder builder = new StringBuilder();
//
//        positions.forEach(
//            it -> builder.append(
//                String.format("-> (%s, %s) ", it.x(), it.y())
//            )
//        );
//
//        return builder.toString();
//
//    }

}
