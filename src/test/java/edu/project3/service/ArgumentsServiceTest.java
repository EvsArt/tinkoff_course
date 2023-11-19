package edu.project3.service;

import edu.project3.pojo.Arguments;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ArgumentsServiceTest {

    ArgumentsService service = new ArgumentsService();

    @Test
    void getArgumentsMap() {

        String[] args = new String[]{"aaa", "fff", "--path", "aasd", "addda", "--format", "md", "aaa"};
        Arguments arguments = service.getArgumentsMap(args);

        assertThat(arguments.paths().containsAll(List.of("aasd", "addda"))).isTrue();
        assertThat(arguments.format()).isEqualTo("md");

    }
}
