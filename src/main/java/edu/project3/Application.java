package edu.project3;

import edu.project3.pojo.Arguments;
import edu.project3.pojo.LogInfo;
import edu.project3.pojo.LogReport;
import edu.project3.pojo.ReportTable;
import edu.project3.service.ArgumentsService;
import edu.project3.service.DateTimeStreamFilterService;
import edu.project3.service.LogParser;
import edu.project3.service.LogReportService;
import edu.project3.service.ReportFileService;
import edu.project3.service.SourceService;
import edu.project3.service.StartDirectoriesCreator;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Application {

    private final StartDirectoriesCreator creator = new StartDirectoriesCreator();
    private final ArgumentsService argumentsService = new ArgumentsService();
    private final SourceService sourceService = new SourceService();
    private final LogParser logParser = new LogParser();
    private final DateTimeStreamFilterService filterService = new DateTimeStreamFilterService();
    private final LogReportService logReportService = new LogReportService();
    private final ReportFileService reportFileService = new ReportFileService();

    public void run(String[] args) {

        try {
            creator.createDirectories();
            Arguments parsedArgs = argumentsService.getArgumentsMap(args);
            List<Path> logFilesList = sourceService.getLogFilesBySources(parsedArgs.paths());
            Stream<LogInfo> logsStream = logParser.getLogsStream(logFilesList);
            Stream<LogInfo> filteredLogsStream = filterService.useTimeArgsForStream(logsStream, parsedArgs);
            LogReport report = logReportService.createLogReport(parsedArgs.paths(), filteredLogsStream);
            List<ReportTable> tables = logReportService.createTables(report);
            reportFileService.createDocument(parsedArgs.format(), tables);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
