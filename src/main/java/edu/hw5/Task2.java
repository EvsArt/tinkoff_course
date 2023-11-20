package edu.hw5;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import static java.time.DayOfWeek.FRIDAY;

public class Task2 {

    private static final int NEEDED_DAY_OF_MONTH = 13;

    public List<LocalDate> getAllFridays13(int year) {

        LocalDate startDate = LocalDate.of(year, 1, NEEDED_DAY_OF_MONTH);

        return startDate
            .datesUntil(startDate.plusYears(1), Period.ofMonths(1))
            .filter(date -> date.getDayOfWeek().equals(FRIDAY))
            .toList();

    }

    public LocalDate getNearestNextFriday13(LocalDate inputDate) {

        TemporalAdjuster nextFriday13Adjuster = temporal -> {
            Temporal res = LocalDate.of(
                temporal.get(ChronoField.YEAR),
                temporal.get(ChronoField.MONTH_OF_YEAR),
                temporal.get(ChronoField.DAY_OF_MONTH)
            );
            do {
                res = res.with(TemporalAdjusters.next(FRIDAY));
            } while (!(res.get(ChronoField.DAY_OF_MONTH) == (NEEDED_DAY_OF_MONTH)));
            return res;
        };

        return LocalDate.from(nextFriday13Adjuster.adjustInto(inputDate));

    }

}
