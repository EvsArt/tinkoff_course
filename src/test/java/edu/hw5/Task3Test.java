package edu.hw5;

import edu.hw5.parsers.NDaysAgoParser;
import edu.hw5.parsers.NearDaysParser;
import edu.hw5.parsers.Parser;
import edu.hw5.parsers.StandardDateWithDashesParser;
import edu.hw5.parsers.StandardDateWithSlashesAndFullYearParser;
import edu.hw5.parsers.StandardDateWithSlashesAndShortenYearParser;
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

        var date1 = "2020-10-10";
        var date2 = "2020-12-2";
        var date3 = "1/3/1976";
        var date4 = "1/3/20";
        var date5 = "tomorrow";
        var date6 = "today";
        var date7 = "yesterday";
        var date8 = "1 day ago";
        var date9 = "2234 days ago";

        var res1 = task3.parseDate(date1).get();
        var res2 = task3.parseDate(date2).get();
        var res3 = task3.parseDate(date3).get();
        var res4 = task3.parseDate(date4).get();
        var res5 = task3.parseDate(date5).get();
        var res6 = task3.parseDate(date6).get();
        var res7 = task3.parseDate(date7).get();
        var res8 = task3.parseDate(date8).get();
        var res9 = task3.parseDate(date9).get();

        var expRes1 = LocalDate.of(2020, 10, 10);
        var expRes2 = LocalDate.of(2020, 12, 2);
        var expRes3 = LocalDate.of(1976, 3, 1);
        var expRes4 = LocalDate.of(2020, 3, 1);
        var expRes5 = LocalDate.now().plusDays(1);
        var expRes6 = LocalDate.now();
        var expRes7 = LocalDate.now().minusDays(1);
        var expRes8 = LocalDate.now().minusDays(1);
        var expRes9 = LocalDate.now().minusDays(2234);

        assertThat(res1).isEqualTo(expRes1);
        assertThat(res2).isEqualTo(expRes2);
        assertThat(res3).isEqualTo(expRes3);
        assertThat(res4).isEqualTo(expRes4);
        assertThat(res5).isEqualTo(expRes5);
        assertThat(res6).isEqualTo(expRes6);
        assertThat(res7).isEqualTo(expRes7);
        assertThat(res8).isEqualTo(expRes8);
        assertThat(res9).isEqualTo(expRes9);

    }

    @Test
    @DisplayName("Test yyyy-MM-dd parser with normal data")
    void standardDateWithDashesParserTest() {
        Parser parser = new StandardDateWithDashesParser();

        var date1 = "2004-02-01";
        var date2 = "2004-2-1";
        var date3 = "2004-12-1";
        var date4 = "2004-9-1";

        LocalDate res1 = parser.parse(date1).get();
        LocalDate res2 = parser.parse(date2).get();
        LocalDate res3 = parser.parse(date3).get();
        LocalDate res4 = parser.parse(date4).get();

        LocalDate expRes12 = LocalDate.of(2004, 2, 1);
        LocalDate expRes3 = LocalDate.of(2004, 12, 1);
        LocalDate expRes4 = LocalDate.of(2004, 9, 1);

        assertThat(res1).isEqualTo(expRes12);
        assertThat(res2).isEqualTo(expRes12);
        assertThat(res3).isEqualTo(expRes3);
        assertThat(res4).isEqualTo(expRes4);

    }

    @Test
    @DisplayName("Test yyyy-MM-dd parser with incorrect data")
    void standardDateWithDashesParserTestWithBadData() {
        Parser parser = new StandardDateWithDashesParser();

        var date1 = "2004-00-00";
        var date2 = "2004-0-0";
        var date3 = "2004-13-02";
        var date4 = "2004-11-32";
        var date5 = "2004/11/13";

        var res1 = parser.parse(date1);
        var res2 = parser.parse(date2);
        var res3 = parser.parse(date3);
        var res4 = parser.parse(date4);
        var res5 = parser.parse(date5);

        assertThat(res1).isEqualTo(Optional.empty());
        assertThat(res2).isEqualTo(Optional.empty());
        assertThat(res3).isEqualTo(Optional.empty());
        assertThat(res4).isEqualTo(Optional.empty());
        assertThat(res5).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

    @Test
    @DisplayName("Test dd/MM/yyyy parser with normal data")
    void standardDateWithSlashesAndFullYearParserTest() {
        Parser parser = new StandardDateWithSlashesAndFullYearParser();

        var date1 = "01/02/2004";
        var date2 = "1/2/2004";
        var date3 = "1/12/2004";
        var date4 = "1/9/2004";

        LocalDate res1 = parser.parse(date1).get();
        LocalDate res2 = parser.parse(date2).get();
        LocalDate res3 = parser.parse(date3).get();
        LocalDate res4 = parser.parse(date4).get();

        LocalDate expRes12 = LocalDate.of(2004, 2, 1);
        LocalDate expRes3 = LocalDate.of(2004, 12, 1);
        LocalDate expRes4 = LocalDate.of(2004, 9, 1);

        assertThat(res1).isEqualTo(expRes12);
        assertThat(res2).isEqualTo(expRes12);
        assertThat(res3).isEqualTo(expRes3);
        assertThat(res4).isEqualTo(expRes4);

    }

    @Test
    @DisplayName("Test dd/MM/yyyy parser with incorrect data")
    void standardDateWithSlashesAndFullYearParserTestWithBadData() {
        Parser parser = new StandardDateWithSlashesAndFullYearParser();

        var date1 = "00/00/2004";
        var date2 = "0/0/2004";
        var date3 = "02/13/2004";
        var date4 = "32/11/2004";

        var res1 = parser.parse(date1);
        var res2 = parser.parse(date2);
        var res3 = parser.parse(date3);
        var res4 = parser.parse(date4);

        assertThat(res1).isEqualTo(Optional.empty());
        assertThat(res2).isEqualTo(Optional.empty());
        assertThat(res3).isEqualTo(Optional.empty());
        assertThat(res4).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

    @Test
    @DisplayName("Test dd/MM/yy parser with normal data")
    void standardDateWithSlashesAndShortenYearParserTest() {
        Parser parser = new StandardDateWithSlashesAndShortenYearParser();

        var date1 = "01/02/04";
        var date2 = "1/2/04";
        var date3 = "1/12/04";
        var date4 = "1/9/04";
        var date5 = "1/9/70";

        LocalDate res1 = parser.parse(date1).get();
        LocalDate res2 = parser.parse(date2).get();
        LocalDate res3 = parser.parse(date3).get();
        LocalDate res4 = parser.parse(date4).get();
        LocalDate res5 = parser.parse(date5).get();

        LocalDate expRes12 = LocalDate.of(2004, 2, 1);
        LocalDate expRes3 = LocalDate.of(2004, 12, 1);
        LocalDate expRes4 = LocalDate.of(2004, 9, 1);
        LocalDate expRes5 = LocalDate.of(1970, 9, 1);

        assertThat(res1).isEqualTo(expRes12);
        assertThat(res2).isEqualTo(expRes12);
        assertThat(res3).isEqualTo(expRes3);
        assertThat(res4).isEqualTo(expRes4);
        assertThat(res5).isEqualTo(expRes5);

    }

    @Test
    @DisplayName("Test dd/MM/yy parser with incorrect data")
    void standardDateWithSlashesParserAndShortenYearParserTestWithBadData() {
        Parser parser = new StandardDateWithSlashesAndShortenYearParser();

        var date1 = "00/00/04";
        var date2 = "0/0/04";
        var date3 = "02/13/04";
        var date4 = "32/11/04";

        var res1 = parser.parse(date1);
        var res2 = parser.parse(date2);
        var res3 = parser.parse(date3);
        var res4 = parser.parse(date4);

        assertThat(res1).isEqualTo(Optional.empty());
        assertThat(res2).isEqualTo(Optional.empty());
        assertThat(res3).isEqualTo(Optional.empty());
        assertThat(res4).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

    @Test
    @DisplayName("Test yesterday-tomorrow parser with normal data")
    void nearDaysParserTest() {
        Parser parser = new NearDaysParser();

        var date1 = "tomorrow";
        var date2 = "yesterday";
        var date3 = "today";

        LocalDate res1 = parser.parse(date1).get();
        LocalDate res2 = parser.parse(date2).get();
        LocalDate res3 = parser.parse(date3).get();

        LocalDate expRes1 = LocalDate.now().plusDays(1);
        LocalDate expRes2 = LocalDate.now().minusDays(1);
        LocalDate expRes3 = LocalDate.now();

        assertThat(res1).isEqualTo(expRes1);
        assertThat(res2).isEqualTo(expRes2);
        assertThat(res3).isEqualTo(expRes3);

    }

    @Test
    @DisplayName("Test yesterday-tomorrow parser with incorrect data")
    void nearDaysParserTestWithBadData() {
        Parser parser = new NearDaysParser();

        var date1 = "aazsddawsd";
        var date2 = "";

        var res1 = parser.parse(date1);
        var res2 = parser.parse(date2);

        assertThat(res1).isEqualTo(Optional.empty());
        assertThat(res2).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

    @Test
    @DisplayName("Test N days ago parser with normal data")
    void nDaysAgoParserTest() {
        Parser parser = new NDaysAgoParser();

        var date1 = "1 day ago";
        var date2 = "120 Days ago";
        var date3 = "0 DaY Ago";

        LocalDate res1 = parser.parse(date1).get();
        LocalDate res2 = parser.parse(date2).get();
        LocalDate res3 = parser.parse(date3).get();

        LocalDate expRes1 = LocalDate.now().minusDays(1);
        LocalDate expRes2 = LocalDate.now().minusDays(120);
        LocalDate expRes3 = LocalDate.now();

        assertThat(res1).isEqualTo(expRes1);
        assertThat(res2).isEqualTo(expRes2);
        assertThat(res3).isEqualTo(expRes3);

    }

    @Test
    @DisplayName("Test N days ago parser with incorrect data")
    void nDaysAgoParserTestWithBadData() {
        Parser parser = new NDaysAgoParser();

        var date1 = "aazsddawsd";
        var date2 = "";

        var res1 = parser.parse(date1);
        var res2 = parser.parse(date2);

        assertThat(res1).isEqualTo(Optional.empty());
        assertThat(res2).isEqualTo(Optional.empty());

        assertThrows(NullPointerException.class, () -> parser.parse(null));

    }

}
