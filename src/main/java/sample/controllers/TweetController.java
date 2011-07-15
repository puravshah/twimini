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
        List <UserModel> followingList = null, followersList = null;
        int tweetCount = 0, followingCount = 0, followerCount = 0;

        try {
            tweetList  = tweetService.getFeed();
            followingList = followService.getFollowing();
            followersList = followService.getFollower();
            tweetCount = tweetService.getTweetCount();
            followingCount = followService.getFollowingCount();
            followerCount = followService.getFollowerCount();
            //if(tweetList == null) throw new Exception("Invalid Tweet List");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("name", session.getAttribute("name"));
        mv.addObject("tweetList", tweetList);
        mv.addObject("followingList", followingList);
        mv.addObject("followersList", followersList);
        mv.addObject("uid", uid);
        mv.addObject("tweetCount", tweetCount);
        mv.addObject("followingCount",followingCount);
        mv.addObject("followerCount", followerCount);
        return mv;
    }

    /*@RequestMapping(value = "/tweet", method = RequestMethod.POST)
    public ModelAndView tweetPost(@RequestParam String tweet, HttpSession session) {
        String uid = (String)session.getAttribute("uid");
        if(uid == null) {
            return new ModelAndView("/index") {{
                addObject("loginMsg", "You need to login first!");
            }};
        }

        db.update("INSERT INTO post(uid, tweet, timestamp) values(?, ?, now())", uid, tweet);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/tweet");
        return mv;
    }*/

    @RequestMapping("/tweet/create.json") @ResponseBody
    Hashtable <String, String> createTweet(@RequestParam final String tweet, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        String uid = (String)session.getAttribute("uid");

        TweetModel t = null;
        try {
            t = tweetService.addTweet(tweet);
            if(t == null) throw new Exception("Invalid tweet");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        ret.put("pid", "" + t.getPid());
        ret.put("uid", "" + t.getUid());
        ret.put("tweet", t.getTweet());
        ret.put("timestamp", t.getTimestamp());
        return ret;
    }

    @RequestMapping("/tweet/getTweetList.json") @ResponseBody
    List <TweetModel> getTweetList(@RequestParam final String uid, HttpSession session) {
        List<TweetModel> ret = null;

        try {
            ret = tweetService.getTweetList();
            if(ret == null) throw new Exception("Could not render tweets");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }
}
