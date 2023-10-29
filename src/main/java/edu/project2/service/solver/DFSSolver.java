package edu.project2.service.solver;

import edu.project2.exceptions.MazeIsNotSolvableException;
import edu.project2.model.Cell;
import edu.project2.model.CellWithPosition;
import edu.project2.model.Field;
import edu.project2.model.Maze;
import edu.project2.model.Position;
import edu.project2.model.SideEnum;
import edu.project2.model.Walls;
import edu.project2.service.SolutionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DFSSolver implements Solver {

    private final Map<Position, Position> positionToPrevPositionMap = new HashMap<>();

    @Override
    public List<Position> solve(Maze maze, Position startPos, Position endPos) {

        Field field = maze.getField();
        field.unmarkAllCells();

        CellWithPosition startCellPos = new CellWithPosition(field.getCell(startPos), startPos);

        boolean mazeIsSolved = dfs(startCellPos, field, endPos);
        if (!mazeIsSolved) {
            throw new MazeIsNotSolvableException("Maze is not solvable!");
        }

        List<Position> solution = SolutionService.restoreSolution(positionToPrevPositionMap, startPos, endPos);
        field.unmarkAllCells();
        field.markAllCellsInList(solution);

        return solution;

    }

    private boolean dfs(CellWithPosition startCellPos, Field field, Position finish) {

        List<CellWithPosition> neighbors = getUnvisitedNeighbors(startCellPos, field);
        if (neighbors.isEmpty()) {
            return false;
        }

        boolean flag;
        for (CellWithPosition cell : neighbors) {
            if (cell.position().equals(finish)) {
                return true;
            } else {
                flag = dfs(cell, field, finish);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<CellWithPosition> getUnvisitedNeighbors(CellWithPosition cell, Field field) {

        Walls walls = cell.cell().getWalls();
        List<CellWithPosition> res = new ArrayList<>(SideEnum.values().length);

        if (!walls.top()) {
            Position newPos = cell.position().getNewPosAfterMoving(SideEnum.TOP);
            Cell newCell = field.getCell(newPos);
            if (!newCell.isMarked()) {
                newCell.mark(true);
                res.add(new CellWithPosition(newCell, newPos));
                positionToPrevPositionMap.put(newPos, cell.position());
            }
        }
        if (!walls.right()) {
            Position newPos = cell.position().getNewPosAfterMoving(SideEnum.RIGHT);
            Cell newCell = field.getCell(newPos);
            if (!newCell.isMarked()) {
                newCell.mark(true);
                res.add(new CellWithPosition(newCell, newPos));
                positionToPrevPositionMap.put(newPos, cell.position());
            }
        }
        if (!walls.bottom()) {
            Position newPos = cell.position().getNewPosAfterMoving(SideEnum.BOTTOM);
            Cell newCell = field.getCell(newPos);
            if (!newCell.isMarked()) {
                newCell.mark(true);
                res.add(new CellWithPosition(newCell, newPos));
                positionToPrevPositionMap.put(newPos, cell.position());
            }
        }
        if (!walls.left()) {
            Position newPos = cell.position().getNewPosAfterMoving(SideEnum.LEFT);
            Cell newCell = field.getCell(newPos);
            if (!newCell.isMarked()) {
                newCell.mark(true);
                res.add(new CellWithPosition(newCell, newPos));
                positionToPrevPositionMap.put(newPos, cell.position());
            }
        }

        return res;

    }

}
