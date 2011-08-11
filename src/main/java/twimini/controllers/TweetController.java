package twimini.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import twimini.ActivationMail;
import twimini.model.TweetModel;
import twimini.model.UserModel;
import twimini.services.*;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 6/30/11
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class TweetController {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;

    @Autowired
    public TweetController(UserService userService, FollowService followService, TweetService tweetService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
    }

    @RequestMapping("/test")
    public ModelAndView test() {
        return new ModelAndView();
    }

    @RequestMapping("/tweet")
    public ModelAndView tweetGet(HttpSession session) {
        String uid = (String) session.getAttribute("uid");
        List<UserModel> followingList = null, followerList = null;
        int tweetCount = 0, followingCount = 0, followerCount = 0;

        try {
            followingList = followService.getFollowing2(uid, uid, "0", "10");
            followerList = followService.getFollower2(uid, uid, "0", "10");
            tweetCount = tweetService.getTweetCount(uid);
            followingCount = followService.getFollowingCount(uid);
            followerCount = followService.getFollowerCount(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("uid", uid);
        mv.addObject("name", session.getAttribute("name"));
        mv.addObject("followingList", followingList);
        mv.addObject("followerList", followerList);
        mv.addObject("tweetCount", tweetCount);
        mv.addObject("followingCount", followingCount);
        mv.addObject("followerCount", followerCount);
        return mv;
    }

    @RequestMapping("/tweet/create")
    @ResponseBody
    JSONObject createTweet(@RequestParam final String tweet, HttpSession session) {
        try {
            String apikey = session.getAttribute("apikey").toString();
            String isActivated = userService.getIsActivated(APIKEYService.getUid(apikey));
            if(!isActivated.equals("activated")) {
                return new JSONObject() {{
                put("status", "0");
                put("errorMessage", "You need to activate your account before posting a tweet. Kindly check your email");
            }};
            }
            JSONObject jsonObject = JSONParser.createTweetFromJSON(tweet, apikey);
            if(jsonObject.get("status").equals("0")) return jsonObject;
            return JSONParser.getTweetDetailsFromJSON(jsonObject.get("pid").toString(), apikey);
        } catch (final NullPointerException e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", "You need to login first");
            }};
        } catch (final Exception e) {
            e.printStackTrace();
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", e.toString());
            }};
        }
    }

    @RequestMapping("/tweet/getTweetDetails")
    @ResponseBody
    Hashtable<String, String> getTweetDetails(@RequestParam final String pid, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        String uid = (String) session.getAttribute("uid");

        TweetModel t = null;
        try {
            t = tweetService.getTweetDetails(pid);
            if (t == null) throw new Exception("Invalid tweet");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
            return null;
        }

        ret.put("pid", "" + t.getPid());
        ret.put("uid", "" + t.getUid());
        ret.put("tweet", t.getTweet());

        ret.put("timestamp",""+(long)(t.getTimestamp()));
        ret.put("status", "1");
        return ret;
    }

    @RequestMapping("/tweet/getTweetList")
    @ResponseBody
    JSONObject getTweetList(@RequestParam final String uid, String start, String count, HttpSession session) {
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
            return JSONParser.getTweetListFromJSON(uid, (String)session.getAttribute("apikey"), start, count);
        } catch (final Exception e) {
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", e.toString());
            }};
        }
    }

    @RequestMapping("/tweet/getFeed")
    @ResponseBody
    JSONObject getFeed(@RequestParam final String uid, String start, String count, HttpSession session) {
        if (true) {

            Thread thread = new ActivationMail(userService);
            thread.start();
        }
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
            JSONObject jsonObject = JSONParser.getFeedFromJSON(uid, session.getAttribute("apikey").toString(), start, count);
            if(jsonObject.get("status").equals("0") && jsonObject.get("errorMessage").equals("Invalid apikey")) {
                session.invalidate();
            }
            return jsonObject;
        } catch (final Exception e) {
            e.printStackTrace();
            return new JSONObject() {{
                put("status", "0");
                put("errorMessage", e.toString());
            }};
        }
    }
}
