package edu.project3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ApplicationTest {

    Application application = new Application();

    @Test
    void run() {
        assertDoesNotThrow(() ->
            application.run(new String[] {"--path",
                "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs",
                "--from", "2003-08-11", "--format", "markdown"})
        );
    }
}
