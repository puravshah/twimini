package sample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sample.model.FollowModel;
import sample.model.TweetModel;
import sample.model.UserModel;
import sample.services.FollowService;
import sample.services.TweetService;
import sample.services.UserService;

import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPBinding;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh.k
 * Date: 6/30/11
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class FollowController {

    @RequestMapping("/user/follower.json") @ResponseBody
    public List<UserModel> followGet(@RequestParam String uid, HttpSession session) {

        List <UserModel> ret = null;
        try {
            ret = FollowService.getFollower(uid);
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
            ret = FollowService.getFollowing(uid);
            if(ret == null) throw new Exception("Could not render following");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
       return ret;
    }
}
