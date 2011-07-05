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

    @RequestMapping("/user/follower")
    @ResponseBody
    public List<UserModel> FollowGet(HttpSession session) {
        String uid = (String)session.getAttribute("uid");
        Hashtable<String, String> ret = new Hashtable<String, String>();

        List<FollowModel> followList = null;

       List<UserModel> Follower=null;

        try {
          //  t = TweetService.addTweet(uid, tweet);
           Follower= FollowService.getFollower(uid);
            //for(FollowModel follower :followList)
             //Follower.add(UserService.getUser(follower.getUid()));
         //   if(t == null) throw new Exception("Inval");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
       return Follower;
     }



    @RequestMapping(value = "/user/following")
    @ResponseBody
    public List<UserModel> FollowerGet(HttpSession session) {
            String uid = (String)session.getAttribute("uid");
              List<FollowModel> followList = null;
        List uidName=null;
       List<UserModel> Following=null;

        try {
          //  t = TweetService.addTweet(uid, tweet);
           Following= FollowService.getFollowing(uid);
            //for(FollowModel following :followList)
             //Following.add(UserService.getUser(following.getUid()));
         //   if(t == null) throw new Exception("Inval");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
       return Following;


    }


}
