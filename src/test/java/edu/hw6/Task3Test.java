package edu.hw6;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static edu.hw6.Task3.globMatches;
import static edu.hw6.Task3.largerThan;
import static edu.hw6.Task3.magicNumber;
import static edu.hw6.Task3.readable;
import static edu.hw6.Task3.regexContains;
import static edu.hw6.Task3.regularFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task3Test {

    private static final Path filesDir = Path.of("./src/main/resources/Task3/");

    @BeforeAll
    static void createDirs() {
        try {
            if (Files.notExists(filesDir)) {
                Files.createDirectories(filesDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void clearDir() {
        try {
            Files.list(filesDir)
                .forEach(it -> {
                    try {
                        Files.delete(it);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAbstractFilter() {
        DirectoryStream.Filter<Path> filter = regularFile
            .and(readable)
            .and(largerThan(100_000))
            .and(magicNumber(0x89, 'P', 'N', 'G'))
            .and(globMatches("*.png"))
            .and(regexContains("two"));

        createPNGFileWithSize("oneFile.png", 120_000);
        createPNGFileWithSize("twoFile.png", 110_000);
        createPNGFileWithSize("threeFile.png", 90_000);
        createNotPNGFileWithSize("nottwoFile.png", 130_000);

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(filesDir, filter)) {
            Iterator<Path> iter = entries.iterator();
            assertThat(iter.next()).isEqualTo(filesDir.resolve("twoFile.png"));
            assertThat(iter.hasNext()).isFalse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testSimpleAbstractFilter() {
        DirectoryStream.Filter<Path> filter = regularFile;

        createPNGFileWithSize("oneFile.png", 120_000);
        createPNGFileWithSize("twoFile.png", 110_000);
        createPNGFileWithSize("threeFile.png", 90_000);
        createNotPNGFileWithSize("nottwoFile.png", 130_000);

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(filesDir, filter)) {
            List<Path> res = new ArrayList<>();
            entries.iterator().forEachRemaining(res::add);
            assertThat(res.size()).isEqualTo(4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void createPNGFileWithSize(String name, long size) {

        Path filePath = filesDir.resolve(name);
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            Files.createFile(filePath);
            RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "rw");

            ByteBuffer pngFileHeader = ByteBuffer.wrap(new byte[] {(byte) 0x89, 'P', 'N', 'G'});
            file.getChannel().write(pngFileHeader);

            file.setLength(size);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void createNotPNGFileWithSize(String name, long size) {

        Path filePath = filesDir.resolve(name);
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            Files.createFile(filePath);

            RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "rw");
            file.setLength(size);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
