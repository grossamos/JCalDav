package com.amosgross.helper;

import com.amosgross.JCalDavClient;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class JCalDavRequestForger {
    public static String fetchCalendarItemsAsXML(JCalDavClient client){
        return makeCaldavRequest("REPORT",
                "<c:calendar-query xmlns:d=\"DAV:\" xmlns:c=\"urn:ietf:params:xml:ns:caldav\">\n" +
                        "    <d:prop>\n" +
                        "        <d:getetag />\n" +
                        "        <c:calendar-data />\n" +
                        "    </d:prop>\n" +
                        "    <c:filter>\n" +
                        "        <c:comp-filter name=\"VCALENDAR\" />\n" +
                        "    </c:filter>\n" +
                        "</c:calendar-query>", client);
    }

    public static String createCalendarEntry(JCalDavClient client, String serializedCalendarItem, String calendarItemName){
        return makeCaldavRequest("PUT", serializedCalendarItem, client, calendarItemName);
    }

    public static String deleteCalendarEntry(JCalDavClient client, String calendarItemName){
        return makeCaldavRequest("DELETE", "", client, calendarItemName);
    }

    public static String makeCaldavRequest(String httpMethod, String body, JCalDavClient client, String urlExtension){
        OkHttpClient httpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url(client.getUrl() + urlExtension)
                .header("Authorization",
                        "Basic " + Base64.getEncoder().encodeToString((client.getUsername() + ":" + client.getPassword()).getBytes(StandardCharsets.UTF_8)))
                .header("Depth", "1")
                .header("Prefer", "return-minimal")
                .method(httpMethod, RequestBody.create(body, MediaType.parse("application/xml; charset=utf-8")));
        Request request = requestBuilder.build();
        try {

            Response response = httpClient.newCall(request).execute();
            return Objects.requireNonNull(response.body()).string();

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String makeCaldavRequest(String httpMethod, String body, JCalDavClient client){
        return makeCaldavRequest(httpMethod, body, client, "");
    }
}
