package edu.hw6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("Test cloning file")
    void cloneFile() {

        String fileName = "file";
        String fileExtension = ".txt";
        Path filePath = parentPath.resolve(fileName + fileExtension);

        try {
            Files.list(parentPath).forEach(it -> {
                try {
                    Files.delete(it);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Files.deleteIfExists(parentPath);
            Files.createDirectory(parentPath);
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

            assertThat(resFileNames.containsAll(expFileNames)).isTrue();
            assertThat(resFileNames.size()).isEqualTo(expFileNames.size());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @DisplayName("Negative tests cloning file")
    void cloneFileNegative() {

        try {
            Files.list(parentPath).forEach(it -> {
                try {
                    Files.delete(it);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            Files.deleteIfExists(parentPath);
            Files.createDirectory(parentPath);
            Files.createFile(parentPath.resolve("a - копия"));
            task2.cloneFile(parentPath.resolve("a - копия"));

            assertThat(Files.exists(parentPath.resolve("a - копия (2)"))).isTrue();
            assertThrows(FileNotFoundException.class, () -> task2.cloneFile(parentPath.resolve("notExist")));
            assertThrows(NullPointerException.class, () -> task2.cloneFile(null));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

        assertThat(res1).isEqualTo("file - копия");
        assertThat(res2).isEqualTo("file - копия.txt");
        assertThat(res3).isEqualTo("file - копия.");
        assertThat(res21).isEqualTo("file - копия (2).txt");
        assertThat(res22).isEqualTo("file - копия (3).txt");

        assertThrows(IllegalArgumentException.class, () -> task2.getNewCopyName(file4));
        assertThrows(NullPointerException.class, () -> task2.getNewCopyName(file5));

    }
}
