package edu.hw6;

import java.io.IOException;
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
import org.jetbrains.annotations.Nullable;

public class Task1 {

    @Slf4j
    static class DiskMap implements Map<String, String> {

        private static final int FILE_READER_BUFFER_SIZE = 4096;

        private final Path mapDir;

        DiskMap(Path mapDir) throws IOException {
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
            return Files.exists(mapDir.resolve((String) o));
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
        public String get(Object o) {
            Objects.requireNonNull(o);
            assert !((String) o).isEmpty();

            var buffer = ByteBuffer.allocate(FILE_READER_BUFFER_SIZE);

            Path file = mapDir.resolve((String) o);
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
            buffer.get(notNullBytes, 0, notNullBytes.length);
            return new String(notNullBytes);
        }

        @Nullable
        @Override
        public String put(String s, String s2) {
            Objects.requireNonNull(s);
            Objects.requireNonNull(s2);
            assert !s.isEmpty();

            Path file = mapDir.resolve(s);

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
                var buffer = ByteBuffer.wrap(s2.getBytes());
                channel.write(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return get(s);
        }

        @Override
        public String remove(Object o) {
            Objects.requireNonNull(o);
            assert !((String) o).isEmpty();

            String valueOfObj = this.get(o);
            Path file = mapDir.resolve((String) o);

            try {
                Files.deleteIfExists(file);
            } catch (IOException e) {
                log.error(String.format("IO error with file %s in method delete() in DiskMap", file));
            }
            return valueOfObj;
        }

        @Override
        public void putAll(@NotNull Map<? extends String, ? extends String> map) {
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
        public Set<String> keySet() {
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
        public Collection<String> values() {
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
        public Set<Map.Entry<String, String>> entrySet() {
            try {
                return Files.list(mapDir)
                    .map(file -> Map.entry(file.getFileName().toString(), this.get(file.getFileName().toString())))
                    .collect(Collectors.toSet());
            } catch (IOException e) {
                log.error(String.format("IO error with directory %s in method containsValue() in DiskMap", mapDir));
                return Set.of();
            }

        }

    }

}
