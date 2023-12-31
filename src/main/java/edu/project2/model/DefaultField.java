package edu.project2.model;

import edu.project2.constraints.StringConstraints;
import edu.project2.exceptions.FieldBorderException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultField implements Field {

    Logger logger = LogManager.getLogger();

    private final int height;
    private final int width;
    private final List<List<Cell>> field;

    public DefaultField(int height, int width) {    // return field where every cell is closed

        this.height = height;
        this.width = width;

        field = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            field.add(i, new ArrayList<>(width));
            for (int j = 0; j < width; j++) {
                field.get(i).add(j, DefaultCell.getClosedCell());
            }
        }
    }

    public List<List<Cell>> getField() {
        return field;
    }

    @Override
    public Cell getCell(Position pos) {
        return field.get(pos.y()).get(pos.x());
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    @SuppressWarnings("MissingSwitchDefault")
    public void changeWay(Position pos, SideEnum side, boolean closing) throws FieldBorderException {

        switch (side) {
            case LEFT -> {
                if (pos.x() <= 0) {
                    throwFieldBorderException(pos.x(), pos.y(), side);
                }
                field.get(pos.y()).get(pos.x()).changeWay(SideEnum.LEFT, closing);
                field.get(pos.y()).get(pos.x() - 1).changeWay(SideEnum.RIGHT, closing);
            }
            case TOP -> {
                if (pos.y() <= 0) {
                    throwFieldBorderException(pos.x(), pos.y(), side);
                }
                field.get(pos.y()).get(pos.x()).changeWay(SideEnum.TOP, closing);
                field.get(pos.y() - 1).get(pos.x()).changeWay(SideEnum.BOTTOM, closing);
            }
            case RIGHT -> {
                if (pos.x() >= width - 1) {
                    throwFieldBorderException(pos.x(), pos.y(), side);
                }
                field.get(pos.y()).get(pos.x()).changeWay(SideEnum.RIGHT, closing);
                field.get(pos.y()).get(pos.x() + 1).changeWay(SideEnum.LEFT, closing);
            }
            case BOTTOM -> {
                if (pos.y() >= height - 1) {
                    throwFieldBorderException(pos.x(), pos.y(), side);
                }
                field.get(pos.y()).get(pos.x()).changeWay(SideEnum.BOTTOM, closing);
                field.get(pos.y() + 1).get(pos.x()).changeWay(SideEnum.TOP, closing);
            }
        }

    }

    private void throwFieldBorderException(int x, int y, SideEnum side) {
        String msg = StringConstraints.attemptToRemovingBorderAt(x, y, side);
        logger.error("Error in changeWay() in DefaultField: " + msg);
        throw new FieldBorderException(msg);
    }

    @Override
    public void unmarkAllCells() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getCell(new Position(x, y)).mark(false);
            }
        }
    }

    @Override
    public void closeAllCells() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getCell(new Position(x, y)).setWalls(
                    new DefaultCell.DefaultWalls(true, true, true, true)
                );
            }
        }
    }

    public void markAllCellsInList(List<Position> list) {
        for (Position pos : list) {
            getCell(pos).mark(true);
        }
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append('\n');

        for (List<Cell> raw : field) {
            for (Cell cell : raw) {
                builder.append(cell.toString());
            }
            builder.append("\n");
        }

        return builder.toString();

    }

}


