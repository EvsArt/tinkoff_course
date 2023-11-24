package edu.project2.service.generator;

import edu.project2.model.Cell;
import edu.project2.model.CellWithPosition;
import edu.project2.model.Field;
import edu.project2.model.Neighbor;
import edu.project2.model.Position;
import edu.project2.model.SideEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class RecursiveBacktrackingGenerator implements Generator {

    private final Random random = new Random();

    @Override
    public Field generate(Field field) {

        field.closeAllCells();
        field.unmarkAllCells();

        Stack<CellWithPosition> cellStack = new Stack<>();
        var position = new Position(0, 0);
        var curCellPos = new CellWithPosition(field.getCell(position), position);

        do {
            curCellPos.cell().mark(true);

            List<Neighbor> unvisitedNeighbors = getUnvisitedNeighboringCells(field, curCellPos.position());
            if (!unvisitedNeighbors.isEmpty()) {
                Neighbor neighbor = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));

                field.changeWay(curCellPos.position(), neighbor.side(), false);
                position = curCellPos.position().getNewPosAfterMoving(neighbor.side());

                var neighborCell = new CellWithPosition(neighbor.cell(), position);
                cellStack.push(neighborCell);
                curCellPos = neighborCell;

            } else {
                cellStack.pop();
                if (cellStack.isEmpty()) {
                    break;
                }
                curCellPos = cellStack.peek();
            }

        } while (!cellStack.isEmpty());

        field.unmarkAllCells();

        return field;

    }

    protected List<Neighbor> getUnvisitedNeighboringCells(Field field, Position cellPosition) {

        List<Neighbor> unvisitedNeighbors = new ArrayList<>(SideEnum.values().length);
        int posX = cellPosition.x();
        int posY = cellPosition.y();
        Cell neighbor;

        if (posX > 0) {
            Position neighborPos = cellPosition.getNewPosAfterMoving(SideEnum.LEFT);
            neighbor = field.getCell(neighborPos);
            if (!neighbor.isMarked()) {
                unvisitedNeighbors.add(new Neighbor(SideEnum.LEFT, neighbor, neighborPos));
            }
        }
        if (posX < field.getWidth() - 1) {
            Position neighborPos = cellPosition.getNewPosAfterMoving(SideEnum.RIGHT);
            neighbor = field.getCell(neighborPos);
            if (!neighbor.isMarked()) {
                unvisitedNeighbors.add(new Neighbor(SideEnum.RIGHT, neighbor, neighborPos));
            }
        }
        if (posY > 0) {
            Position neighborPos = cellPosition.getNewPosAfterMoving(SideEnum.TOP);
            neighbor = field.getCell(neighborPos);
            if (!neighbor.isMarked()) {
                unvisitedNeighbors.add(new Neighbor(SideEnum.TOP, neighbor, neighborPos));
            }
        }
        if (posY < field.getHeight() - 1) {
            Position neighborPos = cellPosition.getNewPosAfterMoving(SideEnum.BOTTOM);
            neighbor = field.getCell(neighborPos);
            if (!neighbor.isMarked()) {
                unvisitedNeighbors.add(new Neighbor(SideEnum.BOTTOM, neighbor, neighborPos));
            }
        }

        return unvisitedNeighbors;

    }

}
