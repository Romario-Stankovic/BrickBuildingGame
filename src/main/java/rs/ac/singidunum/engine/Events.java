package rs.ac.singidunum.engine;

import rs.ac.singidunum.engine.interfaces.ICallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Events {
    private final Map<Object, List<ICallback>> events = new HashMap<>();

    public void subscribe(String event, ICallback callback) {
        if(!events.containsKey(event)) {
            List<ICallback> callbacks = new ArrayList<>();
            callbacks.add(callback);
            events.put(event, callbacks);
        }else {
            List<ICallback> callbacks = events.get(event);
            if(callbacks.contains(callback)) {
                return;
            }
            callbacks.add(callback);
        }
    }

    public void emit(String name, Object... args) {
        if(!events.containsKey(name)) {
            return;
        }

        List<ICallback> callbacks = events.get(name);

        for(ICallback callback : callbacks) {
            callback.call(args);
        }
    }

}
