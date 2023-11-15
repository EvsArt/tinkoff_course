package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task1Test {

    private static final String ROOT_DIR = "./src/main/resources/";
    private static final String WORKING_DIR = "Task1/";
    private static final String MAP_DIR = "diskmap";

    @BeforeAll
    static void createDirs() {
        Path path = Path.of(ROOT_DIR, WORKING_DIR, MAP_DIR);
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Test put-get methods")
    void putGetTest() {

        Task1.DiskMap map = null;

        try {
            map = new Task1.DiskMap(Path.of(ROOT_DIR, WORKING_DIR, MAP_DIR));
            map.clear();
        } catch (IOException e) {
            System.out.println("Error with directory " + ROOT_DIR + WORKING_DIR + MAP_DIR);
            assertThat(true).isFalse();
        }

        map.put("Key1", "val1");
        map.put("Key2", "val2");
        map.put("Key1", "val3");
        map.put("Key3", "val4");

        assertThat(map.get("Key2")).isEqualTo("val2");
        assertThat(map.get("Key1")).isEqualTo("val3");
        assertThat(map.get("Key3")).isEqualTo("val4");
        assertThat(map.get("Key0")).isEqualTo(null);

    }

    @Test
    @DisplayName("Test remove and contains method")
    void removeContainsTest() {

        Task1.DiskMap map = null;

        try {
            map = new Task1.DiskMap(Path.of(ROOT_DIR, WORKING_DIR, MAP_DIR));
            map.clear();
        } catch (IOException e) {
            System.out.println("Error with directory " + ROOT_DIR + WORKING_DIR + MAP_DIR);
            assertThat(true).isFalse();
        }

        map.put("Key1", "val1");
        map.put("Key2", "val2");
        map.remove("Key1");
        map.remove("Key2");
        map.put("Key1", "val3");

        assertThat(map.get("Key2")).isEqualTo(null);
        assertThat(map.get("Key1")).isEqualTo("val3");

        assertThat(map.size()).isEqualTo(1);

        assertThat(map.containsKey("Key2")).isFalse();
        assertThat(map.containsKey("Key1")).isTrue();
        assertThat(map.containsValue("val1")).isFalse();
        assertThat(map.containsValue("val2")).isFalse();
        assertThat(map.containsValue("val3")).isTrue();

        map.remove("Key1");
        assertThat(map.isEmpty()).isTrue();

    }

    @Test
    @DisplayName("Test set-generation methods")
    void setsTest() {

        Task1.DiskMap map = null;

        try {
            map = new Task1.DiskMap(Path.of(ROOT_DIR, WORKING_DIR, MAP_DIR));
            map.clear();
        } catch (IOException e) {
            System.out.println("Error with directory " + ROOT_DIR + WORKING_DIR + MAP_DIR);
            assertThat(true).isFalse();
        }

        Map<String, String> tmpMap = new HashMap<>();

        tmpMap.put("Key1", "val1");
        tmpMap.put("Key2", "val2");
        tmpMap.put("Key3", "val3");
        map.putAll(tmpMap);

        Set<String> keySet = map.keySet();
        Collection<String> valuesSet = map.values();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();

        assertThat(keySet.containsAll(List.of("Key1", "Key2", "Key3"))).isTrue();
        assertThat(valuesSet.containsAll(List.of("val1", "val2", "val3"))).isTrue();

        assertThat(keySet.size()).isEqualTo(3);
        assertThat(valuesSet.size()).isEqualTo(3);
        assertThat(entrySet.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("Test saving and recovering")
    void saveAndRecoveryTest() {

        Task1.DiskMap map = null;
        try {
            map = new Task1.DiskMap(Path.of(ROOT_DIR, WORKING_DIR, MAP_DIR));
            map.clear();
        } catch (IOException e) {
            System.out.println("Error with directory " + ROOT_DIR + WORKING_DIR + MAP_DIR);
            assertThat(true).isFalse();
        }

        Map<String, String> tmpMap = new HashMap<>();
        tmpMap.put("Key1", "val1");
        tmpMap.put("Key2", "val2");
        tmpMap.put("Key3", "val3");
        map.putAll(tmpMap);

        Task1.DiskMap notAnotherMap = null;
        try {
            notAnotherMap = new Task1.DiskMap(Path.of(ROOT_DIR, WORKING_DIR, MAP_DIR));
        } catch (IOException e) {
            System.out.println("Error with directory " + ROOT_DIR + WORKING_DIR + MAP_DIR);
            assertThat(true).isFalse();
        }

        Set<String> keySet = notAnotherMap.keySet();
        Collection<String> valuesSet = notAnotherMap.values();
        Set<Map.Entry<String, String>> entrySet = notAnotherMap.entrySet();

        assertThat(keySet.containsAll(List.of("Key1", "Key2", "Key3"))).isTrue();
        assertThat(valuesSet.containsAll(List.of("val1", "val2", "val3"))).isTrue();

        assertThat(keySet.size()).isEqualTo(3);
        assertThat(valuesSet.size()).isEqualTo(3);
        assertThat(entrySet.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("Negative tests")
    void negativeInputTest() {

        Task1.DiskMap map = null;
        try {
            map = new Task1.DiskMap(Path.of(ROOT_DIR, WORKING_DIR, MAP_DIR));
            map.clear();
        } catch (IOException e) {
            System.out.println("Error with directory " + ROOT_DIR + WORKING_DIR + MAP_DIR);
            assertThat(true).isFalse();
        }

        Task1.DiskMap finalMap = map;
        assertThrows(NullPointerException.class, () -> finalMap.put(null, "val4"));
        assertThrows(NullPointerException.class, () -> finalMap.put("Key1", null));
        assertThrows(AssertionError.class, () -> finalMap.put("", ""));
        assertThrows(AssertionError.class, () -> finalMap.get(""));

    }

}
