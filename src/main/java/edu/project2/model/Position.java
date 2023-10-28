package edu.project2.model;

import edu.project2.constraints.Constraints;
import java.util.List;

public record Position(int x, int y) {

    public static String positionsListToString(List<Position> positions) {

        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        int counter = 0;

        for (Position pos : positions) {
            builder.append(
                String.format("-> (x: %s, y: %s) ", pos.x(), pos.y())
            );
            if (++counter == Constraints.POSITIONSCOUNTINEACHOUTPUTLINE) {
                builder.append("\n");
                counter = 0;
            }

        }
        return builder.toString();
    }

    public Position getNewPosAfterMoving(SideEnum side) {

        int newX = x();
        int newY = y();

        return switch (side) {
            case LEFT -> new Position(newX - 1, newY);
            case TOP -> new Position(newX, newY - 1);
            case RIGHT -> new Position(newX + 1, newY);
            case BOTTOM -> new Position(newX, newY + 1);
        };

    }

}
