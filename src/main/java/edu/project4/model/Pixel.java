package edu.project4.model;

public class Pixel {

    private int hitsCount = 0;
    private Color color = Color.of(0, 0, 0);

    public int getHitsCount() {
        return hitsCount;
    }

    public int incHitsCount() {
        return ++hitsCount;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
