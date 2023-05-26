package rs.ac.singidunum.engine;

import java.util.HashMap;

public class Settings {

    private static final HashMap<String, Object> settings = new HashMap<>();

    public static void set(String key, Object value) {
        settings.put(key, value);
    }

    public static <T> T get(String key, Class<T> type) {
            Object value = settings.get(key);

            if(type.isInstance(value)) {
                return type.cast(value);
            } else {
                return null;
            }
    }

}
