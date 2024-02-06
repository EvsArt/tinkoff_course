package edu.hw10.task2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class DiskMap implements Map<Object, Object> {
    private static final int FILE_READER_BUFFER_SIZE = 4096;

    private final Path mapDir;

    public DiskMap(Path mapDir) throws IOException {
        this.mapDir = mapDir;
        Files.createDirectories(mapDir);
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(mapDir).count();
        } catch (IOException e) {
            log.error(String.format("IO error with directory %s in method size() in DiskMap", mapDir));
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        Objects.requireNonNull(o);
        return Files.exists(mapDir.resolve(String.valueOf(o)));
    }

    @Override
    public boolean containsValue(Object o) {
        Objects.requireNonNull(o);
        try {
            return Files
                .list(mapDir)
                .map(file -> this.get(file.getFileName().toString()))
                .anyMatch(value -> value.equals(o));
        } catch (IOException e) {
            log.error(String.format(
                "IO error with file %s in method containsValue() in DiskMap",
                mapDir.resolve((Path) o)
            ));
        }
        return false;
    }

    @Override
    public Object get(Object o) {
        Objects.requireNonNull(o);

        var buffer = ByteBuffer.allocate(FILE_READER_BUFFER_SIZE);

        Path file = mapDir.resolve(String.valueOf(o));
        if (!Files.exists(file)) {
            return null;
        }
        try (FileChannel channel = new RandomAccessFile(file.toFile(), "r").getChannel()) {
            channel.read(buffer);
        } catch (IOException e) {
            log.error(String.format("IO error with file %s in method get() in DiskMap", file));
            return null;
        }

        buffer.flip();
        byte[] notNullBytes = new byte[buffer.remaining()];

        try {
        ByteArrayInputStream in = new ByteArrayInputStream(buffer.get(notNullBytes, 0, notNullBytes.length).array());
        ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error with generate object from byte array!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object put(Object o, Object o2) {
        Objects.requireNonNull(o);
        Objects.requireNonNull(o2);

        Path file = mapDir.resolve(String.valueOf(o));

        try {
            if (Files.exists(file)) {
                Files.delete(file);
            }
            Files.createFile(file);
        } catch (IOException e) {
            log.error(String.format("IO error with file %s in method put() in DiskMap", file));
            throw new RuntimeException(e);
        }

        try (FileChannel channel = new RandomAccessFile(file.toFile(), "rw").getChannel()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(o2);
            byte[] serializedObject = out.toByteArray();
            var buffer = ByteBuffer.wrap(serializedObject);
            channel.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return get(o);
    }

    @Override
    public Object remove(Object o) {
        Objects.requireNonNull(o);

        Object valueOfObj = this.get(o);

        Path file = mapDir.resolve(String.valueOf(o));
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            log.error(String.format("IO error with file %s in method delete() in DiskMap", file));
        }
        return valueOfObj;
    }

    @Override
    public void putAll(@NotNull Map<? extends Object, ? extends Object> map) {
        Objects.requireNonNull(map);
        map.forEach(this::put);
    }

    @Override
    public void clear() {
        try {
            Files
                .list(mapDir)
                .forEach(file -> {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        log.error(String.format(
                            "IO error with deleting file %s in method clear() in DiskMap",
                            file
                        ));
                    }
                });
        } catch (IOException e) {
            log.error(String.format("IO error with directory %s in method clear() in DiskMap", mapDir));
        }
    }

    @NotNull
    @Override
    public Set<Object> keySet() {
        try {
            return Files.list(mapDir)
                .map(it -> it.getFileName().toString())
                .collect(Collectors.toSet());
        } catch (IOException e) {
            log.error(String.format("IO error with directory %s in method keySet() in DiskMap", mapDir));
            return Set.of();
        }
    }

    @NotNull
    @Override
    public Collection<Object> values() {
        try {
            return Files.list(mapDir)
                .map(file -> this.get(file.getFileName().toString()))
                .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(String.format("IO error with directory %s in method values() in DiskMap", mapDir));
            return Collections.emptyList();
        }
    }

    @NotNull
    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        try {
            return Files.list(mapDir)
                .map(file -> Map.entry((Object) file.getFileName().toString(),
                    (Object) this.get(file.getFileName().toString())))
                .collect(Collectors.toSet());
        } catch (IOException e) {
            log.error(String.format("IO error with directory %s in method containsValue() in DiskMap", mapDir));
            return Set.of();
        }

    }
}
