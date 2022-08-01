package persistence;

import org.json.JSONObject;

import java.io.*;


// code based on Writeable from JsonSerializationDemo
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}