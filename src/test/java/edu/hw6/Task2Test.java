package edu.hw6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.imageio.IIOException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task2Test {

    Task2 task2 = new Task2();

    private static final String ROOT_DIR = "./src/main/resources/";
    private static final String WORKING_DIR = "Task2/";
    private static final Path parentPath = Path.of(ROOT_DIR, WORKING_DIR);

    @BeforeAll
    static void createDirs() {
        try {
            if (Files.notExists(parentPath)) {
                Files.createDirectories(parentPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void clearDir() throws IOException {
        Files.walk(parentPath)
            .filter(Files::isRegularFile)
            .forEach(it -> {
            try {
                Files.delete(it);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Files.deleteIfExists(parentPath);
    }

    @BeforeEach
    void createDir() throws IOException {
        Files.createDirectories(parentPath);
    }

    @Test
    @DisplayName("Test cloning file")
    void cloneFile() throws IOException {

        String fileName = "file";
        String fileExtension = ".txt";
        Path filePath = parentPath.resolve(fileName + fileExtension);

        Files.createFile(filePath);
        task2.cloneFile(filePath);
        task2.cloneFile(filePath);
        task2.cloneFile(filePath);
        task2.cloneFile(filePath);
        task2.cloneFile(filePath);

        List<String> resFileNames = Files.list(parentPath).map(it -> it.getFileName().toString()).toList();

        List<String> expFileNames = List.of(
            fileName + fileExtension,
            fileName + " - копия" + fileExtension,
            fileName + " - копия (2)" + fileExtension,
            fileName + " - копия (3)" + fileExtension,
            fileName + " - копия (4)" + fileExtension,
            fileName + " - копия (5)" + fileExtension
        );

        assertEquals(Set.copyOf(resFileNames), Set.copyOf(expFileNames));

    }

    @Test
    @DisplayName("Negative tests cloning file")
    void cloneFileNegative() throws IOException {

        Path newFile = parentPath.resolve("a - копия");

        Files.createFile(newFile);
        task2.cloneFile(newFile);

        assertTrue(Files.exists(parentPath.resolve("a - копия (2)")));
        assertThrows(FileNotFoundException.class, () -> task2.cloneFile(parentPath.resolve("notExist")));
        assertThrows(NullPointerException.class, () -> task2.cloneFile(null));

    }

    @Test
    @DisplayName("Test getting copy-name of file")
    void getNewCopyName() {

        String file1 = "file";
        String file2 = "file.txt";
        String file3 = "file.";
        String file4 = "";
        String file5 = null;

        String res1 = task2.getNewCopyName(file1);
        String res2 = task2.getNewCopyName(file2);
        String res3 = task2.getNewCopyName(file3);
        String res21 = task2.getNewCopyName(res2);
        String res22 = task2.getNewCopyName(res21);

        assertEquals(res1, "file - копия");
        assertEquals(res2, "file - копия.txt");
        assertEquals(res3, "file - копия.");
        assertEquals(res21, "file - копия (2).txt");
        assertEquals(res22, "file - копия (3).txt");

        assertThrows(IllegalArgumentException.class, () -> task2.getNewCopyName(file4));
        assertThrows(NullPointerException.class, () -> task2.getNewCopyName(file5));

    }
}
