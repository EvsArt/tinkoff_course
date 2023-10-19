package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Task3 {

    private Task3() {
    }

    public static <T> Map<T, Integer> freqDict(List<T> list) {

        Map<T, Integer> result = new HashMap<>();

        for (T elem : list) {
            result.put(elem, result.getOrDefault(elem, 0) + 1);
        }

        return result;

    }

}
