package edu.project3.service;

import edu.project3.pojo.LogInfo;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogParser {

    public Stream<LogInfo> getLogsStream(List<Path> paths) throws IOException {
        Stream<LogInfo> res = Stream.empty();
        for (Path path : paths) {
            res = Stream.concat(res, getLogsStream(path));
        }
        return res;
    }

    public Stream<LogInfo> getLogsStream(Path path) throws IOException {
        return Files.lines(path)
            .map(this::parseLine)
            .filter(Objects::nonNull);
    }

    @SuppressWarnings("MagicNumber")
    protected LogInfo parseLine(String line) {
        LogInfo res = null;
        Pattern nginxLogFormat = Pattern.compile(
            "^([\\\\.\\d]+)"
                + "\\s-\\s-\\s"
                + "\\[(.+)\\]"
                + "\\s"
                + "\"(\\w{3,7})\\s(.+)\\s(.+)\""
                + "\\s(\\d+)\\s(\\d+)"
                + "\\s\"(.+)\"\\s"
                + "\"(.+)\"$"
        );
        Matcher matcher = nginxLogFormat.matcher(line);
        if (matcher.find()) {
            try {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");

                res = new LogInfo()
                    .setClientAddress(Inet4Address.getByName(matcher.group(1)))
                    .setDateTime(LocalDateTime.parse(matcher.group(2), dateTimeFormatter))
                    .setMethod(LogInfo.RequestMethod.stringToRequestMethod(matcher.group(3)))
                    .setResource(matcher.group(4))
                    .setProtocol(matcher.group(5))
                    .setResponseStatus(Integer.parseInt(matcher.group(6)))
                    .setBodyBytesSent(Integer.parseInt(matcher.group(7)))
                    .setHttpReferer(matcher.group(8))
                    .setUserAgent(matcher.group(9));
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

}
