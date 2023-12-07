package edu.project3;

import java.nio.file.Path;

public final class Constants {

    private Constants() {
    }

    public static Path localStoragePath = Path.of("./src/main/resources/project3/");
    public static Path webUrlCopyPath = localStoragePath.resolve("web");
    public static Path logsPath = localStoragePath.resolve("logs");
    public static Path reportsPath = localStoragePath.resolve("reports");

}
