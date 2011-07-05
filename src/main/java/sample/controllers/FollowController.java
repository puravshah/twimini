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
    public Hashtable<String,String> FollowGet(HttpSession session) {
        String uid = (String)session.getAttribute("uid");
        Hashtable<String, String> ret = new Hashtable<String, String>();

        FollowModel t = null;
        UserModel Follower=null;

        try {
          //  t = TweetService.addTweet(uid, tweet);
            t= FollowService.getFollower(uid);
             Follower=UserService.getUser(t.getUid());
         //   if(t == null) throw new Exception("Inval");
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }


        ret.put("uid", "" + t.getUid());
        ret.put("firstname", Follower.getFirstName());
        ret.put("lastname", Follower.getLastName());
        return ret;

        }
    /*
    @RequestMapping(value = "/user/follow")
    @ResponseBody
    public List<Map<String,Object>> FollowerGet(HttpSession session) {
            String uid = (String)session.getAttribute("uid");


            List <Map<String, Object>> follower = db.queryForList("SELECT * FROM follow inner join user on user.uid=follow.uid  WHERE follow.following = ? and  end is not null", uid);

            return follower;
        }
      */

}
