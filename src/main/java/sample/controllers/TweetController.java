package sample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPBinding;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 6/30/11
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class TweetController {
    private SimpleJdbcTemplate db;

    @Autowired
    public TweetController(SimpleJdbcTemplate db) {this.db = db;}

    @RequestMapping("/tweet")
    public ModelAndView tweetGet(HttpSession session) {
        String uid = (String)session.getAttribute("uid");
        if(uid == null) {
            return new ModelAndView("/index") {{
                addObject("loginMsg", "You need to login first!");
            }};
        }

        List <Map<String, Object>> tweets = db.queryForList("SELECT * FROM post WHERE uid = ?", uid);

        ModelAndView mv = new ModelAndView();
        mv.addObject("firstname", session.getAttribute("firstname").toString());
        mv.addObject("tweetList", tweets);
        return mv;
    }

    @RequestMapping(value = "/tweet", method = RequestMethod.POST)
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
    }

    @RequestMapping("/tweet/create.json") @ResponseBody
    Hashtable <String, String> createTweet(@RequestParam final String tweet, HttpSession session) {
        Hashtable<String, String> ret = new Hashtable<String, String>();
        String uid = (String)session.getAttribute("uid");

        db.update("INSERT INTO post(uid, tweet, timestamp) values(?, ?, now())", uid, tweet);
        int pid = db.queryForInt("SELECT MAX(pid) FROM post");
        ret.put("pid", "" + pid);
        ret.put("tweet", tweet);
        return ret;
    }
}
