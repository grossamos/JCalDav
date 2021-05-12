package com.amosgross.helper;

import com.amosgross.JCalDavClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class JCalDavRequestForgerTest {

    // to properly test this class, set $JDAV_UNAME $JDAV_URL and $JDAV_PASS as env vars

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

        client = Mockito.mock(JCalDavClient.class);
        Mockito.when(client.getPassword()).thenReturn(password);
        Mockito.when(client.getUsername()).thenReturn(uname);
        Mockito.when(client.getUrl()).thenReturn(url);
    }

    @Test
    @EnabledIf("isContainsCustomURL")
    void request_ends_in_actual_cal_dav_data(){
        assertTrue(JCalDavRequestForger.fetchCalendarItemsAsXML(client).startsWith("<?xml version=\"1.0\"?>"));
    }

    static boolean isContainsCustomURL(){
        return containsCustomURL;
    }

}