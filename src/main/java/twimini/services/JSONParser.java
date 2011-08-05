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
    private static String urlPrefix = "http://localhost:8080";

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

    public static JSONObject getFeedFromJSON(String uid, String apikey) throws Exception {
        String url = String.format("%s/api/user/%s/getFeed?apikey=%s", urlPrefix, uid, apikey);
        return (JSONObject) JSONParser.getData(url);
    }

    public static JSONObject getTweetListFromJSON(String uid, String apikey) throws Exception {
        String url = String.format("%s/api/user/%s/getTweetList?apikey=%s", urlPrefix, uid, apikey);
        return (JSONObject) JSONParser.getData(url);
    }

    public static JSONObject getFollowingFromJSON(String uid, String apikey) throws Exception {
        String addApikey = apikey == null ? "" : "?apikey=" + apikey;
        String url = String.format("%s/api/user/%s/getFollowing%s", urlPrefix, uid, addApikey);
        return (JSONObject) JSONParser.getData(url);
    }

    public static JSONObject getFollowersFromJSON(String uid, String apikey) throws Exception {
        String addApikey = apikey == null ? "" : "?apikey=" + apikey;
        String url = String.format("%s/api/user/%s/getFollowers%s", urlPrefix, uid, addApikey);
        return (JSONObject) JSONParser.getData(url);
    }

    public static JSONObject getTweetDetailsFromJSON(String pid, String apikey) throws Exception {
        String url = String.format("%s/api/tweet/%s/getTweetDetails?apikey=%s", urlPrefix, pid, apikey);
        return (JSONObject) JSONParser.getData(url);
    }

    public static JSONObject createTweetFromJSON(String tweet, String apikey) throws Exception {
        String url = String.format("%s/api/tweet/create?tweet=%s&apikey=%s", urlPrefix, tweet, apikey);
        return (JSONObject) JSONParser.getData(url);
    }

    public static JSONObject followFromJSON(String uid, String apikey) throws Exception {
        String url = String.format("%s/api/user/%s/follow?apikey=%s", urlPrefix, uid, apikey);
        return (JSONObject) JSONParser.getData(url);
    }

    public static JSONObject unfollowFromJSON(String uid, String apikey) throws Exception {
        String url = String.format("%s/api/user/%s/unfollow?apikey=%s", urlPrefix, uid, apikey);
        return (JSONObject) JSONParser.getData(url);
    }
}
