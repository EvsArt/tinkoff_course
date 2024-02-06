package edu.project3.service;

import edu.project3.pojo.LogInfo;
import edu.project3.pojo.LogReport;
import edu.project3.pojo.ReportTable;
import edu.project3.pojo.TimeSection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class LogReportService {

    private final static int TOP_COUNT = 3;

    public LogReport createLogReport(List<String> sources, Stream<LogInfo> logsStream) {

        int count = 0;
        LocalDate startDate = LocalDate.MAX;
        LocalDate endDate = LocalDate.MIN;
        long sumResponseByteSize = 0;
        Map<String, Long> topRequestedResources = new HashMap<>();
        Map<Integer, Long> topResponseCodes = new HashMap<>();
        Map<String, Long> topUsers = new HashMap<>();
        Map<TimeSection, Long> topTimeSections = new HashMap<>();

        for (LogInfo log : logsStream.toList()) {
            count++;
            LocalDate date = log.getDateTime().toLocalDate();
            if (date.isBefore(startDate)) {
                startDate = date;
            }
            if (date.isAfter(endDate)) {
                endDate = date;
            }

            sumResponseByteSize += log.getBodyBytesSent();

            String resource = log.getResource();
            topRequestedResources.put(resource, topRequestedResources.getOrDefault(resource, 0L) + 1);

            int responseCode = log.getResponseStatus();
            topResponseCodes.put(responseCode, topResponseCodes.getOrDefault(responseCode, 0L) + 1);

            String userInfo = log.getClientAddress() + ": " + log.getUserAgent();
            topUsers.put(userInfo, topUsers.getOrDefault(userInfo, 0L) + 1);

            TimeSection timeSection =
                TimeSection.getTimeSectionByTime(log.getDateTime().toLocalTime());
            topTimeSections.put(timeSection, topTimeSections.getOrDefault(timeSection, 0L) + 1);

        }

        return new LogReport(
            sources,
            count,
            startDate,
            endDate,
            (double) sumResponseByteSize / count,

            topRequestedResources.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<String, Long> it) -> it.getValue().intValue()).reversed())
                .limit(TOP_COUNT)
                .toList(),

            topResponseCodes.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<Integer, Long> it) -> it.getValue().intValue()).reversed())
                .limit(TOP_COUNT)
                .toList(),

            topUsers.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<String, Long> it) -> it.getValue().intValue()).reversed())
                .limit(TOP_COUNT)
                .toList(),

            topTimeSections.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<TimeSection, Long> it) -> it.getValue().intValue())
                    .reversed())
                .toList()

        );

    }

    @SuppressWarnings("MultipleStringLiterals")
    public List<ReportTable> createTables(LogReport report) {

        List<ReportTable> res = new ArrayList<>();

        ReportTable table1 = new ReportTable("Общая информация", List.of(
            List.of("Метрика", "Значение"),
            List.of("Источник(-и)", String.join(", ", report.sources())),
            List.of("Начальная дата", report.startDate().format(DateTimeFormatter.ISO_LOCAL_DATE)),
            List.of("Конечная дата", report.endDate().format(DateTimeFormatter.ISO_LOCAL_DATE)),
            List.of("Количество запросов", String.valueOf(report.logsCount())),
            List.of("Средний размер ответа", String.valueOf(report.averageResponseSize()))
        ));
        res.add(table1);

        List<List<String>> raws2 = new ArrayList<>();
        raws2.add(List.of("Ресурс", "Количество"));
        ReportTable table2 = new ReportTable("Запрашиваемые ресурсы", raws2);
        for (Map.Entry<String, Long> entry : report.top3RequestedResources()) {
            table2.args().add(List.of(entry.getKey(), String.valueOf(entry.getValue())));
        }
        res.add(table2);

        List<List<String>> raws3 = new ArrayList<>();
        raws3.add(List.of("Код", "Количество"));
        ReportTable table3 = new ReportTable("Коды ответа", raws3);
        for (Map.Entry<Integer, Long> entry : report.top3ResponseCodes()) {
            table3.args().add(List.of(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
        }
        res.add(table3);

        List<List<String>> raws4 = new ArrayList<>();
        raws4.add(List.of("Данные пользователя (Адрес и агент)", "Количество запросов"));
        ReportTable table4 = new ReportTable("Активные пользователи", raws4);
        for (Map.Entry<String, Long> entry : report.top3Users()) {
            table4.args().add(List.of(entry.getKey(), String.valueOf(entry.getValue())));
        }
        res.add(table4);

        List<List<String>> raws5 = new ArrayList<>();
        raws5.add(List.of("Временной период", "Количество запросов"));
        ReportTable table5 = new ReportTable("Статистика по временным периодам", raws5);
        for (Map.Entry<TimeSection, Long> entry : report.timeSectionStat()) {
            table5.args().add(List.of(entry.getKey().name(), String.valueOf(entry.getValue())));
        }
        res.add(table5);

        return res;

    }

}
