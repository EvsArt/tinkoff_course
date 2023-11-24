package edu.project2.model;

public enum SideEnum {

    LEFT,
    TOP,
    RIGHT,
    BOTTOM;

    public SideEnum getOppositeSide() {
        return switch (this) {
            case LEFT -> RIGHT;
            case TOP -> BOTTOM;
            case RIGHT -> LEFT;
            case BOTTOM -> TOP;
        };
    }

}
