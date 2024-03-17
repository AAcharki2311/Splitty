package client.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface WriteEventNamesInterface {

    /**
     * Writes the event name to the JSON file
     * @param eventName The name of the event
     */
    public default void writeEventName(String eventName){
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<String> eventNames = readEventsFromJson();

            if(eventNames.contains(eventName)){
                eventNames.remove(eventName);
            }
            eventNames.add(eventName);
            while (eventNames.size() > 4) {
                eventNames.remove(0);
            }
            mapper.writeValue(new File("src/main/resources/recentEvents.json"), eventNames);
            System.out.println("Successfully wrote event to JSON file.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the event names from the JSON file
     * @return The list of event names
     */
    public default List<String> readEventsFromJson(){
        File file = new File("src/main/resources/recentEvents.json");
        if(!file.exists()){
            return new ArrayList<>();
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(file, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
