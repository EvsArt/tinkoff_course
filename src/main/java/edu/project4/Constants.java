package edu.project4;

import java.nio.file.Path;

public final class Constants {

    private Constants() {
    }

    public static final Path ROOT_IMAGE_PATH = Path.of("./", "src", "main", "resources", "project4", "img");
    public static final String IMAGE_FORMAT = "png";
    public static final int X_RES = 1920;
    public static final int Y_RES = 1080;
    public static final int SAMPLE_COUNT = 1000000;
    public static final int ITERATIONS_IN_SAMPLE = 60;
    public static final int AFFINE_FUNCTIONS_COUNT = 7;
    public static final int SYMMETRIC_VALUE = 1;
    public static final int THREAD_COUNT = 16;
    public static final double GAMMA_CORRECTION = 1.4;
    public static final int MAX_COLOR_VALUE = 255;


}
