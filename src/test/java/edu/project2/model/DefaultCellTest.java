package edu.project2.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("CellTest")
class DefaultCellTest {

    @Test
    @DisplayName("Test Getting closed cell")
    void getClosedCell() {

        DefaultCell cell = DefaultCell.getClosedCell();

        assertThat(cell.getWalls().isLeft()).isTrue();
        assertThat(cell.getWalls().isTop()).isTrue();
        assertThat(cell.getWalls().isRight()).isTrue();
        assertThat(cell.getWalls().isBottom()).isTrue();

    }

    @Test
    @DisplayName("Test Changing ways in cell")
    void changeWay() {

        DefaultCell cell = DefaultCell.getClosedCell();

        cell.changeWay(SideEnum.LEFT, false);
        cell.changeWay(SideEnum.RIGHT, false);

        assertThat(cell.getWalls().isLeft()).isFalse();
        assertThat(cell.getWalls().isTop()).isTrue();
        assertThat(cell.getWalls().isRight()).isFalse();
        assertThat(cell.getWalls().isBottom()).isTrue();
    }
}
