package twimini.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import twimini.model.UserModel;
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
    Hashtable<String, String> unFollow(@PathVariable String id, HttpSession session) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();

        try {
            String uid = session.getAttribute("uid").toString();//APIKEYService.getUid(APIKEY);
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
            hashtable.put("errorMessage", "Invalid APIKEY");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
        }

        return hashtable;
    }

    @RequestMapping("/api/user/{id}/follow")
    @ResponseBody
    Hashtable<String, String> follow(@PathVariable String id, HttpSession session) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        UserModel u = null;

        try {
            String uid = session.getAttribute("uid").toString();//APIKEYService.getUid(APIKEY);
            u = followService.addFollowing(uid, id);
            hashtable.put("status", "1");
        } catch (NullPointerException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "You need to login first");
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Invalid APIKEY");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
        }
        return hashtable;
    }

    @RequestMapping("/api/user/{uid}/getFollower")
    @ResponseBody
    public Hashtable <String, Object> followGet(@PathVariable String uid, HttpSession session) {
        Hashtable <String, Object> hashtable = new Hashtable <String, Object>();
        try {
            //String user = APIKEYService.getUid(APIKEY);
            List<UserModel> list;
            if(session.getAttribute("uid") != null) list = followService.getFollower2(uid, (String)session.getAttribute("uid"));
            else list = FollowService.getFollowerWithoutLogin(uid); 
            hashtable.put("status", "1");
            hashtable.put("followers", list);
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Invalid APIKEY");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
        }
        return hashtable;
    }

    @RequestMapping("api/user/{uid}/getFollowing")
    @ResponseBody
    public Hashtable <String, Object> followerGet(@PathVariable String uid, HttpSession session) {
        Hashtable <String, Object> hashtable = new Hashtable <String, Object>();
        try {
            //String user = APIKEYService.getUid(APIKEY);
            List<UserModel> list;
            if(session.getAttribute("uid") != null) list = followService.getFollowing2(uid, (String)session.getAttribute("uid"));
            else list = FollowService.getFollowingWithoutLogin(uid);
            hashtable.put("status", "1");
            hashtable.put("following", list);
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Invalid APIKEY");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "1");
            hashtable.put("data", e.toString());
        }
        return hashtable;
    }
}
