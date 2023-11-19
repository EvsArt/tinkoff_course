package edu.project3.service;

import edu.project3.Constants;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartDirectoriesCreator {

    Logger logger = LogManager.getLogger();

    public void createDirectories() {
        try {
            Files.createDirectories(Constants.webUrlCopyPath);
            Files.createDirectories(Constants.logsPath);
            Files.createDirectories(Constants.reportsPath);
        } catch (IOException e) {
            logger.error("Error with creating start directories");
            throw new RuntimeException(e);
        }
    }

}
