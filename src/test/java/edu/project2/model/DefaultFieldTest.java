package edu.project2.model;

import edu.project2.exceptions.FieldBorderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("FieldTest")
class DefaultFieldTest {

    @Test
    @DisplayName("Test creating field")
    void createField() {

        int height = 3;
        int width = 3;

        DefaultField field = new DefaultField(height, width);

        assertThat(field.getField().size()).isEqualTo(height);
        for (int i = 0; i < height; i++) {
            assertThat(field.getField().get(i).size()).isEqualTo(width);
            for (int j = 0; j < width; j++) {
                DefaultCell cell = (DefaultCell) field.getCell(new Position(j, i));
                assertThat(cell.getWalls().bottom()).isTrue();
                assertThat(cell.getWalls().right()).isTrue();
                assertThat(cell.getWalls().left()).isTrue();
                assertThat(cell.getWalls().top()).isTrue();
            }
        }

    }

    @Test
    @DisplayName("Test changing way")
    void changeWay() {

        DefaultField field = new DefaultField(3, 3);
        field.changeWay(new Position(1, 1), SideEnum.LEFT, false);
        field.changeWay(new Position(1, 1), SideEnum.RIGHT, false);
        field.changeWay(new Position(1, 1), SideEnum.TOP, false);
        field.changeWay(new Position(1, 1), SideEnum.BOTTOM, false);

        DefaultCell cell;

        cell = (DefaultCell) field.getCell(new Position(1, 1));    // center
        assertThat(cell.getWalls().top()).isFalse();
        assertThat(cell.getWalls().left()).isFalse();
        assertThat(cell.getWalls().right()).isFalse();
        assertThat(cell.getWalls().bottom()).isFalse();

        cell = (DefaultCell) field.getCell(new Position(0, 0));    // non-active cell
        assertThat(cell.getWalls().left()).isTrue();
        assertThat(cell.getWalls().right()).isTrue();
        assertThat(cell.getWalls().top()).isTrue();
        assertThat(cell.getWalls().bottom()).isTrue();

        cell = (DefaultCell) field.getCell(new Position(0, 1));    // left cell
        assertThat(cell.getWalls().left()).isTrue();
        assertThat(cell.getWalls().right()).isFalse();
        assertThat(cell.getWalls().top()).isTrue();
        assertThat(cell.getWalls().bottom()).isTrue();

        cell = (DefaultCell) field.getCell(new Position(2, 1));    // right cell
        assertThat(cell.getWalls().left()).isFalse();
        assertThat(cell.getWalls().right()).isTrue();
        assertThat(cell.getWalls().top()).isTrue();
        assertThat(cell.getWalls().bottom()).isTrue();

        cell = (DefaultCell) field.getCell(new Position(1, 0));    // top cell
        assertThat(cell.getWalls().left()).isTrue();
        assertThat(cell.getWalls().right()).isTrue();
        assertThat(cell.getWalls().top()).isTrue();
        assertThat(cell.getWalls().bottom()).isFalse();

        cell = (DefaultCell) field.getCell(new Position(1, 2));    // bottom cell
        assertThat(cell.getWalls().left()).isTrue();
        assertThat(cell.getWalls().right()).isTrue();
        assertThat(cell.getWalls().top()).isFalse();
        assertThat(cell.getWalls().bottom()).isTrue();

    }

    @Test
    @DisplayName("Test change border")
    void changeBorder() {

        DefaultField field = new DefaultField(3, 3);

        assertThrows(FieldBorderException.class, () -> field.changeWay(new Position(1, 2), SideEnum.BOTTOM, false));
        assertThrows(FieldBorderException.class, () -> field.changeWay(new Position(1, 0), SideEnum.TOP, false));
        assertThrows(FieldBorderException.class, () -> field.changeWay(new Position(0, 1), SideEnum.LEFT, false));
        assertThrows(FieldBorderException.class, () -> field.changeWay(new Position(2, 1), SideEnum.RIGHT, false));

    }

    @Test
    @DisplayName("Test unmarking cells")
    void unmarkAllCells() {

        int height = 5;
        int width = 5;

        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(width - 1, height - 1);
        Position pos3 = new Position(2, 2);

        DefaultField field = new DefaultField(5, 5);
        field.getCell(pos1).mark(true);
        field.getCell(pos2).mark(true);
        field.getCell(pos3).mark(true);

        field.unmarkAllCells();
        boolean res =
            field.getCell(pos1).isMarked()
                || field.getCell(pos2).isMarked()
                || field.getCell(pos3).isMarked();

        assertThat(res).isFalse();

    }

    @Test
    @DisplayName("Test closing all cells")
    void closeAllCells() {

        int height = 5;
        int width = 5;

        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(width - 1, height - 1);
        Position pos3 = new Position(2, 2);

        DefaultField field = new DefaultField(5, 5);
        field.getCell(pos1).getWalls().setTop(false);
        field.getCell(pos1).getWalls().setBottom(false);
        field.getCell(pos2).getWalls().setRight(false);
        field.getCell(pos2).getWalls().setTop(false);
        field.getCell(pos3).getWalls().setRight(false);
        field.getCell(pos3).getWalls().setLeft(false);

        field.closeAllCells();

        boolean[] closedCell = new boolean[] {true, true, true, true};

        boolean res =
            Arrays.equals(field.getCell(pos1).getWalls().getWallsAsBoolArray(), closedCell)
                && Arrays.equals(field.getCell(pos2).getWalls().getWallsAsBoolArray(), closedCell)
                && Arrays.equals(field.getCell(pos3).getWalls().getWallsAsBoolArray(), closedCell);

        assertThat(res).isTrue();

    }

    @Test
    @DisplayName("Test converting to string")
    void testToString() {

        DefaultField field = new DefaultField(20, 20);

        String expectedNewFieldString =
            """

                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                """;

        String newFieldString = field.toString();

        field.changeWay(new Position(0, 0), SideEnum.RIGHT, false);
        field.changeWay(new Position(0, 0), SideEnum.BOTTOM, false);
        field.changeWay(new Position(1, 0), SideEnum.LEFT, false);
        field.changeWay(new Position(2, 0), SideEnum.LEFT, false);
        field.changeWay(new Position(2, 1), SideEnum.TOP, false);
        field.changeWay(new Position(2, 1), SideEnum.LEFT, false);
        field.changeWay(new Position(2, 1), SideEnum.BOTTOM, false);
        field.changeWay(new Position(2, 1), SideEnum.RIGHT, false);
        field.getCell(new Position(0, 0)).mark(true);
        field.getCell(new Position(1, 2)).mark(true);
        field.getCell(new Position(4, 4)).mark(true);
        field.getCell(new Position(0, 1)).mark(true);

        String expectedFieldAfterChangesString =
            """

                ⡿⠏⣉⣉⠉⢹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣷⣾⣏⣉⠀⠀⣉⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣿⣿⣇⣸⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣿⣿⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                ⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹⣏⣹
                """;

        String fieldAfterChangesString = field.toString();

        assertThat(newFieldString).isEqualTo(expectedNewFieldString);
        assertThat(fieldAfterChangesString).isEqualTo(expectedFieldAfterChangesString);

    }
}
