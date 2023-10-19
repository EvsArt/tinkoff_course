package edu.project2.model;

import edu.project2.exceptions.FieldBorderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
                assertThat(cell.getWalls().isBottom()).isTrue();
                assertThat(cell.getWalls().isRight()).isTrue();
                assertThat(cell.getWalls().isLeft()).isTrue();
                assertThat(cell.getWalls().isTop()).isTrue();
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
        assertThat(cell.getWalls().isTop()).isFalse();
        assertThat(cell.getWalls().isLeft()).isFalse();
        assertThat(cell.getWalls().isRight()).isFalse();
        assertThat(cell.getWalls().isBottom()).isFalse();

        cell = (DefaultCell) field.getCell(new Position(0, 0));    // non-active cell
        assertThat(cell.getWalls().isLeft()).isTrue();
        assertThat(cell.getWalls().isRight()).isTrue();
        assertThat(cell.getWalls().isTop()).isTrue();
        assertThat(cell.getWalls().isBottom()).isTrue();

        cell = (DefaultCell) field.getCell(new Position(0, 1));    // left cell
        assertThat(cell.getWalls().isLeft()).isTrue();
        assertThat(cell.getWalls().isRight()).isFalse();
        assertThat(cell.getWalls().isTop()).isTrue();
        assertThat(cell.getWalls().isBottom()).isTrue();

        cell = (DefaultCell) field.getCell(new Position(2, 1));    // right cell
        assertThat(cell.getWalls().isLeft()).isFalse();
        assertThat(cell.getWalls().isRight()).isTrue();
        assertThat(cell.getWalls().isTop()).isTrue();
        assertThat(cell.getWalls().isBottom()).isTrue();

        cell = (DefaultCell) field.getCell(new Position(1, 0));    // top cell
        assertThat(cell.getWalls().isLeft()).isTrue();
        assertThat(cell.getWalls().isRight()).isTrue();
        assertThat(cell.getWalls().isTop()).isTrue();
        assertThat(cell.getWalls().isBottom()).isFalse();

        cell = (DefaultCell) field.getCell(new Position(1, 2));    // bottom cell
        assertThat(cell.getWalls().isLeft()).isTrue();
        assertThat(cell.getWalls().isRight()).isTrue();
        assertThat(cell.getWalls().isTop()).isFalse();
        assertThat(cell.getWalls().isBottom()).isTrue();

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
    @DisplayName("Test converting to string")
    void testToString() {
        // TODO: 20.10.2023
    }
}
