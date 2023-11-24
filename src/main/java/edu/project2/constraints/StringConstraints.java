package edu.project2.constraints;

import edu.project2.model.SideEnum;

public final class StringConstraints {

    private StringConstraints() {
    }

    public static String attemptToRemovingBorderAt(int posX, int posY, SideEnum side) {
        return String.format("Attempt to remove %s border of field at {x=%d, y=%d}", side.toString(), posX, posY);
    }

}
