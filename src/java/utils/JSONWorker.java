/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONWorker {
    
    protected static Gson gson = new Gson();

    public static JsonObject getJsonObj(String json) {
        try {
            return (JsonObject) new JsonParser().parse(json);
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }

    public static JsonArray getJsonArr(String json) {
        try {
            return (JsonArray) new JsonParser().parse(json);
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return null;
    }
}
