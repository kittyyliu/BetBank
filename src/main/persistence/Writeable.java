package persistence;
import org.json.JSONObject;

import java.io.*;


//based on Writeable from JsonSerializationDemo
public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}