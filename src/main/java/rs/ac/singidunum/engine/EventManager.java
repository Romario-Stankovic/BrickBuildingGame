package rs.ac.singidunum.engine;

import rs.ac.singidunum.engine.interfaces.ICallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Event manager class
// Used to manage events sent to the engine
public class EventManager {
    // List of callbacks
    private final Map<String, List<ICallback>> events = new HashMap<>();

    public void subscribe(String event, ICallback callback) {
        // Check if the event exists
        if(!events.containsKey(event)) {
            // If not, create it
            List<ICallback> callbacks = new ArrayList<>();
            callbacks.add(callback);
            events.put(event, callbacks);
        }else {
            // If it does, add the callback to the list of callbacks for that event
            List<ICallback> callbacks = events.get(event);
            if(callbacks.contains(callback)) {
                return;
            }
            callbacks.add(callback);
        }
    }

    // Emit an event with the given name and arguments (if any)
    public void emit(String name, Object... args) {
        // Check if the event exists
        if(!events.containsKey(name)) {
            // If not, return
            return;
        }

        // Get the list of callbacks for the event
        List<ICallback> callbacks = events.get(name);

        // Call each callback with the given arguments
        for(ICallback callback : callbacks) {
            callback.call(args);
        }
    }

}
