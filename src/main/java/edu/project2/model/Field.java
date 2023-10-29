package edu.project2.model;

import edu.project2.exceptions.FieldBorderException;
import java.util.List;

public interface Field {

    void changeWay(Position pos, SideEnum side, boolean closing) throws FieldBorderException;

    Cell getCell(Position position);

    int getHeight();

    int getWidth();

    void unmarkAllCells();

    void closeAllCells();

    void openAllCells();

    void markAllCellsInList(List<Position> positions);

}
