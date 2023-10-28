package edu.project2.model;

public interface Cell {

    void changeWay(SideEnum side, boolean closing);

    Walls getWalls();

    void setWalls(Walls walls);

    void mark(boolean marking);

    boolean isMarked();
}
