package client.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WriteEventNames implements WriteEventNamesInterface {

    /**
     * Writes the event name to the JSON file
     * @param filepath The path to the JSON file
     * @param eventName The name of the event
     * @param id The id of the event
     */
    @Override
    public void writeEventName(String filepath, String eventName, String id) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<String> eventNames = readEventsFromJson(filepath);

            if(eventNames.contains(eventName)){
                eventNames.remove(eventName);
            }

            for(String n : eventNames){
                if(n.contains("id: " + id)){
                    eventNames.remove(n);
                }
            }

            eventNames.add(eventName);
            while (eventNames.size() > 3) {
                eventNames.remove(0);
            }
            mapper.writeValue(new File(filepath), eventNames);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the event names from the JSON file
     * @param filepath The path to the JSON file
     * @return The list of event names
     */
    @Override
    public List<String> readEventsFromJson(String filepath){
        File file = new File(filepath);
        if(!file.exists()){
            return new ArrayList<>();
        }
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(file, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            if(e instanceof MismatchedInputException){
                return new ArrayList<>();
            } else{
                throw new RuntimeException(e);
            }
        }
    }

}