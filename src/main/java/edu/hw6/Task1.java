package edu.hw6;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Task1 {

    private static final int BUFFER_SIZE = 4096;

    static class DiskMap implements Map<String, String> {

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
                throw new RuntimeException(e);
            }
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
                throw new RuntimeException(e);
            }
        }

        @Override
        public String get(Object o) {
            Objects.requireNonNull(o);
            assert !((String) o).isEmpty();

            var buffer = ByteBuffer.allocate(BUFFER_SIZE);

            Path file = mapDir.resolve((String) o);
            try (FileChannel channel = new RandomAccessFile(file.toFile(), "r").getChannel()) {
                channel.read(buffer);
            } catch (IOException e) {
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
                if (Files.exists(file)) {
                    Files.delete(file);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
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
                            throw new RuntimeException(e);
                        }
                    });
            } catch (IOException e) {
                throw new RuntimeException(e);
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
                throw new RuntimeException(e);
            }
        }

        @NotNull
        @Override
        public Collection<String> values() {
            try {
                return Files.list(mapDir)
                    .map(file -> this.get(file.getFileName().toString()))
                    .collect(Collectors.toSet());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @NotNull
        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            try {
                return Files.list(mapDir)
                    .map(file -> new Entry(file.getFileName().toString(), this.get(file.getFileName().toString())))
                    .collect(Collectors.toSet());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        static class Entry implements Map.Entry<String, String> {

            private final String key;
            private String value;

            Entry(String key, String value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public String getKey() {
                return key;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public String setValue(String s) {
                this.value = s;
                return value;
            }
        }

    }

}
