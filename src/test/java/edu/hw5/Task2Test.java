package edu.hw5;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task2Test {

    Task2 task2 = new Task2();

    @Test
    void getAllFridays13() {

        int year1 = 1925;
        int year2 = 2024;

        List<LocalDate> expRes1 = List.of(
            LocalDate.of(year1, 2, 13),
            LocalDate.of(year1, 3, 13),
            LocalDate.of(year1, 11, 13)
        );

        List<LocalDate> expRes2 = List.of(
            LocalDate.of(year2, 9, 13),
            LocalDate.of(year2, 12, 13)
        );

        var res1 = task2.getAllFridays13(year1);
        var res2 = task2.getAllFridays13(year2);

        assertThat(res1).isEqualTo(expRes1);
        assertThat(res2).isEqualTo(expRes2);

        assertThrows(DateTimeException.class, () -> task2.getAllFridays13(Integer.MIN_VALUE));
        assertThrows(DateTimeException.class, () -> task2.getAllFridays13(Integer.MAX_VALUE));

    }

    @Test
    void getNearestNextFriday13() {

        LocalDate date1 = LocalDate.of(2024, 9, 12);
        LocalDate date2 = LocalDate.of(2024, 9, 13);
        LocalDate fridayDate1 = LocalDate.of(2024, 9, 13);
        LocalDate fridayDate2 = LocalDate.of(2024, 12, 13);

        LocalDate res1 = task2.getNearestNextFriday13(date1);
        LocalDate res2 = task2.getNearestNextFriday13(date2);

        assertThat(res1).isEqualTo(fridayDate1);
        assertThat(res2).isEqualTo(fridayDate2);

    }
}
