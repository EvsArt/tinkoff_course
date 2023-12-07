package edu.project3.pojo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record LogReport(
    List<String> sources,
    long logsCount,
    LocalDate startDate,
    LocalDate endDate,
    double averageResponseSize,
    List<Map.Entry<String, Long>> top3RequestedResources,
    List<Map.Entry<Integer, Long>> top3ResponseCodes,
    List<Map.Entry<String, Long>> top3Users,                    // 1 dop stat
    List<Map.Entry<TimeSection, Long>> timeSectionStat          // 2 dop stat
) {
}
