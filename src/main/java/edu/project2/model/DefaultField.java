package edu.project2.model;

import edu.project2.constraints.StringConstraints;
import edu.project2.exceptions.FieldBorderException;
import java.util.ArrayList;

public class DefaultField implements Field {

    private final int height;
    private final int width;
    private final ArrayList<ArrayList<Cell>> field;

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

    public ArrayList<ArrayList<Cell>> getField() {
        return field;
    }

    public Cell getCell(Position pos) {
        return field.get(pos.y()).get(pos.x());
    }

    @Override
    public void changeWay(Position pos, SideEnum side, boolean closing) throws FieldBorderException {

        switch (side) {
            case LEFT -> {
                if (pos.x() <= 0) {
                    throw new FieldBorderException(StringConstraints.attemptToRemovingBorderAt(pos.x(), pos.y(), side));
                }
                field.get(pos.y()).get(pos.x()).changeWay(SideEnum.LEFT, closing);
                field.get(pos.y()).get(pos.x() - 1).changeWay(SideEnum.RIGHT, closing);
            }
            case TOP -> {
                if (pos.y() <= 0) {
                    throw new FieldBorderException(StringConstraints.attemptToRemovingBorderAt(pos.x(), pos.y(), side));
                }
                field.get(pos.y()).get(pos.x()).changeWay(SideEnum.TOP, closing);
                field.get(pos.y() - 1).get(pos.x()).changeWay(SideEnum.BOTTOM, closing);
            }
            case RIGHT -> {
                if (pos.x() >= width - 1) {
                    throw new FieldBorderException(StringConstraints.attemptToRemovingBorderAt(pos.x(), pos.y(), side));
                }
                field.get(pos.y()).get(pos.x()).changeWay(SideEnum.RIGHT, closing);
                field.get(pos.y()).get(pos.x() + 1).changeWay(SideEnum.LEFT, closing);
            }
            case BOTTOM -> {
                if (pos.y() >= height - 1) {
                    throw new FieldBorderException(StringConstraints.attemptToRemovingBorderAt(pos.x(), pos.y(), side));
                }
                field.get(pos.y()).get(pos.x()).changeWay(SideEnum.BOTTOM, closing);
                field.get(pos.y() + 1).get(pos.x()).changeWay(SideEnum.TOP, closing);
            }
            default -> {
            }
        }

    }

    @Override
    public String toString() {
        // TODO: 20.10.2023 create pretty print
        return field.toString();
    }

}


