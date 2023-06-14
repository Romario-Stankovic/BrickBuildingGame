package rs.ac.singidunum.engine;

import java.util.HashMap;

// Settings class
// Used to store game settings
public class Settings {

    // Settings map
    private static final HashMap<String, Object> settings = new HashMap<>();

    // Set a setting value
    public static void set(String key, Object value) {
        settings.put(key, value);
    }

    // Get a setting value
    public static <T> T get(String key, Class<T> type) {
            // Get the value
            Object value = settings.get(key);

            // Check if the value is of the given type
            if(type.isInstance(value)) {
                // If it is, return it
                return type.cast(value);
            } else {
                // If not, return null
                return null;
            }
    }

}
