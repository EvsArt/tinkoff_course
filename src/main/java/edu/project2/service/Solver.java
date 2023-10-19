package edu.project2.service;

import edu.project2.model.Maze;
import edu.project2.model.Position;

public interface Solver {

    Maze solve(Maze maze, Position startPos, Position endPos);

}
