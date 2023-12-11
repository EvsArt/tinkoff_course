package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.utils.ImageUtils;
import edu.project4.utils.MultiThreadRenderer;
import edu.project4.utils.Renderer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    private static final int NANO_SEC_IN_MILLI_SEC = 1000000;

    public void run(String[] args) {

        Path imgDir = Constants.ROOT_IMAGE_PATH;

        Renderer renderer1 = new Renderer();
        Renderer renderer2 = new MultiThreadRenderer();
        try {
            FractalImage image = FractalImage.create(Constants.X_RES, Constants.Y_RES);
//            long time1 = System.nanoTime();
//            renderer1.render(
//                Constants.SAMPLE_COUNT,
//                Constants.AFFINE_FUNCTIONS_COUNT,
//                Constants.ITERATIONS_IN_SAMPLE,
//                Constants.SYMMETRIC_VALUE,
//                image
//            );
//            renderer1.correction(image);
//            Files.createDirectories(imgDir);
//            ImageUtils.save(
//                image,
//                imgDir.resolve(UUID.randomUUID() + "." + Constants.IMAGE_FORMAT),
//                Constants.THREAD_COUNT
//            );
//            time1 = System.nanoTime() - time1;

            long time2 = System.nanoTime();
            image = FractalImage.create(Constants.X_RES, Constants.Y_RES);
            renderer2.render(
                Constants.SAMPLE_COUNT,
                Constants.AFFINE_FUNCTIONS_COUNT,
                Constants.ITERATIONS_IN_SAMPLE,
                Constants.SYMMETRIC_VALUE,
                image
            );
            renderer2.correction(image);
            time2 = System.nanoTime() - time2;
            ImageUtils.multiThreadSave(
                image,
                imgDir.resolve(UUID.randomUUID() + "." + Constants.IMAGE_FORMAT),
                Constants.THREAD_COUNT
            );
            Files.createDirectories(imgDir);

//            log.info("One thread: " + time1 / NANO_SEC_IN_MILLI_SEC + "ms");
            log.info(Constants.THREAD_COUNT + " threads: " + time2 / NANO_SEC_IN_MILLI_SEC + "ms");
//            log.info(String.valueOf((double) time1 / time2));
        } catch (IOException e) {
            log.error("Error with writing image to dir {}", imgDir);
            throw new RuntimeException(e);
        }

    }

}
