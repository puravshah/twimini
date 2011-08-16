package twimini.services;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 8/3/11
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONParser {
    private static String urlPrefix = "http://localhost:8080";

    public static JSONObject getData(String url) {
        JSONObject jsonObject = new JSONObject();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String json = "", temp;
            while ((temp = reader.readLine()) != null) json += temp;
            jsonObject = (JSONObject) JSONValue.parse(json);
        } catch (MalformedURLException e) {
            jsonObject.put("status", "0");
            jsonObject.put("errorMessage", "Invalid Request");
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("status", "0");
            jsonObject.put("errorMessage", e.toString());
        }
        return jsonObject;
    }

    public static JSONObject postData(String url, Map<String, String> attributes) {
        try {
            //the URL to which we want to post
            URLConnection urlConn = new URL(url).openConnection();
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            DataOutputStream printout = new DataOutputStream(urlConn.getOutputStream());

            //put the POST parameters here
            String content = "";
            for (String key : attributes.keySet()) content += key + "=" + URLEncoder.encode(attributes.get(key)) + "&";
            printout.writeBytes(content);
            //System.out.println("content " + content);
            printout.flush();
            printout.close();

            //fetch response from server
            BufferedReader input = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String json = "", line;
            while ((line = input.readLine()) != null) json += line;
            return (JSONObject) JSONValue.parse(json);
        } catch (MalformedURLException e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", "Invalid Request");
            }};
        } catch (final Exception e) {
            e.printStackTrace();
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", e.toString());
            }};
        }
    }

    public static JSONObject getFeedFromJSON(String uid, String apikey, String start, String count) throws Exception {
        String attributes = "?apikey=" + apikey;
        if (start != null) attributes += "&start=" + start;
        if (count != null) attributes += "&count=" + count;
        String url = String.format("%s/api/user/%s/getFeed%s", urlPrefix, uid, attributes);
        return JSONParser.getData(url);
    }

    public static JSONObject getTweetListFromJSON(String uid, String apikey, String start, String count) throws Exception {
        String attributes = "?apikey=" + apikey;
        if (start != null) attributes += "&start=" + start;
        if (count != null) attributes += "&count=" + count;
        String url = String.format("%s/api/user/%s/getTweetList%s", urlPrefix, uid, attributes);
        return JSONParser.getData(url);
    }

    public static JSONObject getFollowingFromJSON(String uid, String apikey, String start, String count) throws Exception {
        String attributes = apikey == null ? "" : "?apikey=" + apikey;
        if (start != null) attributes += "&start=" + start;
        if (count != null) attributes += "&count=" + count;
        if (attributes.charAt(0) == '&') attributes = "?" + attributes.substring(1);
        String url = String.format("%s/api/user/%s/getFollowing%s", urlPrefix, uid, attributes);
        return JSONParser.getData(url);
    }

    public static JSONObject getFollowersFromJSON(String uid, String apikey, String start, String count) throws Exception {
        String attributes = apikey == null ? "" : "?apikey=" + apikey;
        if (start != null) attributes += "&start=" + start;
        if (count != null) attributes += "&count=" + count;
        if (attributes.charAt(0) == '&') attributes = "?" + attributes.substring(1);
        String url = String.format("%s/api/user/%s/getFollowers%s", urlPrefix, uid, attributes);
        return JSONParser.getData(url);
    }

    public static JSONObject getTweetDetailsFromJSON(String pid, String apikey) throws Exception {
        String url = String.format("%s/api/tweet/%s/getTweetDetails?apikey=%s", urlPrefix, pid, apikey);
        return JSONParser.getData(url);
    }

    public static JSONObject createTweetFromJSON(String tweet, String apikey) throws Exception {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("tweet", tweet);
        attributes.put("apikey", apikey);
        return JSONParser.postData(urlPrefix + "/api/tweet/create", attributes);
    }

    public static JSONObject followFromJSON(String uid, String apikey) throws Exception {
        String url = String.format("%s/api/user/%s/follow?apikey=%s", urlPrefix, uid, apikey);
        return JSONParser.getData(url);
    }

    public static JSONObject unfollowFromJSON(String uid, String apikey) throws Exception {
        String url = String.format("%s/api/user/%s/unfollow?apikey=%s", urlPrefix, uid, apikey);
        return JSONParser.getData(url);
    }

    public static JSONObject loginFromJSON(String email, String password) throws Exception {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("email", email);
        attributes.put("password", password);
        return JSONParser.postData(urlPrefix + "/api/user/login", attributes);
    }

    public static JSONObject searchFromJSON(String query, String apikey, String start, String count) {
        String APIKEY = apikey == null ? "" : "&apikey=" + apikey;
        String url = String.format("%s/api/search?query=%s&start=%s&count=%s%s", urlPrefix, query, start, count, APIKEY);
        return JSONParser.getData(url);
    }
}
