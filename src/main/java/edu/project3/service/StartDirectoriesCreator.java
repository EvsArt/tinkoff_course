package edu.project3.service;

import edu.project3.Constants;
import java.io.IOException;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartDirectoriesCreator {

    public void createDirectories() {
        try {
            Files.createDirectories(Constants.webUrlCopyPath);
            Files.createDirectories(Constants.logsPath);
            Files.createDirectories(Constants.reportsPath);
        } catch (IOException e) {
            log.error("Error with creating start directories");
            throw new RuntimeException(e);
        }
    }

}
