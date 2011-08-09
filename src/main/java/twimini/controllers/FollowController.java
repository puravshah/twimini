package twimini.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import twimini.services.*;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh.k
 * Date: 6/30/11
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class FollowController {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;

    @Autowired
    public FollowController(UserService userService, FollowService followService, TweetService tweetService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
    }

    @RequestMapping("/user/unfollow")
    @ResponseBody
    public JSONObject unfollow(@RequestParam String id, HttpSession session) {
        try {
            String apikey = session.getAttribute("apikey").toString();
            String isActivated = userService.getIsActivated(APIKEYService.getUid(apikey));
            if(!isActivated.equals("activated")) {
                return new JSONObject() {{
                    put("status", "0");
                    put("errorMessage", "You need to activate your account before Unfollowing someone. Kindly check your email");
                }};
            }
            return JSONParser.unfollowFromJSON(id, apikey);
        } catch (final NullPointerException e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", "You need to login first");
            }};
        } catch (final Exception e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", e.toString());
            }};
        }
    }
    /*Hashtable<String, String> unFollow(@RequestParam String id, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        String uid = (String) session.getAttribute("uid");

        try {
            int rows = followService.removeFollowing(uid, id);
            if (rows == 0) {
                ret.put("status", "0");
                ret.put("errorMsg", "You are not following this user");
            } else ret.put("status", "1");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
        }

        return ret;
    }*/

    @RequestMapping("/user/follow")
    @ResponseBody
    public JSONObject follow(@RequestParam String id, HttpSession session) {
        try {
            String apikey = session.getAttribute("apikey").toString();
            String isActivated = userService.getIsActivated(APIKEYService.getUid(apikey));
            if(!isActivated.equals("activated")) {
                return new JSONObject() {{
                    put("status", "0");
                    put("errorMessage", "You need to activate your account before Following someone. Kindly check your email");
                }};
            }
            return JSONParser.followFromJSON(id, apikey);
        } catch (final NullPointerException e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", "You need to login first");
            }};
        } catch (final Exception e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", e.toString());
            }};
        }
    }
    /*Hashtable<String, String> follow(@RequestParam String id, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        String uid = (String) session.getAttribute("uid");
        UserModel u = null;

        try {
            u = followService.addFollowing(uid, id);
        } catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMsg", "Invalid user id");
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
            return ret;
        }

        ret.put("status", "1");
        ret.put("uid", "" + u.getUid());
        ret.put("name", u.getName());
        ret.put("email", u.getEmail());
        return ret;
    }*/

    @RequestMapping("/user/getFollowers")
    @ResponseBody
    public JSONObject getFollowers(@RequestParam String uid, String start, String count, HttpSession session) {
        if (count == null || count.equals("")) count = "10";
        if (start == null || start.equals("")) start = "0";
        try {
            Integer.parseInt(count);
        } catch (Exception e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", "count attribute should be a valid number");
            }};
        }

        try {
            Integer.parseInt(start);
        } catch (Exception e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", "start attribute should be a valid number");
            }};
        }

        try {
            return JSONParser.getFollowersFromJSON(uid, (String) session.getAttribute("apikey"), start, count);
        } catch (final Exception e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", e.toString());
            }};
        }
    }
    /*public List<UserModel> followGet(@RequestParam String uid, HttpSession session) {
        String user = (String) session.getAttribute("uid");
        List<UserModel> ret = null;

        try {
            ret = followService.getFollower2(uid, user);
            if (ret == null) throw new Exception("Could not render followers list");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }*/

    @RequestMapping(value = "/user/getFollowing")
    @ResponseBody
    public JSONObject getFollowing(@RequestParam String uid, String start, String count, HttpSession session) {
        if (count == null || count.equals("")) count = "10";
        if (start == null || start.equals("")) start = "0";
        try {
            Integer.parseInt(count);
        } catch (Exception e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", "count attribute should be a valid number");
            }};
        }

        try {
            Integer.parseInt(start);
        } catch (Exception e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", "start attribute should be a valid number");
            }};
        }

        try {
            return JSONParser.getFollowingFromJSON(uid, (String) session.getAttribute("apikey"), start, count);
        } catch (final Exception e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", e.toString());
            }};
        }
    }
    /*public List<UserModel> followerGet(@RequestParam String uid, HttpSession session) {
        List<UserModel> ret = null;

        try {
            String user = (String) session.getAttribute("uid");
            ret = followService.getFollowing2(uid, user);
            if (ret == null) throw new Exception("Could not render following list");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }*/
}
