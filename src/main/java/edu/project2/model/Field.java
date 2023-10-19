package edu.project2.model;

import edu.project2.exceptions.FieldBorderException;

public interface Field {

    void changeWay(Position pos, SideEnum side, boolean closing) throws FieldBorderException;

}
