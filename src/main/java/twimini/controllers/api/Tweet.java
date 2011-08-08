package twimini.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import twimini.model.TweetModel;
import twimini.services.APIKEYService;
import twimini.services.FollowService;
import twimini.services.TweetService;
import twimini.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 6/30/11
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class Tweet {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;

    @Autowired
    public Tweet(UserService userService, FollowService followService, TweetService tweetService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
    }

    /* REST API for creating a tweet */
    @RequestMapping(value = "/api/tweet/create", method = RequestMethod.POST)
    @ResponseBody
    Hashtable<String, String> createTweet(@RequestParam final String tweet, @RequestParam String apikey, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        TweetModel t = null;
        try {
            String uid = APIKEYService.getUid(apikey);
            t = tweetService.addTweet(uid, tweet);
            ret.put("status", "1");
            ret.put("pid", "" + t.getPid());
        } catch(NullPointerException e) {
            ret.put("status", "0");
            ret.put("errorMessage", "You need to login first");
        } catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMessage", "Invalid APIKEY");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMessage", e.toString());
        }
        return ret;
    }

    @RequestMapping("/api/tweet/{pid}/getTweetDetails")
    @ResponseBody
    Hashtable<String, Object> getTweetDetails(@PathVariable String pid, @RequestParam String apikey, HttpSession session) {
        Hashtable<String, Object> ret = new Hashtable<String, Object>();

        try {
            String uid = APIKEYService.getUid(apikey);
        }
        catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMessage", "Invalid APIKEY");
        }
        catch (Exception e) {
            ret.put("status", "0");
            ret.put("errorMessage", e.toString());
        }

        try {
            TweetModel t = tweetService.getTweetDetails(pid);
            /*ret.put("pid", "" + t.getPid());
            ret.put("uid", "" + t.getUid());
            ret.put("tweet", t.getTweet());
            ret.put("timestamp", t.getTimestamp());*/
            ret.put("status", "1");
            ret.put("tweetDetails", t);
        } catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMessage", "Invalid Tweet ID");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMessage", e.toString());
        }
        return ret;
    }
}