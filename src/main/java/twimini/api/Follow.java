package twimini.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import twimini.model.UserModel;
import twimini.services.APIKEYService;
import twimini.services.FollowService;
import twimini.services.TweetService;
import twimini.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh.k
 * Date: 6/30/11
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class Follow {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;

    @Autowired
    public Follow(UserService userService, FollowService followService, TweetService tweetService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
    }

    @RequestMapping("/api/{APIKEY}/user/{id}/unfollow")
    @ResponseBody
    Hashtable<String, String> unFollow(@PathVariable String APIKEY, @PathVariable String id, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();

        try {
            String uid = APIKEYService.getUid(APIKEY);
            int rows = followService.removeFollowing(uid, id);
            if (rows == 0) {
                ret.put("status", "0");
                ret.put("errorMsg", "You are not following this user");
            } else ret.put("status", "1");
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

    @RequestMapping("/api/{APIKEY}/user/{id}/follow")
    @ResponseBody
    Hashtable<String, String> follow(@PathVariable String APIKEY, @PathVariable String id, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        UserModel u = null;

        try {
            String uid = APIKEYService.getUid(APIKEY);
            u = followService.addFollowing(uid, id);
            ret.put("status", "1");
        } catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMsg", "Invalid APIKEY");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
            return ret;
        }
        return ret;
    }

    @RequestMapping("/api/{APIKEY}/user/{uid}/getFollower")
    @ResponseBody
    public Hashtable <String, Object> followGet(@PathVariable String APIKEY, @PathVariable String uid, HttpSession session) {
        Hashtable <String, Object> hashtable = new Hashtable<String, Object>();
        try {
            String user = APIKEYService.getUid(APIKEY);
            List<UserModel> list = followService.getFollower(uid);
            hashtable.put("status", "1");
            hashtable.put("data", list);
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMsg", "Invalid APIKEY");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMsg", e.toString());
        }
        return hashtable;
    }

    @RequestMapping("api/{APIKEY}/user/{uid}/getFollowing")
    @ResponseBody
    public Hashtable <String, Object> followerGet(@PathVariable String APIKEY, @PathVariable String uid, HttpSession session) {

        Hashtable <String, Object> hashtable = new Hashtable <String, Object>();
        try {
            String user = APIKEYService.getUid(APIKEY);
            List<UserModel> list = followService.getFollowing(uid);
            hashtable.put("status", "1");
            hashtable.put("data", list);
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMsg", "Invalid APIKEY");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "1");
            hashtable.put("data", e.toString());
        }
        return hashtable;
    }
}
