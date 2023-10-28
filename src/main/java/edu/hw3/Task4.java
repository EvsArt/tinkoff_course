package edu.hw3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class Task4 {

    private Task4() {
    }

    protected static final String WRONGINPUT = "None";
    private static final int MAXNUM = 3999;
    private static final int MINNUM = 1;

    // If num <= 3 we use simple digits multiplication (2 = II), (13 = XIII), (1321 = MCCCXXI)
    private static final int FIRSTCONTROLNUM = 3;

    // Before 5 we use subtraction from 5-starts numbers: (4 = 5-1 = IV), (42 = 50 - 10 + 2 = XLII) (5 includes)
    private static final int SECONDCONTROLNUM = 5;

    // Before 8 we use addition for 5-starts numbers: (7 = 5 + 2 = VII), (82 = 50 + 30 + 2 = LXXXII)
    private static final int THIRDCONTROLNUM = 8;

    // With 9 we use subtraction from 10-starts numbers: (9 = 10 - 1 = IX), (902 = 1000 - 100 + 2 = CMII)
    private static final int FOURTHCONTROLNUM = 9;

    // We have 5-starts numbers and 10-starts numbers in Roman numbers
    private static final int FIVE = 5;
    private static final int TEN = 10;

    public static String convertToRoman(int num) {

        if (num < MINNUM || num > MAXNUM) {
            return WRONGINPUT;
        }

        Map<RomanDigits, Integer> romanDigitsToCount = getNumInfo(num);
        String romanNum = convertNumInfoToRomanString(romanDigitsToCount);
        return romanNum;
    }

    private static Map<RomanDigits, Integer> getNumInfo(int num) {

        Map<RomanDigits, Integer> romanDigitsToCount = new HashMap<>();

        RomanDigits prevElem = null;
        RomanDigits maxElem = RomanDigits.getMaxElement();
        romanDigitsToCount.put(maxElem, num / maxElem.getValue());
        for (RomanDigits elem : RomanDigits.getSortRoundValues()) {
            if (!elem.equals(maxElem)) {
                romanDigitsToCount.put(elem, (num / elem.value) % (prevElem.getValue() / elem.getValue()));
            }
            prevElem = elem;
        }

        return romanDigitsToCount;

    }

    private static String convertNumInfoToRomanString(Map<RomanDigits, Integer> numInfo) {

        StringBuilder result = new StringBuilder();
        RomanDigits maxElem = RomanDigits.getMaxElement();
        appendNSymbols(result, maxElem, numInfo.get(maxElem));

        for (RomanDigits elem : RomanDigits.getSortRoundValues()) {
            if (elem.equals(maxElem)) {
                continue;
            }

            int count = numInfo.get(elem);
            if (count <= FIRSTCONTROLNUM) {
                appendNSymbols(result, elem, count);
            } else if (count <= SECONDCONTROLNUM) {
                RomanDigits next5StartedDigit = RomanDigits.findDigitByValue(elem.getValue() * FIVE);
                appendNSymbols(result, elem, FIVE - count);
                appendNSymbols(result, next5StartedDigit, 1);
            } else if (count <= THIRDCONTROLNUM) {
                RomanDigits next5StartedDigit = RomanDigits.findDigitByValue(elem.getValue() * FIVE);
                appendNSymbols(result, next5StartedDigit, 1);
                appendNSymbols(result, elem, count - FIVE);
            } else if (count == FOURTHCONTROLNUM) {
                RomanDigits next10StartedDigit = RomanDigits.findDigitByValue(elem.getValue() * TEN);
                appendNSymbols(result, elem, TEN - count);
                appendNSymbols(result, next10StartedDigit, 1);
            }
        }
        return result.toString();
    }

    private static void appendNSymbols(StringBuilder builder, RomanDigits symbol, int count) {

        builder.append(String.valueOf(symbol.charValue).repeat(count));

    }

    public enum RomanDigits {

        THOUSAND(1000, 'M'),
        FIVEHUNDRED(500, 'D'),
        HUNDRED(100, 'C'),
        FIFTY(50, 'L'),
        TEN(10, 'X'),
        FIVE(5, 'V'),
        ONE(1, 'I');

        private final int value;
        private final char charValue;

        private static final Map<Integer, RomanDigits> VALUETODIGIT = new HashMap<>();

        RomanDigits(int value, char charValue) {
            this.value = value;
            this.charValue = charValue;
        }

        public int getValue() {
            return value;
        }

        public static RomanDigits findDigitByValue(int value) {
            if (VALUETODIGIT.isEmpty()) {
                for (RomanDigits digit : values()) {
                    VALUETODIGIT.put(digit.value, digit);
                }
            }
            return VALUETODIGIT.get(value);
        }

        public static RomanDigits[] getSortedValues() {
            RomanDigits[] res = RomanDigits.values();
            Arrays.sort(res, Comparator.comparingInt(RomanDigits::getValue).reversed());
            return res;
        }

        public static RomanDigits[] getSortRoundValues() {
            ArrayList<RomanDigits> res = new ArrayList<>();
            for (RomanDigits elem : RomanDigits.getSortedValues()) {
                if (!String.valueOf(elem.value).startsWith("5")) {
                    res.add(elem);
                }
            }

            RomanDigits[] resArray = new RomanDigits[res.size()];
            return res.toArray(resArray);
        }

        public static RomanDigits getMaxElement() {
            return getSortedValues()[0];
        }

    }

}
