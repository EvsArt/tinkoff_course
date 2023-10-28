package edu.project2.service.solver;

import edu.project2.exceptions.MazeIsNotSolvable;
import edu.project2.model.Cell;
import edu.project2.model.CellWithPosition;
import edu.project2.model.Field;
import edu.project2.model.Maze;
import edu.project2.model.Position;
import edu.project2.model.SideEnum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFSSolver implements Solver {

    @Override
    public List<Position> solve(Maze maze, Position startPos, Position endPos) {

        Field field = maze.getField();
        field.unmarkAllCells();
        Map<Position, Position> posToPrevPosMap = new HashMap<>();

        Queue<CellWithPosition> bfsQueue = new LinkedList<>();

        CellWithPosition curCellPos = new CellWithPosition(field.getCell(startPos), startPos);
        bfsQueue.offer(curCellPos);
        curCellPos.cell().mark(true);

        while (bfsQueue.size() > 0) {

            curCellPos = bfsQueue.poll();
            if (curCellPos.position().equals(endPos)) {

                List<Position> solution = restoreSolution(posToPrevPosMap, startPos, endPos);
                field.unmarkAllCells();
                field.markAllCellsInList(solution);

                return solution;

            }
            offerAllUnmarkedNeighborsToQueue(curCellPos, bfsQueue, posToPrevPosMap, field);
        }

        throw new MazeIsNotSolvable("Error: This maze is not solvable!");

    }

    private List<Position> restoreSolution(
        Map<Position, Position> posToPrevPosMap,
        Position startPos,
        Position endPos
    ) {

        ArrayList<Position> res = new ArrayList<>();
        Position prevPosition = endPos;
        res.add(prevPosition);
        Position newPosition = endPos;

        while (!newPosition.equals(startPos)) {

            newPosition = posToPrevPosMap.get(prevPosition);
            prevPosition = newPosition;
            res.add(newPosition);

        }

        return res.reversed();

    }

    private void offerAllUnmarkedNeighborsToQueue(
        CellWithPosition curCellPos,
        Queue<CellWithPosition> bfsQueue,
        Map<Position, Position> posToPrevPosMap,
        Field field
    ) {

        if (!curCellPos.cell().getWalls().left()) {
            Position newPos = curCellPos.position().getNewPosAfterMoving(SideEnum.LEFT);
            Cell cell = field.getCell(newPos);
            if (!cell.isMarked()) {
                cell.mark(true);
                bfsQueue.offer(new CellWithPosition(cell, newPos));
                posToPrevPosMap.put(newPos, curCellPos.position());
            }
        }
        if (!curCellPos.cell().getWalls().right()) {
            Position newPos = curCellPos.position().getNewPosAfterMoving(SideEnum.RIGHT);
            Cell cell = field.getCell(newPos);
            if (!cell.isMarked()) {
                cell.mark(true);
                bfsQueue.offer(new CellWithPosition(cell, newPos));
                posToPrevPosMap.put(newPos, curCellPos.position());
            }
        }
        if (!curCellPos.cell().getWalls().top()) {
            Position newPos = curCellPos.position().getNewPosAfterMoving(SideEnum.TOP);
            Cell cell = field.getCell(newPos);
            if (!cell.isMarked()) {
                cell.mark(true);
                bfsQueue.offer(new CellWithPosition(cell, newPos));
                posToPrevPosMap.put(newPos, curCellPos.position());
            }
        }
        if (!curCellPos.cell().getWalls().bottom()) {
            Position newPos = curCellPos.position().getNewPosAfterMoving(SideEnum.BOTTOM);
            Cell cell = field.getCell(newPos);
            if (!cell.isMarked()) {
                cell.mark(true);
                bfsQueue.offer(new CellWithPosition(cell, newPos));
                posToPrevPosMap.put(newPos, curCellPos.position());
            }
        }

    }

}
