package edu.project2.service.solver;

import edu.project2.model.Maze;
import edu.project2.model.Position;
import java.util.List;

public interface Solver {

    List<Position> solve(Maze maze, Position startPos, Position endPos);

}
