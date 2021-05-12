package com.amosgross.helper;

import com.amosgross.items.JCalDavCalenderItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JCalDavResponseParser {

    public static ArrayList<JCalDavCalenderItem> parseCalendarDump(String calendarDump) {
        ArrayList<JCalDavCalenderItem> calenderItems = new ArrayList<>();
        Pattern calendarItemPattern = Pattern.compile("BEGIN:VEVENT.*?END:VEVENT", Pattern.DOTALL);
        Matcher calendarItemMatcher = calendarItemPattern.matcher(calendarDump);

        while (calendarItemMatcher.find()){
            String calendarEvent = calendarItemMatcher.group();
            String summary = "";
            String description = "";
            String location = "";
            LocalDateTime startTime = null;
            LocalDateTime endTime = null;

            Matcher summaryMatcher = Pattern.compile("SUMMARY:[^\n]*").matcher(calendarEvent);
            if (summaryMatcher.find()) {
                summary = summaryMatcher.group().substring(8);
            }

            Matcher descriptionMatcher = Pattern.compile("DESCRIPTION:[^\n]*").matcher(calendarEvent);
            if (descriptionMatcher.find()) {
                description = descriptionMatcher.group().substring(12);
            }

            Matcher locationMatcher = Pattern.compile("LOCATION:[^\n]*").matcher(calendarEvent);
            if (locationMatcher.find()) {
                location = locationMatcher.group().substring(9);
            }

            Matcher startTimeMatcher = Pattern.compile("(DTSTART[^\n]*)", Pattern.DOTALL).matcher(calendarEvent);
            if (startTimeMatcher.find()) {
                startTime = getDateTimeFromString(startTimeMatcher.group());
            }
            Matcher endTimeMatcher = Pattern.compile("(DTEND[^\n]*)", Pattern.DOTALL).matcher(calendarEvent);
            if (endTimeMatcher.find()) {
                endTime = getDateTimeFromString(endTimeMatcher.group());
            }

            calenderItems.add(new JCalDavCalenderItem(summary, description, location, startTime, endTime));
        }
        return calenderItems;
    }

    private static LocalDateTime getDateTimeFromString(String dateTimeAsString) {
        if (dateTimeAsString.length() == 42 || dateTimeAsString.length() == 40) {
            String timeString = dateTimeAsString.substring(dateTimeAsString.length() - 15);
            return LocalDateTime.parse(timeString, JCalDavConstants.TIME_FORMAT);
        } else {
            System.out.println("Failed to parse time: " + dateTimeAsString);
            return LocalDateTime.of(1, 1, 1, 1, 1, 1);
        }
    }
}
