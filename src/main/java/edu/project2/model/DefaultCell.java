package edu.project2.model;

import edu.project2.constraints.symbols.MarkedMazeSymbols;
import edu.project2.constraints.symbols.MazeSymbols;
import edu.project2.constraints.symbols.Symbols;
import edu.project2.exceptions.SymbolNotFoundException;
import java.util.Arrays;
import java.util.Optional;

public class DefaultCell implements Cell {

    private Walls walls;
    private boolean isMarked = false;

    public DefaultCell(DefaultWalls walls) {
        this.walls = walls;
    }

    public static DefaultCell getClosedCell() {
        return new DefaultCell(new DefaultWalls(true, true, true, true));
    }

    public static DefaultCell getCell(boolean left, boolean top, boolean right, boolean bottom) {
        return new DefaultCell(new DefaultWalls(left, top, right, bottom));
    }

    @Override
    public void changeWay(SideEnum side, boolean closing) {
        switch (side) {
            case LEFT -> walls.setLeft(closing);
            case TOP -> walls.setTop(closing);
            case RIGHT -> walls.setRight(closing);
            case BOTTOM -> walls.setBottom(closing);
            default -> {
            }
        }
    }

    @Override
    public void mark(boolean marking) {
        isMarked = marking;
    }

    @Override
    public boolean isMarked() {
        return isMarked;
    }

    @Override
    public Walls getWalls() {
        return walls;
    }

    @Override
    public void setWalls(Walls walls) {
        this.walls = walls;
    }

    @Override
    public String toString() {

        Symbols[] symbolsArray;
        if (isMarked) {
            symbolsArray = MarkedMazeSymbols.values();
        } else {
            symbolsArray = MazeSymbols.values();
        }

        Optional<Symbols> symbol =
            Arrays.stream(symbolsArray).filter((Symbols it) -> Arrays.equals(
                it.getCell().getWalls().getWallsAsBoolArray(),
                getWalls().getWallsAsBoolArray()
            )).findFirst();

        if (symbol.isEmpty()) {
            throw new SymbolNotFoundException(String.format(
                "Symbol with walls left: %b, top: %b, right: %b, bottom: %b not found!",
                walls.left(),
                walls.top(),
                walls.right(),
                walls.bottom()
            ));
        }

        return symbol.get().getValue();

    }

    static class DefaultWalls implements Walls {
        private boolean left;
        private boolean top;
        private boolean right;
        private boolean bottom;

        DefaultWalls(boolean left, boolean top, boolean right, boolean bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public boolean left() {
            return left;
        }

        public void setLeft(boolean left) {
            this.left = left;
        }

        public boolean top() {
            return top;
        }

        public void setTop(boolean top) {
            this.top = top;
        }

        public boolean right() {
            return right;
        }

        public void setRight(boolean right) {
            this.right = right;
        }

        public boolean bottom() {
            return bottom;
        }

        public void setBottom(boolean bottom) {
            this.bottom = bottom;
        }

        public boolean[] getWallsAsBoolArray() {
            return new boolean[] {left, top, right, bottom};
        }

    }

}
