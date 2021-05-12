package com.amosgross;

import com.amosgross.helper.JCalDavItemSerializer;
import com.amosgross.helper.JCalDavRequestForger;
import com.amosgross.helper.JCalDavResponseParser;
import com.amosgross.items.JCalDavCalenderItem;

import java.util.ArrayList;

public class JCalDavClient {
    private final String url;
    private final String username;
    private final String password;

    public JCalDavClient(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public ArrayList<JCalDavCalenderItem> fetchCalendar(){
        String calendarDump = JCalDavRequestForger.fetchCalendarItemsAsXML(this);
        return JCalDavResponseParser.parseCalendarDump(calendarDump);
    }

    public void saveItemToCalendar(JCalDavCalenderItem calenderItem){
        String calenderItemAsString = JCalDavItemSerializer.serializeXMLCalendarObject(calenderItem);
        JCalDavRequestForger.createCalendarEntry(this, calenderItemAsString, calenderItem.hash());
    }

    public void deleteItemFromCalendar(JCalDavCalenderItem calenderItem){
        JCalDavRequestForger.deleteCalendarEntry(this, calenderItem.hash());
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
