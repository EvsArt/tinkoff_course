package edu.hw2;

import edu.hw2.Task4.CallingInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.hw2.Task4.callingInfo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Кто вызвал функцию?")
class Task4Test {

    @Test
    void callingInfoTest() {
        //given
        CallingInfo thisClassMethodInfo;

        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            StackTraceElement elem = e.getStackTrace()[0];   // Info about this class and method

            String fullClassName = elem.getClassName();
            String[] path = fullClassName.split("\\.");
            String className = path[path.length - 1];     // "edu.hw2.Task4" -> "Task4"

            thisClassMethodInfo = new CallingInfo(
                className,
                elem.getMethodName()
            );
        }

        //when
        CallingInfo callingInfo = callingInfo();

        //then
        assertThat(callingInfo).isEqualTo(thisClassMethodInfo);
    }

}
