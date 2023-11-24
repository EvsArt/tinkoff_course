package edu.hw6;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task4Test {

    Task4 task4 = new Task4();

    static Path workingDir = Path.of("./src/main/resources/Task4/");
    static String fileName = "MyFile";
    static String outputString = "Programming is learned by writing programs. ― Brian Kernighan";

    @BeforeAll
    static void createDirs() {
        try {
            if (Files.notExists(workingDir)) {
                Files.createDirectories(workingDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void clearDir() throws IOException {
        Files.walk(workingDir)
            .filter(Files::isRegularFile)
            .forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @Test
    void writeString() throws IOException {

        task4.writeString(workingDir.resolve(fileName), outputString);

        InputStream is = Files.newInputStream(workingDir.resolve(fileName));
        byte[] fileContent = is.readAllBytes();
        is.close();
        String s = new String(fileContent, StandardCharsets.UTF_8);

        assertEquals(s, outputString);

    }
}
