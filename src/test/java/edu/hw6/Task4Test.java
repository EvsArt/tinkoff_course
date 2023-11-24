package edu.hw6;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task4Test {

    Task4 task4 = new Task4();

    private static Path WORKING_DIR = Path.of("./src/main/resources/Task4/");
    private static String FILE_NAME = "MyFile";
    private static String OUTPUT_STRING = "Programming is learned by writing programs. ― Brian Kernighan";

    @BeforeAll
    static void createDirs() throws IOException {
        if (Files.exists(WORKING_DIR)) {
            removeDir();
        }
        Files.createDirectories(WORKING_DIR);
    }

    @BeforeEach
    void clearDir() throws IOException {
        Files.walk(WORKING_DIR)
            .filter(Files::isRegularFile)
            .forEach(it -> {
                try {
                    Files.delete(it);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @AfterAll
    static void removeDir() throws IOException {
        Files.walk(WORKING_DIR)
            .filter(Files::isRegularFile)
            .forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        Files.deleteIfExists(WORKING_DIR);
    }

    @Test
    void writeString() throws IOException {

        task4.writeString(WORKING_DIR.resolve(FILE_NAME), OUTPUT_STRING);

        InputStream is = Files.newInputStream(WORKING_DIR.resolve(FILE_NAME));
        byte[] fileContent = is.readAllBytes();
        is.close();
        String s = new String(fileContent, StandardCharsets.UTF_8);

        assertEquals(s, OUTPUT_STRING);

    }
}
