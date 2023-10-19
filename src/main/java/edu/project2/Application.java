package edu.project2;

import edu.project2.constraints.Constraints;
import edu.project2.model.Maze;
import edu.project2.model.Position;
import edu.project2.service.Generator;
import edu.project2.service.Solver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private final Logger logger = LogManager.getLogger();

    private Solver solver;
    private Generator generator;
    private Maze maze;

    int height = Constraints.MAZESIZE;
    int width = Constraints.MAZESIZE;

    public void run() {

        Maze unsolvedMaze = new Maze(height, width, generator);
        Position startPos = new Position(0, 0);
        Position endPos = new Position(width - 1, height - 1);
        Maze solvedMaze = solver.solve(maze, startPos, endPos);

        logger.info(unsolvedMaze);
        logger.info(solvedMaze);

        // TODO: 20.10.2023 Надо реализовать отображение лабиринтов и ячеек с метками в консоль
        // TODO: 20.10.2023 Затем написать реализации Generator и Solver

    }

}
