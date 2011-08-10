package twimini.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/api/user/{id}/unfollow")
    @ResponseBody
    Hashtable<String, String> unFollow(@PathVariable String id, @RequestParam String apikey, HttpSession session) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();

        try {
            String uid = APIKEYService.getUid(apikey);
            int rows = followService.removeFollowing(uid, id);
            if (rows == 0) {
                hashtable.put("status", "0");
                hashtable.put("errorMessage", "You are not following this user");
            } else hashtable.put("status", "1");
        } catch (NullPointerException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "You need to login first");
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Invalid apikey");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
        }

        return hashtable;
    }

    @RequestMapping("/api/user/{id}/follow")
    @ResponseBody
    Hashtable<String, String> follow(@PathVariable String id, @RequestParam String apikey, HttpSession session) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        try {
            String uid = APIKEYService.getUid(apikey);
            UserModel u = followService.addFollowing(uid, id);
            hashtable.put("status", "1");
        } catch (NullPointerException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "You need to login first");
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Invalid apikey");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
        }
        return hashtable;
    }

    @RequestMapping("/api/user/{uid}/getFollowers")
    @ResponseBody
    public Hashtable <String, Object> followGet(@PathVariable String uid, String apikey, String start, String count, HttpSession session) {
        Hashtable <String, Object> hashtable = new Hashtable <String, Object>();
        try {
            String user = null;
            if(apikey != null) user = APIKEYService.getUid(apikey);
            List<UserModel> list;
            if(user != null) list = followService.getFollower2(uid, user, start, count);
            else list = FollowService.getFollowerWithoutLogin(uid, start, count);
            hashtable.put("status", "1");
            hashtable.put("followers", list);
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            System.out.println("empty result data access exception");
            hashtable.put("errorMessage", "Invalid apikey");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
        }
        return hashtable;
    }

    @RequestMapping("api/user/{uid}/getFollowing")
    @ResponseBody
    public Hashtable <String, Object> followerGet(@PathVariable String uid, String apikey, String start, String count, HttpSession session) {
        Hashtable <String, Object> hashtable = new Hashtable <String, Object>();
        try {
            String user = apikey == null ? null : APIKEYService.getUid(apikey);
            List<UserModel> list;
            if(user != null) list = followService.getFollowing2(uid, user, start, count);
            else list = FollowService.getFollowingWithoutLogin(uid, start, count);
            hashtable.put("status", "1");
            hashtable.put("following", list);
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Invalid apikey");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "1");
            hashtable.put("data", e.toString());
        }
        return hashtable;
    }
}
