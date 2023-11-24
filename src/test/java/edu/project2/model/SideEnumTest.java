package edu.project2.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SideEnumTest {

    @Test
    void getOppositeSide() {

        SideEnum leftSide = SideEnum.LEFT;
        SideEnum rightSide = SideEnum.RIGHT;
        SideEnum topSide = SideEnum.TOP;
        SideEnum bottomSide = SideEnum.BOTTOM;

        assertThat(leftSide.getOppositeSide()).isEqualTo(rightSide);
        assertThat(rightSide.getOppositeSide()).isEqualTo(leftSide);
        assertThat(topSide.getOppositeSide()).isEqualTo(bottomSide);
        assertThat(bottomSide.getOppositeSide()).isEqualTo(topSide);

    }
}
