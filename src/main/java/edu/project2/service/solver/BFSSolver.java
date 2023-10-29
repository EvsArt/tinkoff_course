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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BFSSolver implements Solver {

    Logger logger = LogManager.getLogger();

    @Override
    public List<Position> solve(Maze maze, Position startPos, Position endPos) {

        Field field = maze.getField();
        field.unmarkAllCells();
        Map<Position, Position> posToPrevPosMap = new HashMap<>();
        boolean isSolved = false;

        Queue<CellWithPosition> bfsQueue = new LinkedList<>();

        CellWithPosition curCellPos = new CellWithPosition(field.getCell(startPos), startPos);
        bfsQueue.offer(curCellPos);
        curCellPos.cell().mark(true);

        while (bfsQueue.size() > 0) {

            curCellPos = bfsQueue.poll();
            if (curCellPos.position().equals(endPos)) {
                isSolved = true;
                break;
            }
            offerAllUnmarkedNeighborsToQueue(curCellPos, bfsQueue, posToPrevPosMap, field);
        }

        if (isSolved) {
            List<Position> solution = SolutionService.restoreSolution(posToPrevPosMap, startPos, endPos);
            field.unmarkAllCells();
            field.markAllCellsInList(solution);

            return solution;
        }

        logger.error("Error in solve() in BFSSolver: Maze is not solvable!");
        throw new MazeIsNotSolvableException("Error: This maze is not solvable!");

    }

    private void offerAllUnmarkedNeighborsToQueue(
        CellWithPosition curCellPos,
        Queue<CellWithPosition> bfsQueue,
        Map<Position, Position> posToPrevPosMap,
        Field field
    ) {

        Walls walls = curCellPos.cell().getWalls();
        if (!walls.left()) {
            Position newPos = curCellPos.position().getNewPosAfterMoving(SideEnum.LEFT);
            Cell newCell = field.getCell(newPos);
            if (!newCell.isMarked()) {
                newCell.mark(true);
                bfsQueue.offer(new CellWithPosition(newCell, newPos));
                posToPrevPosMap.put(newPos, curCellPos.position());
            }
        }
        if (!walls.right()) {
            Position newPos = curCellPos.position().getNewPosAfterMoving(SideEnum.RIGHT);
            Cell newCell = field.getCell(newPos);
            if (!newCell.isMarked()) {
                newCell.mark(true);
                bfsQueue.offer(new CellWithPosition(newCell, newPos));
                posToPrevPosMap.put(newPos, curCellPos.position());
            }
        }
        if (!walls.top()) {
            Position newPos = curCellPos.position().getNewPosAfterMoving(SideEnum.TOP);
            Cell newCell = field.getCell(newPos);
            if (!newCell.isMarked()) {
                newCell.mark(true);
                bfsQueue.offer(new CellWithPosition(newCell, newPos));
                posToPrevPosMap.put(newPos, curCellPos.position());
            }
        }
        if (!walls.bottom()) {
            Position newPos = curCellPos.position().getNewPosAfterMoving(SideEnum.BOTTOM);
            Cell newCell = field.getCell(newPos);
            if (!newCell.isMarked()) {
                newCell.mark(true);
                bfsQueue.offer(new CellWithPosition(newCell, newPos));
                posToPrevPosMap.put(newPos, curCellPos.position());
            }
        }

    }

}
