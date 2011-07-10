package sample.controllers;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sample.model.TweetModel;
import sample.model.UserModel;
import sample.services.FollowService;
import sample.services.TweetService;
import sample.services.UserService;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.List;

@Controller
public class UserController {

    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {
        String uid = (String)session.getAttribute("uid");
        ModelAndView mv = new ModelAndView("/index");
        if(uid != null && !uid.equals("")) mv.setViewName("redirect:/tweet");
        return mv;
    }

    @RequestMapping("/login")
    public ModelAndView loginGet(HttpSession session) {
         String uid = (String)session.getAttribute("uid");
         ModelAndView mv = new ModelAndView();
         if(uid != null && !uid.equals("")) mv.setViewName("redirect:/tweet");
         return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam final String email,
                                  @RequestParam final String password,
                                  HttpSession session) {

        if(email.equals(""))
            return new ModelAndView("/login") {{
                addObject("msg", "Email id field cannot be left blank");
            }};

        if(password.equals(""))
            return new ModelAndView("/login") {{
                addObject("msg", "Password field cannot be left blank");
            }};

        UserModel m = null;
        try {
            m = UserService.getUser(email, password);
            if(m == null) {
                throw new Exception("Invalid Email id or Password");
            }
        }
        catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ModelAndView("/login") {{
                addObject("msg", "Invalid Email id or Password");
            }};
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ModelAndView("/login") {{
                addObject("msg", "Login failed");
            }};
        }

        session.setAttribute("uid", "" + m.getUid());
        session.setAttribute("name", m.getName());
        ModelAndView mv = new ModelAndView("/tweet");
        mv.setViewName("redirect:/tweet");
        return mv;
    }

    @RequestMapping("/signup")
    public ModelAndView signupGet(HttpSession session) {
        return new ModelAndView();
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signupPost(@RequestParam final String email,
                                   @RequestParam final String password,
                                   @RequestParam final String cpassword,
                                   @RequestParam final String name,
                                   HttpSession session) {

        if(name.equals("") || email.equals("") || password.equals("") || cpassword.equals(""))
            return new ModelAndView("/index") {{
                addObject("signupMsg", "Please fill out all the required fields");
            }};

        if(!cpassword.equals(password))
            return new ModelAndView("/index") {{
                addObject("signupMsg", "The passwords don't match");
            }};

        /*if(password.length() < 6)
            return new ModelAndView("/signup") {{
                addObject("signupMsg", "The passwords is too short");
            }};*/

        UserModel m = null;
        try {
            m = UserService.addUser(name, email, password);
            if(m == null) throw new Exception("Unable to register user");
        }
        catch(Exception e) {
            e.printStackTrace();
            final String E = e.toString();
            return new ModelAndView("/index") {{
                addObject("signupMsg", "Unable to Signup" + E);
            }};
        }

        session.setAttribute("uid", "" + m.getUid());
        session.setAttribute("name", m.getName());
        return new ModelAndView("/index") {{
            setViewName("redirect:/tweet");
        }};
    }

    @RequestMapping("/logout")
    ModelAndView logoutMethod(HttpSession session) {
        String uid = (String)session.getAttribute("uid");
        if(uid == null) {
            return new ModelAndView("/login") {{
                addObject("msg", "You need to login first!");
            }};
        }

        session.removeAttribute("uid");
        session.removeAttribute("username");
        return new ModelAndView(){{
            setViewName("redirect:/");
        }};
    }

    @RequestMapping("/user")
    ModelAndView getUserProfile(@RequestParam String uid, HttpSession session) {
        String UID = (String)session.getAttribute("uid");
        if(UID == null || UID.equals("")) {
            return new ModelAndView("/login") {{
                addObject("msg", "You need to login first");
            }};
        }

        ModelAndView mv = new ModelAndView();
        UserModel u = null;
        try {
            u = UserService.getUser(Integer.parseInt(uid));
            if(u == null) throw new Exception("Invalid User");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        List <TweetModel> tweetList = null;
        List <UserModel> followingList = null, followerList = null;
        int tweetCount = 0, followingCount = 0, followerCount = 0;

        try {
            tweetList = TweetService.getTweetList(uid);
            followingList = FollowService.getFollowing(uid);
            followerList = FollowService.getFollower(uid);
            tweetCount = TweetService.getTweetCount(uid);
            followingCount = FollowService.getFollowingCount(uid);
            followerCount = FollowService.getFollowerCount(uid);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        mv.addObject("uid", uid);
        mv.addObject("name", u.getName());
        mv.addObject("email", u.getEmail());
        mv.addObject("tweetList", tweetList);
        mv.addObject("followingList", followingList);
        mv.addObject("followerList", followerList);
        mv.addObject("tweetCount", tweetCount);
        mv.addObject("followingCount",followingCount);
        mv.addObject("followerCount", followerCount);

        return mv;
    }

    @RequestMapping("/user/unfollow.json") @ResponseBody
    Hashtable <String, String> unFollow(@RequestParam String unfollowId, HttpSession session) {
        Hashtable <String, String> ret = new Hashtable <String, String> ();
        try {
            FollowService.removeFollowing((String)session.getAttribute("uid"), unfollowId);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        ret.put("msg", "sucess");
        return ret;
    }
}