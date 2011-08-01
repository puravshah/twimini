package sample.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sample.model.TweetModel;
import sample.services.APIKEYService;
import sample.services.FollowService;
import sample.services.TweetService;
import sample.services.UserService;

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
    @RequestMapping("/api/{APIKEY}/tweet/create")
    @ResponseBody
    Hashtable<String, String> createTweet(@PathVariable String APIKEY, @RequestParam final String tweet, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        TweetModel t = null;

        try {
            String uid = APIKEYService.getUid(APIKEY);
            t = tweetService.addTweet(uid, tweet);
            ret.put("pid", "" + t.getPid());
            ret.put("status", "1");
        } catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMsg", "Invalid APIKEY");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
        }
        return ret;
    }

    @RequestMapping("/api/{APIKEY}/tweet/getTweetDetails/{pid}")
    @ResponseBody
    Hashtable<String, String> getTweetDetails(@PathVariable String APIKEY, @PathVariable String pid, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();

        try {
            String uid = APIKEYService.getUid(APIKEY);
        }
        catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMsg", "Invalid APIKEY");
        }
        catch (Exception e) {
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
        }

        try {
            TweetModel t = tweetService.getTweetDetails(pid);
            ret.put("pid", "" + t.getPid());
            ret.put("uid", "" + t.getUid());
            ret.put("tweet", t.getTweet());
            ret.put("timestamp", t.getTimestamp());
            ret.put("status", "1");
        } catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMsg", "Invalid Tweet ID");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
        }
        return ret;
    }
}