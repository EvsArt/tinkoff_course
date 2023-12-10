package edu.project4.model;

public record FractalImage(Pixel[][] data, int width, int height) {

    public static FractalImage create(int width, int height) {
        return new FractalImage(new Pixel[height][width], width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel getPixel(int x, int y) {
        if (data[y][x] == null) {
            data[y][x] = new Pixel();
        }
        return data[y][x];
    }

    public boolean isNull(int x, int y) {
        return data[y][x] == null;
    }

}
