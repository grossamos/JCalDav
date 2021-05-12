package com.amosgross.helper;

import com.amosgross.items.JCalDavCalenderItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JCalDavResponseParserTest {

    private static final String TEST_CAL_DAV_OBJ =
            "<?xml version=\"1.0\"?>\n" +
            "<d:multistatus xmlns:d=\"DAV:\" xmlns:s=\"http://sabredav.org/ns\" xmlns:cal=\"urn:ietf:params:xml:ns:caldav\" xmlns:cs=\"http://calendarserver.org/ns/\" xmlns:oc=\"http://owncloud.org/ns\" xmlns:nc=\"http://nextcloud.org/ns\"><d:response><d:href>/remote.php/dav/calendars/inf20038/vorlesungen/-1402982999.ics</d:href><d:propstat><d:prop><d:getetag>&quot;fd1c45accfff76affd4c683d8e22f0f6&quot;</d:getetag><cal:calendar-data>BEGIN:VCALENDAR\n" +
            "VERSION:2.0\n" +
            "PRODID:-//Sabre//Sabre VObject 4.3.0//EN\n" +
            "BEGIN:VEVENT\n" +
            "SUMMARY:ULTRA EVENT\n" +
            "DESCRIPTION:Osterrieder, U-dawg\n" +
            "DTSTART;TZID=Europe/Berlin:20210121T090000\n" +
            "DTEND;TZID=Europe/Berlin:20210121T120000\n" +
            "LOCATION:fucking timbuktu\n" +
            "UID:sabre-vobject-5ee1cdfb-e9e1-43be-a54b-bc46abdd2c0f\n" +
            "DTSTAMP:20201213T215807Z\n" +
            "END:VEVENT\n" +
            "END:VCALENDAR\n" +
            "</cal:calendar-data></d:prop><d:status>HTTP/1.1 200 OK</d:status></d:propstat></d:response><d:response><d:href>/remote.php/dav/calendars/inf20038/vorlesungen/-1402983027.ics</d:href><d:propstat><d:prop><d:getetag>&quot;b519d3b6c2fd257cc4800cc6a2ca4f69&quot;</d:getetag>";

    @Test
    void can_correctly_get_item_meta_info(){
        JCalDavCalenderItem item = JCalDavResponseParser.parseCalendarDump(TEST_CAL_DAV_OBJ).get(0);
        assertEquals("ULTRA EVENT", item.getSummary());
        assertEquals("Osterrieder, U-dawg", item.getDescription());
        assertEquals("fucking timbuktu", item.getLocation());
        assertNotNull(item.getStartTime());
        assertNotNull(item.getEndTime());
    }

    @Test
    void can_corectly_get_multiple_items(){
        assertEquals(2, JCalDavResponseParser.parseCalendarDump(TEST_CAL_DAV_OBJ + TEST_CAL_DAV_OBJ).size());
    }
}