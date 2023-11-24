package edu.project2.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    @DisplayName("Test converting positions list to string")
    void positionsListToString() {

        List<Position> positions = List.of(
            new Position(0, 1),
            new Position(1, 1),
            new Position(1, 2)
        );

        String expectedRes = "\n-> (x: 0, y: 1) -> (x: 1, y: 1) -> (x: 1, y: 2) ";

        String res = Position.positionsListToString(positions);

        assertThat(res).isEqualTo(expectedRes);

    }

    @Test
    void getNewPosAfterMoving() {

        Position startPos = new Position(2, 2);

        Position expLeftPos = new Position(1, 2);
        Position expTopPos = new Position(2, 1);
        Position expRightPos = new Position(3, 2);
        Position expBottomPos = new Position(2, 3);

        Position leftPos = startPos.getNewPosAfterMoving(SideEnum.LEFT);
        Position topPos = startPos.getNewPosAfterMoving(SideEnum.TOP);
        Position rightPos = startPos.getNewPosAfterMoving(SideEnum.RIGHT);
        Position bottomPos = startPos.getNewPosAfterMoving(SideEnum.BOTTOM);

        assertThat(leftPos).isEqualTo(expLeftPos);
        assertThat(topPos).isEqualTo(expTopPos);
        assertThat(rightPos).isEqualTo(expRightPos);
        assertThat(bottomPos).isEqualTo(expBottomPos);

    }
}
