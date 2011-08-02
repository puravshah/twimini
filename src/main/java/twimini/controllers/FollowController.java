package twimini.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    Hashtable<String, String> unFollow(@RequestParam String id, HttpSession session) {
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
    }

    @RequestMapping("/user/follow")
    @ResponseBody
    Hashtable<String, String> follow(@RequestParam String id, HttpSession session) {
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
    }

    @RequestMapping("/user/getFollower")
    @ResponseBody
    public List<UserModel> followGet(@RequestParam String uid, HttpSession session) {
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
    }

    @RequestMapping(value = "/user/getFollowing")
    @ResponseBody
    public List<UserModel> followerGet(@RequestParam String uid, HttpSession session) {
        String user = (String) session.getAttribute("uid");
        List<UserModel> ret = null;

        try {
            ret = followService.getFollowing2(uid, user);
            if (ret == null) throw new Exception("Could not render following list");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }
}
