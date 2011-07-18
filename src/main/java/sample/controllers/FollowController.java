package sample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sample.model.UserModel;
import sample.services.FollowService;
import sample.services.TweetService;
import sample.services.UserService;

import javax.servlet.http.HttpSession;
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
    private  final UserService userService;
    private  final TweetService tweetService;
    private  final FollowService followService;

    @Autowired
    public FollowController(UserService userService,FollowService followService,TweetService tweetService)
    {
        this.userService=userService;
        this.followService=followService;
        this.tweetService=tweetService;
    }

    @RequestMapping("/user/follower.json") @ResponseBody
    public List<UserModel> followGet(@RequestParam String uid, HttpSession session) {

        List <UserModel> ret = null;
        try {
            ret = followService.getFollower();
            if(ret == null) throw new Exception("Could not render followers");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

    @RequestMapping(value = "/user/following.json") @ResponseBody
    public List<UserModel> followerGet(@RequestParam String uid, HttpSession session) {
        List<UserModel> ret = null;

        try {
            ret = followService.getFollowing();
            if(ret == null) throw new Exception("Could not render following");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
       return ret;
    }
}
