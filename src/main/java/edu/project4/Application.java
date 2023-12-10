package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import java.util.Arrays;

public class Application {

    public void run(String[] args) {

        Renderer renderer = new Renderer();
        renderer.render(100, 20, 20, FractalImage.create(1080, 1080));


    }

}
