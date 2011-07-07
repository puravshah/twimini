package sample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sample.model.TweetModel;
import sample.model.UserModel;
import sample.services.FollowService;
import sample.services.TweetService;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.LinkedList;
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

    @RequestMapping("/tweet")
    public ModelAndView tweetGet(HttpSession session) {
        String uid = (String)session.getAttribute("uid");
        if(uid == null) {
            return new ModelAndView("/index") {{
                addObject("loginMsg", "You need to login first!");
            }};
        }

        List <TweetModel> tweetList = null;
        List <UserModel> followingList = null, followersList = null;
        try {
            tweetList = TweetService.getTweetList(uid);
            followingList = FollowService.getFollowing(uid);
            followersList = FollowService.getFollower(uid);
            if(tweetList == null) throw new Exception("Invalid Tweet List");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("firstname", (String)session.getAttribute("firstname"));
        mv.addObject("tweetList", tweetList);
        mv.addObject("followingList", followingList);
        mv.addObject("followersList", followersList);
        mv.addObject("uid", uid);
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
            t = TweetService.addTweet(uid, tweet);
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
            ret = TweetService.getTweetList(uid);
            if(ret == null) throw new Exception("Could not render tweets");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }
}
