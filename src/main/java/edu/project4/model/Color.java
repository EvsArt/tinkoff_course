package edu.project4.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Color implements Cloneable {

    private Color(){
    }

    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private int brightness = 100;

    public static Color of(int r, int g, int b) {
        Color res = new Color();
        res.setRed(r);
        res.setGreen(g);
        res.setBlue(b);
        return res;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        if(red < 0 || red > 255) {
            log.error("Illegal value of red ({})", red);
            throw new IllegalArgumentException("Red value must be between 0 and 255!");
        }
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        if(green < 0 || green > 255) {
            log.error("Illegal value of green ({})", green);
            throw new IllegalArgumentException("Green value must be between 0 and 255!");
        }
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        if(blue < 0 || blue > 255) {
            log.error("Illegal value of blue ({})", blue);
            throw new IllegalArgumentException("Blue value must be between 0 and 255!");
        }
        this.blue = blue;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        if(brightness < 0 || brightness > 100) {
            log.error("Illegal value of brightness ({})", brightness);
            throw new IllegalArgumentException("Brightness value must be between 0 and 100!");
        }
        this.brightness = brightness;
    }

    @Override public Color clone() {
        return Color.of(red, green, blue);
    }
}
