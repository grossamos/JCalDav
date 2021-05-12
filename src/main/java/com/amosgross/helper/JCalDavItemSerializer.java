package com.amosgross.helper;

import com.amosgross.items.JCalDavCalenderItem;

import java.time.LocalDateTime;

public class JCalDavItemSerializer {
    public static String serializeXMLCalendarObject(JCalDavCalenderItem calItem){
        return "BEGIN:VCALENDAR\n" +
                "BEGIN:VEVENT\n" +
                "SUMMARY:" + calItem.getSummary() + "\n" +
                "DESCRIPTION:" + calItem.getDescription() + "\n" +
                "DTSTART;TZID=Europe/Berlin:" + generateTimeString(calItem.getStartTime()) + "\n" +
                "DTEND;TZID=Europe/Berlin:" + generateTimeString(calItem.getEndTime())  + "\n" +
                "LOCATION:"+ calItem.getLocation() + "\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR";
    }

    private static String generateTimeString(LocalDateTime time){
        return time.format(JCalDavConstants.TIME_FORMAT);
    }
}
