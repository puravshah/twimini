package twimini.services;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 8/3/11
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONParser {

    public static JSONObject getData(String url) {
        JSONObject jsonObject = new JSONObject();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String json = "", temp;
            while ((temp = reader.readLine()) != null) json += temp;
            jsonObject = (JSONObject)JSONValue.parse(json);
        } catch (MalformedURLException e) {
            jsonObject.put("status", "0");
            jsonObject.put("errorMsg", "Invalid url");
        } catch (Exception e) {
            jsonObject.put("status", "0");
            jsonObject.put("errorMsg", e.toString());
        }
        return jsonObject;
    }
}
