package edu.project3.pojo;

import java.time.LocalTime;

public enum TimeSection {
    NIGHT(LocalTime.MIDNIGHT, LocalTime.of(6, 0)),
    MORNING(LocalTime.of(6, 0), LocalTime.NOON),
    AFTERNOON(LocalTime.NOON, LocalTime.of(18, 0)),
    EVENING(LocalTime.of(18, 0), LocalTime.MIDNIGHT);

    private final LocalTime sectionStart;
    private final LocalTime sectionEnd;

    public static TimeSection getTimeSectionByTime(LocalTime time) {
        if (time.isAfter(AFTERNOON.sectionStart)) {
            if (time.isAfter(EVENING.sectionStart)) {
                return EVENING;
            } else {
                return AFTERNOON;
            }
        } else {
            if (time.isAfter(MORNING.sectionStart)) {
                return MORNING;
            } else {
                return NIGHT;
            }
        }
    }

    TimeSection(LocalTime sectionStart, LocalTime sectionEnd) {
        this.sectionStart = sectionStart;
        this.sectionEnd = sectionEnd;
    }

    public LocalTime getSectionStart() {
        return sectionStart;
    }

    public LocalTime getSectionEnd() {
        return sectionEnd;
    }
}
