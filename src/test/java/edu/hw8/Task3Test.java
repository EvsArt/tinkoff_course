package edu.hw8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task3Test {

    String infoFromDB = """
        a.v.petrov  e10adc3949ba59abbe56e057f20f883e
        v.v.belov   d8578edf8458ce06fbc5bb76a58c5ca4
        a.s.ivanov  482c811da5d5b4bc6d497ffa98491e38
        k.p.maslov  5f4dcc3b5aa765d61d8327deb882cf99""";

    @ParameterizedTest
    @CsvSource(value = {
        "hello, hellp",
        "hellz, hellA",
        "hell9, hella",
        "hellZ, helm0",
        "ZZZZ, 00000",
        "'', 0"
    })
    void nextPassword(String pass, String expNextPass) {

        Task3 task3 = new Task3(infoFromDB);

        String nextPath = task3.nextPassword(pass);

        assertEquals(expNextPass, nextPath);

    }

    @Test
    void hackBD() {

        Task3 task3 = new Task3(infoFromDB);

//        System.out.println(task3.hackBDInMultiThread(6, 16));

    }

}
