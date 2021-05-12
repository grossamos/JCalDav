# JCalDav

## Description
JCalDav, as the name might imply is a Library for Cal Dav within Java.
Its main goal is to fix the issues with other libraries (such as CalDavForJ), not forcing a developer to understand the details of the CalDav Standard.

As a standard, CalDav can be used as a way for applications to interface with Calendar orchestration servers, such as iCloud or Nextcloud.
JCalDav is thus a library designed to allow Java applications to make use of this Standard.

## Features
In the way it is currently set up JCalDav only supports some basic interactions with Cal Dav servers:
- serializing the Calendar to Java Objects
- publishing Calendar entries
- deleting entries added by JCalDav

With JCalDav being somewhat new there are still a bunch of things that it still can't do:
- deleting existing entries
- selectively serializing the Calendar
- creating special CalDav objects (such as TODO lists)
- creating "All-day" events (although you can still set an event's time to cover up the entire day)

## Usage
Here we will be running through the main three use cases for JCalDav: fetching, creating and finally deleting calendar data.

### Fetching calendar data
Fetching Calendar data can be done by initializing a ``JCalDavClient`` object and calling ``fetchCalendar``.
This returns an ``ArrayList`` for ``JCalDavCalendarItem``, which contain the data found in your calendar.

Example:

```java
JCalDavClient client = new JCalDavClient();
ArrayList<JCalDavCalendarItem> items = client.fetchCalendar();

for(JCalDavCalendarItem item : items){
    System.out.println(item.summary);
}
```

### Creating calendar items
In order to add a calendar item to your calendar, you have to first create a ``JCalDavCalendarItem`` and then push that to the server using a ``JCalDavClient`` object.

Example:

```java
JCalDavClient client = new JCalDavClient();

JCalDavCalenderItem item = new JCalDavCalenderItem("TEST TEST", "still testing", "testlandia", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
client.saveItemToCalendar(item);
```

### Deleting calendar items
Currently you can only delete calendar items created by JCalDav (this is because deletion is handled filenames not present, when JCalDav does it's initial fetch).
Even though I would like to add this feature, in the mean time, you can delete items you created with JCalDav as follows:

Example:
```java
JCalDavClient client = new JCalDavClient();

JCalDavCalenderItem item = new JCalDavCalenderItem("TEST TEST", "still testing", "testlandia", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
client.saveItemToCalendar(item);
client.deleteItemFromCalendar(item);
```

## Contributing guide
To build the project from source run ``maven install``.

If you wish to run tests on your calDav server simply set the ``$JDAV_UNAME``, ``$JDAV_URL`` and ``$JDAV_PASS`` env variables.
Otherwise, all integration and unit tests that rely on a live server will be skipped.

For all bugs/errors you encounter please raise an Isses in Github or create a pull request accordingly.