package edu.project2.model;

public class DefaultCell implements Cell {

    private final Walls walls;
    private boolean isMarked = false;

    public DefaultCell(Walls walls) {
        this.walls = walls;
    }

    public static DefaultCell getClosedCell() {
        return new DefaultCell(new Walls(true, true, true, true));
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

    public void mark(boolean marking) {
        isMarked = marking;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public Walls getWalls() {
        return walls;
    }

    static class Walls {
        private boolean left;
        private boolean top;
        private boolean right;
        private boolean bottom;

        Walls(boolean left, boolean top, boolean right, boolean bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public boolean isLeft() {
            return left;
        }

        public void setLeft(boolean left) {
            this.left = left;
        }

        public boolean isTop() {
            return top;
        }

        public void setTop(boolean top) {
            this.top = top;
        }

        public boolean isRight() {
            return right;
        }

        public void setRight(boolean right) {
            this.right = right;
        }

        public boolean isBottom() {
            return bottom;
        }

        public void setBottom(boolean bottom) {
            this.bottom = bottom;
        }
    }

}
