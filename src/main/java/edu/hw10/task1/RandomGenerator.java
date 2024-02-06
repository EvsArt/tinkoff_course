package edu.hw10.task1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class RandomGenerator {

    private static final int DEFAULT_MAX_STRING_LENGTH = 20;

    private final Random random = new Random();

    private final Map<Class<?>, BiFunction<String, String, Object>> supportedTypes = new HashMap<>();

    public RandomGenerator() {
        fillSupportedTypes();
    }

    private void fillSupportedTypes() {
        supportedTypes.put(Integer.class, getRandInt);
        supportedTypes.put(int.class, getRandInt);
        supportedTypes.put(Short.class, getRandShort);
        supportedTypes.put(short.class, getRandShort);
        supportedTypes.put(Long.class, getRandLong);
        supportedTypes.put(long.class, getRandLong);
        supportedTypes.put(Double.class, getRandDouble);
        supportedTypes.put(double.class, getRandDouble);
        supportedTypes.put(Float.class, getRandFloat);
        supportedTypes.put(float.class, getRandFloat);
        supportedTypes.put(Boolean.class, getRandBoolean);
        supportedTypes.put(boolean.class, getRandBoolean);
        supportedTypes.put(String.class, getRandString);
    }

    public <T> Map<Class<?>, BiFunction<String, String, Object>> getSupportedTypes() {
        return supportedTypes;
    }

    private BiFunction<String, String, Integer> getRandomInt = (String min, String max) ->
        getRandomInt(
            (min == null) ? null : Integer.valueOf(min),
            (max == null) ? null : Integer.valueOf(max)
        );

    public <T> T getRandomValueOf(Class<?> type, String min, String max) {
        return (T) supportedTypes.get(type).apply(min, max);
    }

    private Integer getRandomInt(Integer min, Integer max) {
        Integer min1 = min;
        Integer max1 = max;
        if (min == null) {
            min1 = Integer.MIN_VALUE;
        }
        if (max == null) {
            max1 = Integer.MAX_VALUE - 1;
        }
        return random.nextInt(min1, max1 + 1);
    }

    private Short getRandomShort(Short min, Short max) {
        Short min1 = min;
        Short max1 = max;
        if (min == null) {
            min1 = Short.MIN_VALUE;
        }
        if (max == null) {
            max1 = Short.MAX_VALUE - 1;
        }
        return (short) random.nextInt(min1, max1 + 1);
    }

    private Long getRandomLong(Long min, Long max) {
        Long min1 = min;
        Long max1 = max;
        if (min == null) {
            min1 = Long.MIN_VALUE;
        }
        if (max == null) {
            max1 = Long.MAX_VALUE - 1;
        }
        return random.nextLong(min1, max1 + 1);
    }

    private Float getRandomFloat(Float min, Float max) {
        Float min1 = min;
        Float max1 = max;
        if (min == null) {
            min1 = Float.MIN_VALUE;
        }
        if (max == null) {
            max1 = Float.MAX_VALUE;
        }
        return random.nextFloat(min1, max1);
    }

    private Double getRandomDouble(Double min, Double max) {
        Double min1 = min;
        Double max1 = max;
        if (min == null) {
            min1 = Double.MIN_VALUE;
        }
        if (max == null) {
            max1 = Double.MAX_VALUE;
        }
        return random.nextDouble(min1, max1);
    }

    private Boolean getRandomBoolean(Boolean min, Boolean max) {
        return random.nextBoolean();
    }

    private String getRandomString(String min, String max) {
        String min1 = min;
        String max1 = max;
        if (min == null) {
            min1 = "";
        }
        if (max == null) {
            max1 = "0".repeat(DEFAULT_MAX_STRING_LENGTH);
        }

        int minLen = min1.length();
        int maxLen = max1.length();

        if (min1.length() > max1.length()) {
            throw new IllegalArgumentException("min string length is greater than max string length");
        }

        return random.ints(random.nextInt(minLen, maxLen + 1), 'a', 'z' + 1)
            .mapToObj(it -> String.valueOf((char) it))
            .collect(Collectors.joining());

    }

    private BiFunction<String, String, Object> getRandInt = ((String min, String max) ->
        getRandomInt(
            (min == null) ? null : Integer.valueOf(min),
            (max == null) ? null : Integer.valueOf(max)
        ));
    private BiFunction<String, String, Object> getRandLong = ((String min, String max) ->
        getRandomLong(
            (min == null) ? null : Long.valueOf(min),
            (max == null) ? null : Long.valueOf(max)
        ));
    private BiFunction<String, String, Object> getRandShort = ((String min, String max) ->
        getRandomShort(
            (min == null) ? null : Short.valueOf(min),
            (max == null) ? null : Short.valueOf(max)
        ));
    private BiFunction<String, String, Object> getRandDouble = ((String min, String max) ->
        getRandomDouble(
            (min == null) ? null : Double.valueOf(min),
            (max == null) ? null : Double.valueOf(max)
        ));
    private BiFunction<String, String, Object> getRandFloat = ((String min, String max) ->
        getRandomFloat(
            (min == null) ? null : Float.valueOf(min),
            (max == null) ? null : Float.valueOf(max)
        ));
    private BiFunction<String, String, Object> getRandBoolean = ((String min, String max) ->
        getRandomBoolean(
            (min == null) ? null : Boolean.valueOf(min),
            (max == null) ? null : Boolean.valueOf(max)
        ));
    private BiFunction<String, String, Object> getRandString = this::getRandomString;

}
