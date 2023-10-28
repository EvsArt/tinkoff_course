package edu.project2.model;

import edu.project2.constraints.symbols.MarkedMazeSymbols;
import edu.project2.constraints.symbols.MazeSymbols;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("CellTest")
class DefaultCellTest {

    @Test
    @DisplayName("Test Getting closed cell")
    void getClosedCell() {

        DefaultCell cell = DefaultCell.getClosedCell();

        assertThat(cell.getWalls().left()).isTrue();
        assertThat(cell.getWalls().top()).isTrue();
        assertThat(cell.getWalls().right()).isTrue();
        assertThat(cell.getWalls().bottom()).isTrue();

    }

    @Test
    @DisplayName("Test Changing ways in cell")
    void changeWay() {

        DefaultCell cell = DefaultCell.getClosedCell();

        cell.changeWay(SideEnum.LEFT, false);
        cell.changeWay(SideEnum.RIGHT, false);

        assertThat(cell.getWalls().left()).isFalse();
        assertThat(cell.getWalls().top()).isTrue();
        assertThat(cell.getWalls().right()).isFalse();
        assertThat(cell.getWalls().bottom()).isTrue();
    }

    @Test
    @DisplayName("Test converting to string")
    void testToString() {
        DefaultCell cell = new DefaultCell(new DefaultCell.DefaultWalls(true, true, true, true));

        String closedCell = cell.toString();
        cell.mark(true);
        String closedMarkedCell = cell.toString();
        cell.mark(false);

        cell.changeWay(SideEnum.TOP, false);
        String topOpenedCell = cell.toString();
        cell.mark(true);
        String topOpenedMarkedCell = cell.toString();

        assertThat(closedCell).isEqualTo(MazeSymbols.CELLCLOSED.getValue());
        assertThat(closedMarkedCell).isEqualTo(MarkedMazeSymbols.CELLCLOSED.getValue());
        assertThat(topOpenedCell).isEqualTo(MazeSymbols.CELLWITHONLYTOPWAY.getValue());
        assertThat(topOpenedMarkedCell).isEqualTo(MarkedMazeSymbols.CELLWITHONLYTOPWAY.getValue());
    }
}
