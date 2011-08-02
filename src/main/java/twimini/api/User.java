package twimini.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import twimini.model.TweetModel;
import twimini.model.TweetWrapper;
import twimini.model.UserModel;
import twimini.services.APIKEYService;
import twimini.services.FollowService;
import twimini.services.TweetService;
import twimini.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.List;

@Controller
public class User {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;

    @Autowired


    public User(UserService userService, FollowService followService, TweetService tweetService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
    }

    /* REST API for getting the APIKEY */
    @RequestMapping("/api/getAPIKEY")
    @ResponseBody
    Hashtable<String, String> getAPIKEY(@RequestParam String email, @RequestParam String password) {
        Hashtable<String, String> ret = new Hashtable<String, String>();

        try {
            UserModel u = userService.getUser(email, password);
            ret.put("status", "1");
            ret.put("APIKEY", APIKEYService.getAPIKEY(u.getUid()));
        } catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMsg", "Invalid email id or password");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
        }
        return ret;
    }

    /* REST API for getting tweet list of a user */
    @RequestMapping("/api/{APIKEY}/user/{uid}/getTweetList")
    @ResponseBody
    List<TweetModel> getTweetList(@PathVariable String APIKEY, @PathVariable String uid, HttpSession session) {
        List<TweetModel> ret = null;

        try {
            ret = tweetService.getTweetList(uid);
            if (ret == null) throw new Exception("Could not render tweets");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    /* REST API for getting tweet feed of a user */
    @RequestMapping("/api/{APIKEY}/user/{uid}/getFeed")
    @ResponseBody
    List<TweetWrapper> getFeed(@PathVariable String APIKEY, @PathVariable String uid, HttpSession session) {
        List<TweetWrapper> ret = null;

        try {
            ret = tweetService.getFeed(uid);
            if (ret == null) throw new Exception("Could not render tweets");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }
}