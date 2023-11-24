package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task1Test {

    private static final String ROOT_DIR = "./src/main/resources/";
    private static final String WORKING_DIR = "Task1/";
    private static final String MAP_DIR = "diskmap";
    private static final Path MAP_PATH = Path.of(ROOT_DIR, WORKING_DIR, MAP_DIR);

    @BeforeAll
    static void createDirs() throws IOException {
        if (Files.notExists(MAP_PATH)) {
            Files.createDirectories(MAP_PATH);
        }
    }

    Task1.DiskMap map = null;

    @BeforeEach
    void createDiskMap() throws IOException {
        map = new Task1.DiskMap(MAP_PATH);
        map.clear();
    }

    @AfterEach
    void removeDiskMap() throws IOException {
        Files.walk(MAP_PATH)
            .filter(Files::isRegularFile)
            .forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        Files.deleteIfExists(MAP_PATH);
    }

    @Test
    @DisplayName("Test put-get methods")
    void putGetTest() {

        map.put("Key1", "val1");
        map.put("Key2", "val2");
        map.put("Key1", "val3");
        map.put("Key3", "val4");

        String valOfKey1 = map.get("Key1");
        String valOfKey2 = map.get("Key2");
        String valOfKey3 = map.get("Key3");
        String valOfKey0 = map.get("Key0");

        assertThat(valOfKey2).isEqualTo("val2");
        assertThat(valOfKey1).isEqualTo("val3");
        assertThat(valOfKey3).isEqualTo("val4");
        assertThat(valOfKey0).isEqualTo(null);

    }

    @Test
    @DisplayName("Test remove and contains method")
    void removeContainsTest() {

        map.put("Key1", "val1");
        map.put("Key2", "val2");
        map.remove("Key1");
        map.remove("Key2");
        map.put("Key1", "val3");

        String valOfKey1 = map.get("Key1");
        String valOfKey2 = map.get("Key2");
        int mapSize = map.size();
        boolean mapContainsKey1 = map.containsKey("Key1");
        boolean mapContainsKey2 = map.containsKey("Key2");
        boolean mapContainsValue1 = map.containsValue("val1");
        boolean mapContainsValue2 = map.containsValue("val2");
        boolean mapContainsValue3 = map.containsValue("val3");

        assertThat(valOfKey2).isEqualTo(null);
        assertThat(valOfKey1).isEqualTo("val3");

        assertThat(mapSize).isEqualTo(1);

        assertThat(mapContainsKey2).isFalse();
        assertThat(mapContainsKey1).isTrue();
        assertThat(mapContainsValue1).isFalse();
        assertThat(mapContainsValue2).isFalse();
        assertThat(mapContainsValue3).isTrue();

    }

    @Test
    @DisplayName("Test set-generation methods")
    void setsTest() {

        Map<String, String> tmpMap = new HashMap<>();

        tmpMap.put("Key1", "val1");
        tmpMap.put("Key2", "val2");
        tmpMap.put("Key3", "val3");
        map.putAll(tmpMap);

        Set<String> keySet = map.keySet();
        Collection<String> values = map.values();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();

        Set<String> expKeySet = tmpMap.keySet();
        Collection<String> expValuesSet = tmpMap.values();
        Set<Map.Entry<String, String>> expEntrySet = tmpMap.entrySet();

        assertEquals(keySet, expKeySet);
        assertEquals(Set.copyOf(values), Set.copyOf(expValuesSet));
        assertEquals(expEntrySet, entrySet);

    }

    @Test
    @DisplayName("Test saving and recovering")
    void saveAndRecoveryTest() {

        Map<String, String> tmpMap = new HashMap<>();
        tmpMap.put("Key1", "val1");
        tmpMap.put("Key2", "val2");
        tmpMap.put("Key3", "val3");
        map.putAll(tmpMap);

        Task1.DiskMap notAnotherMap = null;
        try {
            notAnotherMap = new Task1.DiskMap(MAP_PATH);
        } catch (IOException e) {
            System.out.println("Error with directory " + MAP_PATH);
            assertThat(true).isFalse();
        }

        Set<String> keySet = notAnotherMap.keySet();
        Collection<String> values = notAnotherMap.values();
        Set<Map.Entry<String, String>> entrySet = notAnotherMap.entrySet();

        Set<String> expKeySet = map.keySet();
        Collection<String> expValues = map.values();
        Set<Map.Entry<String, String>> expEntrySet = map.entrySet();

        assertEquals(keySet, expKeySet);
        assertEquals(values, expValues);
        assertEquals(entrySet, expEntrySet);

    }

    @Test
    @DisplayName("Negative tests")
    void negativeInputTest() {

        Task1.DiskMap finalMap = map;
        assertThrows(NullPointerException.class, () -> finalMap.put(null, "val4"));
        assertThrows(NullPointerException.class, () -> finalMap.put("Key1", null));
        assertThrows(AssertionError.class, () -> finalMap.put("", ""));
        assertThrows(AssertionError.class, () -> finalMap.get(""));

    }

}
