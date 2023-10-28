package edu.project2.model;

public interface Walls {

    boolean left();

    void setLeft(boolean left);

    boolean top();

    void setTop(boolean top);

    boolean right();

    void setRight(boolean right);

    boolean bottom();

    void setBottom(boolean bottom);

    boolean[] getWallsAsBoolArray();

}
