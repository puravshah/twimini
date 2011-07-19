package sample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sample.model.TweetModel;
import sample.model.TweetWrapper;
import sample.model.UserModel;
import sample.services.FollowService;
import sample.services.TweetService;
import sample.services.UserService;

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
    private  final UserService userService;
    private  final TweetService tweetService;
    private  final FollowService  followService;

    @Autowired
    public TweetController(UserService userService,FollowService followService,TweetService tweetService)
    {
       this.userService=userService;
        this.followService=followService;
        this.tweetService=tweetService;
    }

    @RequestMapping("/tweet")
    public ModelAndView tweetGet(HttpSession session) {
        String uid = (String)session.getAttribute("uid");
        if(uid == null) {
            return new ModelAndView("/index") {{
                addObject("loginMsg", "You need to login first!");
            }};
        }

        List <TweetWrapper> tweetList = null;
        List <UserModel> followingList = null, followerList = null;
        int tweetCount = 0, followingCount = 0, followerCount = 0;

        try {
            tweetList = tweetService.getFeed(uid);
            followingList = followService.getFollowing(uid);
            followerList = followService.getFollower(uid);
            tweetCount = tweetService.getTweetCount(uid);
            followingCount = followService.getFollowingCount(uid);
            followerCount = followService.getFollowerCount(uid);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("uid", uid);
        mv.addObject("name", session.getAttribute("name"));
        mv.addObject("tweetList", tweetList);
        mv.addObject("followingList", followingList);
        mv.addObject("followerList", followerList);
        mv.addObject("tweetCount", tweetCount);
        mv.addObject("followingCount",followingCount);
        mv.addObject("followerCount", followerCount);
        return mv;
    }

    /* REST API for creating a tweet */
    @RequestMapping("/tweet/create") @ResponseBody
    Hashtable <String, String>  createTweet(@RequestParam final String tweet, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        String uid = (String)session.getAttribute("uid");
        TweetModel t = null;

        try {
            t = tweetService.addTweet(uid, tweet);
        }
        catch(Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
            return ret;
        }

        ret.put("pid", "" + t.getPid());
        ret.put("uid", "" + t.getUid());
        ret.put("tweet", t.getTweet());
        ret.put("timestamp", t.getTimestamp());
        ret.put("status", "1");
        return ret;
    }

    @RequestMapping("/tweet/getTweetDetails") @ResponseBody
    Hashtable <String, String>  getTweetDetails(@RequestParam final String pid, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        String uid = (String)session.getAttribute("uid");

        TweetModel t = null;
        try {
            t = tweetService.getTweetDetails(pid);
            if(t == null) throw new Exception("Invalid tweet");
        }
        catch(Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMsg", e.toString());
            return null;
        }

        ret.put("pid", "" + t.getPid());
        ret.put("uid", "" + t.getUid());
        ret.put("tweet", t.getTweet());
        ret.put("timestamp", t.getTimestamp());
        ret.put("status", "1");
        return ret;
    }

    /* REST API for getting tweet list of a user */
    @RequestMapping("/tweet/getTweetList") @ResponseBody
    List <TweetModel> getTweetList(@RequestParam final String uid, HttpSession session) {
        List<TweetModel> ret = null;

        try {
            ret = tweetService.getTweetList(uid);
            if(ret == null) throw new Exception("Could not render tweets");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    /* REST API for getting tweet feed of a user */
    @RequestMapping("/tweet/getFeed") @ResponseBody
    List <TweetWrapper> getFeed(@RequestParam final String uid, HttpSession session) {
        List<TweetWrapper> ret = null;

        try {
            ret = tweetService.getFeed(uid);
            if(ret == null) throw new Exception("Could not render tweets");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }
}
