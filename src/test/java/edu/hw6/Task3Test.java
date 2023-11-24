package edu.hw6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static edu.hw6.Task3.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Task3Test {

    private static final Path filesDir = Path.of("./src/main/resources/Task3/");

    @BeforeAll
    static void createDirs() throws IOException {
        if (Files.exists(filesDir)) {
            removeDir();
        }
        Files.createDirectories(filesDir);
    }

    @BeforeEach
    public void clearDir() throws IOException {
        Files.walk(filesDir)
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
    public static void removeDir() throws IOException {
        Files.walk(filesDir)
            .filter(Files::isRegularFile)
            .forEach(it -> {
                try {
                    Files.delete(it);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        Files.deleteIfExists(filesDir);
    }

    @Test
    public void testAbstractFilter() throws IOException {

        createPNGFileWithSize("oneFile.png", 120_000);
        createPNGFileWithSize("twoFile.png", 110_000);
        createPNGFileWithSize("threeFile.png", 90_000);
        createNotPNGFileWithSize("nottwoFile.png", 130_000);

        DirectoryStream.Filter<Path> filter = regularFile
            .and(readable)
            .and(largerThan(100_000))
            .and(magicNumber(0x89, 'P', 'N', 'G'))
            .and(globMatches("*.png"))
            .and(regexContains("two"));

        DirectoryStream<Path> entries = Files.newDirectoryStream(filesDir, filter);
        Iterator<Path> iter = entries.iterator();
        Path filteredFile = iter.next();
        boolean hasOneMoreFiles = iter.hasNext();
        entries.close();

        assertEquals(filteredFile, filesDir.resolve("twoFile.png"));
        assertFalse(hasOneMoreFiles);

    }

    @Test
    public void testSimpleAbstractFilter() throws IOException {

        createPNGFileWithSize("oneFile.png", 120_000);
        createPNGFileWithSize("twoFile.png", 110_000);
        createPNGFileWithSize("threeFile.png", 90_000);
        createNotPNGFileWithSize("notPngFile.png", 130_000);

        DirectoryStream.Filter<Path> filter = regularFile;

        List<Path> res = new ArrayList<>();
        DirectoryStream<Path> entries = Files.newDirectoryStream(filesDir, filter);
        entries.iterator().forEachRemaining(res::add);
        entries.close();

        List<Path> expRes = List.of(
            Path.of(filesDir.resolve("oneFile.png").toString()),
            Path.of(filesDir.resolve("twoFile.png").toString()),
            Path.of(filesDir.resolve("threeFile.png").toString()),
            Path.of(filesDir.resolve("notPngFile.png").toString())
        );

        assertEquals(Set.copyOf(res), Set.copyOf(expRes));

    }

    private void createPNGFileWithSize(String name, long size) throws IOException {

        Path filePath = filesDir.resolve(name);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        Files.createFile(filePath);
        RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "rw");

        ByteBuffer pngFileHeader = ByteBuffer.wrap(new byte[] {(byte) 0x89, 'P', 'N', 'G'});
        file.getChannel().write(pngFileHeader);

        file.setLength(size);
        file.close();

    }

    private void createNotPNGFileWithSize(String name, long size) throws IOException {

        Path filePath = filesDir.resolve(name);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        Files.createFile(filePath);

        RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "rw");
        file.setLength(size);
        file.close();

    }

}
