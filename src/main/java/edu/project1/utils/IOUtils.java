package edu.project1.utils;

import edu.project1.constraints.StringConstraints;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class IOUtils {

    private IOUtils() {
    }

    private static final Logger LOG = LogManager.getLogger("MyLog");

    public static void consoleLineOutput(String msg) {
        String out = StringConstraints.startEveryOutputLine + " " + msg;
        LOG.info(out);
    }

    public static void consoleLineOutput() {
        LOG.info(StringConstraints.startEveryOutputLine);
    }

    public static String consoleLineInput() {
        Scanner scanner = new Scanner(System.in);
        LOG.info(StringConstraints.startEveryInputLine + " ");
        return scanner.nextLine();
    }

}
