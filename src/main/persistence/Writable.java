package persistence;

import org.json.JSONObject;

// Description: All classes that use the JSON save and load functionality should implement this interface
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
