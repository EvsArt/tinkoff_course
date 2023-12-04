package edu.hw9.task2;

import edu.hw9.task2.TreeSearcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeSearcherTest {

    private static final Path root = Path.of("./src", "main", "resources", "hw9");
    private static final Path dir0 = root.resolve("dir0");
    private static final Path dir1 = root.resolve("dir1");
    private static final Path dir10 = dir1.resolve("dir10");

    @BeforeAll
    static void createTestFiles() throws IOException {

        Files.createDirectories(root);
        clearDir(root);

        Files.createDirectory(dir0);
        createNFilesInPath(dir0, 1000);

        Files.createDirectory(dir1);
        createNFilesInPath(dir1, 1001);

        Files.createDirectory(dir10);
        createNFilesInPath(dir10, 1001);
    }

    @AfterAll
    static void removeTestFiles() {
        clearDir(root);
    }

    private static void clearDir(Path path) {
        try {
            Files.walk(path)
                .skip(1)
                .forEach((p) -> {
                    if (Files.isDirectory(p)) {
                        clearDir(p);
                    }
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createNFilesInPath(Path path, int n) throws IOException {
        for (int i = 0; i < n; i++) {
            Path newFile = path.resolve("file" + i);
            if (Files.notExists(newFile)) {
                Files.createFile(newFile);
            }
        }
    }

    private final Predicate<Path> dirsCondition = (path -> {
        try {
            return Files.isDirectory(path)
                && Files.list(path).filter(Files::isRegularFile).count() > 1000;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    });

    @Test
    void DirectoriesSearcherTest() {
        TreeSearcher processor = new TreeSearcher(root, dirsCondition);
        Set<Path> expRes = Set.of(dir1, dir10);

        Set<Path> res = processor.compute();

        assertEquals(expRes, res);
    }

    private final Predicate<Path> filesCondition = (path ->
        Files.isRegularFile(path)
            && path.getFileName().toString().equals("file14")
    );

    @Test
    void FilesSearcherTest() {
        TreeSearcher processor = new TreeSearcher(root, filesCondition);

        Set<Path> expRes = Set.of(
            dir1.resolve("file14"),
            dir10.resolve("file14"),
            dir0.resolve("file14")
        );

        Set<Path> res = processor.compute();

        assertEquals(expRes, res);
    }

}
