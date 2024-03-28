package client.utils;

import java.util.List;

public interface WriteEventNamesInterface {

    /**
     * Writes the event name to the JSON file
     * @param filepath The path to the JSON file
     * @param eventName The name of the event
     * @param id The id of the event
     */
    void writeEventName(String filepath, String eventName, String id);

    /**
     * Reads the event names from the JSON file
     * @param filepath The path to the JSON file
     * @return The list of event names
     */
    List<String> readEventsFromJson(String filepath);
}
