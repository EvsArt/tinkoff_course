package edu.hw5;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task3Test {

    Task3 task3 = new Task3();

    @Test
    @DisplayName("Test parseData method")
    void parseDataTest() {
        var date1 = task3.parseDate("2020-10-10").get();
        var date2 = task3.parseDate("2020-12-2").get();
        var date3 = task3.parseDate("1/3/1976").get();
        var date4 = task3.parseDate("1/3/20").get();
        var date5 = task3.parseDate("tomorrow").get();
        var date6 = task3.parseDate("today").get();
        var date7 = task3.parseDate("yesterday").get();
        var date8 = task3.parseDate("1 day ago").get();
        var date9 = task3.parseDate("2234 days ago").get();

        var expRes1 = LocalDate.of(2020, 10, 10);
        var expRes2 = LocalDate.of(2020, 12, 2);
        var expRes3 = LocalDate.of(1976, 3, 1);
        var expRes4 = LocalDate.of(2020, 3, 1);
        var expRes5 = LocalDate.now().plusDays(1);
        var expRes6 = LocalDate.now();
        var expRes7 = LocalDate.now().minusDays(1);
        var expRes8 = LocalDate.now().minusDays(1);
        var expRes9 = LocalDate.now().minusDays(2234);

        assertThat(date1).isEqualTo(expRes1);
        assertThat(date2).isEqualTo(expRes2);
        assertThat(date3).isEqualTo(expRes3);
        assertThat(date4).isEqualTo(expRes4);
        assertThat(date5).isEqualTo(expRes5);
        assertThat(date6).isEqualTo(expRes6);
        assertThat(date7).isEqualTo(expRes7);
        assertThat(date8).isEqualTo(expRes8);
        assertThat(date9).isEqualTo(expRes9);

    }

    @Test
    @DisplayName("Test yyyy-MM-dd parser with normal data")
    void standardDateWithDashesParserTest() {
        Task3.Parser parser = new Task3.StandardDateWithDashesParser();

        LocalDate date1 = parser.parse("2004-02-01").get();
        LocalDate date2 = parser.parse("2004-2-1").get();
        LocalDate date3 = parser.parse("2004-12-1").get();
        LocalDate date4 = parser.parse("2004-9-1").get();

        LocalDate expRes12 = LocalDate.of(2004, 2, 1);
        LocalDate expRes3 = LocalDate.of(2004, 12, 1);
        LocalDate expRes4 = LocalDate.of(2004, 9, 1);

        assertThat(date1).isEqualTo(expRes12);
        assertThat(date2).isEqualTo(expRes12);
        assertThat(date3).isEqualTo(expRes3);
        assertThat(date4).isEqualTo(expRes4);

    }

    @Test
    @DisplayName("Test yyyy-MM-dd parser with incorrect data")
    void standardDateWithDashesParserTestWithBadData() {
        Task3.Parser parser = new Task3.StandardDateWithDashesParser();

        var date1 = parser.parse("2004-00-00");
        var date2 = parser.parse("2004-0-0");
        var date3 = parser.parse("2004-13-02");
        var date4 = parser.parse("2004-11-32");
        var date5 = parser.parse("2004/11/13");

        assertThat(date1).isEqualTo(Optional.empty());
        assertThat(date2).isEqualTo(Optional.empty());
        assertThat(date3).isEqualTo(Optional.empty());
        assertThat(date4).isEqualTo(Optional.empty());
        assertThat(date5).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

    @Test
    @DisplayName("Test dd/MM/yyyy parser with normal data")
    void standardDateWithSlashesAndFullYearParserTest() {
        Task3.Parser parser = new Task3.StandardDateWithSlashesAndFullYearParser();

        LocalDate date1 = parser.parse("01/02/2004").get();
        LocalDate date2 = parser.parse("1/2/2004").get();
        LocalDate date3 = parser.parse("1/12/2004").get();
        LocalDate date4 = parser.parse("1/9/2004").get();

        LocalDate expRes12 = LocalDate.of(2004, 2, 1);
        LocalDate expRes3 = LocalDate.of(2004, 12, 1);
        LocalDate expRes4 = LocalDate.of(2004, 9, 1);

        assertThat(date1).isEqualTo(expRes12);
        assertThat(date2).isEqualTo(expRes12);
        assertThat(date3).isEqualTo(expRes3);
        assertThat(date4).isEqualTo(expRes4);

    }

    @Test
    @DisplayName("Test dd/MM/yyyy parser with incorrect data")
    void standardDateWithSlashesAndFullYearParserTestWithBadData() {
        Task3.Parser parser = new Task3.StandardDateWithSlashesAndFullYearParser();

        var date1 = parser.parse("00/00/2004");
        var date2 = parser.parse("0/0/2004");
        var date3 = parser.parse("02/13/2004");
        var date4 = parser.parse("32/11/2004");

        assertThat(date1).isEqualTo(Optional.empty());
        assertThat(date2).isEqualTo(Optional.empty());
        assertThat(date3).isEqualTo(Optional.empty());
        assertThat(date4).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

    @Test
    @DisplayName("Test dd/MM/yy parser with normal data")
    void standardDateWithSlashesAndShortenYearParserTest() {
        Task3.Parser parser = new Task3.StandardDateWithSlashesAndShortenYearParser();

        LocalDate date1 = parser.parse("01/02/04").get();
        LocalDate date2 = parser.parse("1/2/04").get();
        LocalDate date3 = parser.parse("1/12/04").get();
        LocalDate date4 = parser.parse("1/9/04").get();
        LocalDate date5 = parser.parse("1/9/70").get();

        LocalDate expRes12 = LocalDate.of(2004, 2, 1);
        LocalDate expRes3 = LocalDate.of(2004, 12, 1);
        LocalDate expRes4 = LocalDate.of(2004, 9, 1);
        LocalDate expRes5 = LocalDate.of(1970, 9, 1);

        assertThat(date1).isEqualTo(expRes12);
        assertThat(date2).isEqualTo(expRes12);
        assertThat(date3).isEqualTo(expRes3);
        assertThat(date4).isEqualTo(expRes4);
        assertThat(date5).isEqualTo(expRes5);

    }

    @Test
    @DisplayName("Test dd/MM/yy parser with incorrect data")
    void standardDateWithSlashesParserAndShortenYearParserTestWithBadData() {
        Task3.Parser parser = new Task3.StandardDateWithSlashesAndShortenYearParser();

        var date1 = parser.parse("00/00/04");
        var date2 = parser.parse("0/0/04");
        var date3 = parser.parse("02/13/04");
        var date4 = parser.parse("32/11/04");

        assertThat(date1).isEqualTo(Optional.empty());
        assertThat(date2).isEqualTo(Optional.empty());
        assertThat(date3).isEqualTo(Optional.empty());
        assertThat(date4).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

    @Test
    @DisplayName("Test yesterday-tomorrow parser with normal data")
    void nearDaysParserTest() {
        Task3.Parser parser = new Task3.NearDaysParser();

        LocalDate date1 = parser.parse("tomorrow").get();
        LocalDate date2 = parser.parse("yesterday").get();
        LocalDate date3 = parser.parse("today").get();

        LocalDate expRes1 = LocalDate.now().plusDays(1);
        LocalDate expRes2 = LocalDate.now().minusDays(1);
        LocalDate expRes3 = LocalDate.now();

        assertThat(date1).isEqualTo(expRes1);
        assertThat(date2).isEqualTo(expRes2);
        assertThat(date3).isEqualTo(expRes3);

    }

    @Test
    @DisplayName("Test yesterday-tomorrow parser with incorrect data")
    void nearDaysParserTestWithBadData() {
        Task3.Parser parser = new Task3.NearDaysParser();

        var date1 = parser.parse("aazsddawsd");
        var date2 = parser.parse("");

        assertThat(date1).isEqualTo(Optional.empty());
        assertThat(date2).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

    @Test
    @DisplayName("Test N days ago parser with normal data")
    void nDaysAgoParserTest() {
        Task3.Parser parser = new Task3.NDaysAgoParser();

        LocalDate date1 = parser.parse("1 day ago").get();
        LocalDate date2 = parser.parse("120 Days ago").get();
        LocalDate date3 = parser.parse("0 DaY Ago").get();

        LocalDate expRes1 = LocalDate.now().minusDays(1);
        LocalDate expRes2 = LocalDate.now().minusDays(120);
        LocalDate expRes3 = LocalDate.now();

        assertThat(date1).isEqualTo(expRes1);
        assertThat(date2).isEqualTo(expRes2);
        assertThat(date3).isEqualTo(expRes3);

    }

    @Test
    @DisplayName("Test N days ago parser with incorrect data")
    void nDaysAgoParserTestWithBadData() {
        Task3.Parser parser = new Task3.NDaysAgoParser();

        var date1 = parser.parse("aazsddawsd");
        var date2 = parser.parse("");

        assertThat(date1).isEqualTo(Optional.empty());
        assertThat(date2).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

}
