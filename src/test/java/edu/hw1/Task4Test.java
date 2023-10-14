package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("4.Сломанная строка")
class Task4Test {

    @Test
    void fixString() {
        //given
        String str1 = "оПомигети псаривьтс ртко!и";
        String str2 = "123456";
        String str3 = "hTsii  s aimex dpus rtni.g";
        String str4 = "badce";
        String str5 = "";
        String str6 = "a";

        //when
        String res1 = Task4.fixString(str1);
        String res2 = Task4.fixString(str2);
        String res3 = Task4.fixString(str3);
        String res4 = Task4.fixString(str4);
        String res5 = Task4.fixString(str5);
        String res6 = Task4.fixString(str6);

        //then
        assertThat(res1).isEqualTo("Помогите исправить строки!");
        assertThat(res2).isEqualTo("214365");
        assertThat(res3).isEqualTo("This is a mixed up string.");
        assertThat(res4).isEqualTo("abcde");
        assertThat(res5).isEqualTo("");
        assertThat(res6).isEqualTo("a");

    }
}
