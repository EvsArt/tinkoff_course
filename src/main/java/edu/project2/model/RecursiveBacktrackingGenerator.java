package edu.project2.model;

import edu.project2.service.Generator;
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
            curCellPos.cell.mark(true);

            List<Neighbor> unvisitedNeighbors = getUnvisitedNeighboringCells(field, curCellPos.position);
            if (!unvisitedNeighbors.isEmpty()) {
                Neighbor neighbor = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));

                field.changeWay(curCellPos.position, neighbor.side, false);
                position = getNewPosAfterMoving(curCellPos.position, neighbor.side);

                var neighborCell = new CellWithPosition(neighbor.cell, position);
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

    private List<Neighbor> getUnvisitedNeighboringCells(Field field, Position cellPosition) {

        List<Neighbor> unvisitedNeighbors = new ArrayList<>(SideEnum.values().length);
        int posX = cellPosition.x();
        int posY = cellPosition.y();
        Cell neighbor;

        if (posX > 0) {
            neighbor = field.getCell(new Position(posX - 1, posY));
            if (!neighbor.isMarked()) {
                unvisitedNeighbors.add(new Neighbor(SideEnum.LEFT, neighbor));
            }
        }
        if (posX < field.getWidth() - 1) {
            neighbor = field.getCell(new Position(posX + 1, posY));
            if (!neighbor.isMarked()) {
                unvisitedNeighbors.add(new Neighbor(SideEnum.RIGHT, neighbor));
            }
        }
        if (posY > 0) {
            neighbor = field.getCell(new Position(posX, posY - 1));
            if (!neighbor.isMarked()) {
                unvisitedNeighbors.add(new Neighbor(SideEnum.TOP, neighbor));
            }
        }
        if (posY < field.getHeight() - 1) {
            neighbor = field.getCell(new Position(posX, posY + 1));
            if (!neighbor.isMarked()) {
                unvisitedNeighbors.add(new Neighbor(SideEnum.BOTTOM, neighbor));
            }
        }

        return unvisitedNeighbors;

    }

    private Position getNewPosAfterMoving(Position oldPos, SideEnum side) {

        int x = oldPos.x();
        int y = oldPos.y();

        return switch (side) {
            case LEFT -> new Position(x - 1, y);
            case TOP -> new Position(x, y - 1);
            case RIGHT -> new Position(x + 1, y);
            case BOTTOM -> new Position(x, y + 1);
        };
    }

    private record Neighbor(SideEnum side, Cell cell) {
    }

    private record CellWithPosition(Cell cell, Position position) {
    }

}
