package com.amosgross;

import com.amosgross.items.JCalDavCalenderItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JCalDavClientTest {

    private static JCalDavClient client;

    private static boolean containsCustomURL;

    private static final String UNAME_ENV = "JDAV_UNAME";
    private static final String URL_ENV = "JDAV_URL";
    private static final String PASSWD_ENV = "JDAV_PASS";

    @BeforeAll
    static void init(){
        String url = System.getenv(URL_ENV);
        String uname = System.getenv(UNAME_ENV);
        String password = System.getenv(PASSWD_ENV);
        containsCustomURL = !(url == null || uname == null || password == null);

        if (containsCustomURL){
            client = new JCalDavClient(url, uname, password);
        }
    }

    @Test
    @EnabledIf("isContainsCustomURL")
    void can_create_some_request(){
        // this is probably more of an integration test than a unit test
        assertNotNull(client.fetchCalendar());
    }

    @Test
    @Disabled
    // enable this if you want to do an intigration test on creating Calendar Items
    void can_create_item(){
        JCalDavCalenderItem item = new JCalDavCalenderItem("TEST TEST", "still testing", "testlandia", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        client.saveItemToCalendar(item);
        client.deleteItemFromCalendar(item);
    }



    static boolean isContainsCustomURL(){
        return containsCustomURL;
    }

}