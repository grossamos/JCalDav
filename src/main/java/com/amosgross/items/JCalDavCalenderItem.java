package com.amosgross.items;

import java.time.LocalDateTime;

public class JCalDavCalenderItem {
    private final String summary;
    private final String description;
    private final String location;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public JCalDavCalenderItem(String summary, String description, String location, LocalDateTime startTime, LocalDateTime endTime) {
        this.summary = summary;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String hash(){
        return String.valueOf((summary + description + location + startTime.toString() + endTime.toString()).hashCode());
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
