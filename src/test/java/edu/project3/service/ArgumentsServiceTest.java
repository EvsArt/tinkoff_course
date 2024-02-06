package edu.project3.service;

import edu.project3.pojo.Arguments;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArgumentsServiceTest {

    ArgumentsService service = new ArgumentsService();

    @Test
    void getArgumentsMap() {

        String[] args = new String[] {"aaa", "fff", "--path", "aasd", "addda", "--format", "md", "aaa"};
        Arguments arguments = service.getArgumentsMap(args);

        List<String> pathList = arguments.paths();
        String format = arguments.format();

        List<String> expPathList = List.of("aasd", "addda");
        String expFormat = "md";

        assertEquals(pathList, expPathList);
        assertEquals(format, expFormat);

    }
}
