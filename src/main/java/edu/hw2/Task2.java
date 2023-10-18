package edu.hw2;

public class Task2 {

    public static class Rectangle {
        private int width;
        private int height;

        final Rectangle setWidth(int width) {
            this.width = width;
            return this;
        }

        final Rectangle setHeight(int height) {
            this.height = height;
            return this;
        }

        double area() {
            return width * height;
        }
    }

    public static class Square extends Rectangle {
        Square setSide(int side) {
            super.setHeight(side);
            super.setWidth(side);
            return this;
        }
    }

}

