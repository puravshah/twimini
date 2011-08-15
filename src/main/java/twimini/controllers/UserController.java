package twimini.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import twimini.ActivationMail;
import twimini.InviteFriend;
import twimini.PasswordMail;
import twimini.model.LikeModel;
import twimini.model.UserModel;
import twimini.services.*;

import javax.annotation.PostConstruct;
import javax.mail.Session;
import javax.naming.ldap.StartTlsRequest;
import javax.servlet.http.HttpSession;
import java.sql.Struct;
import java.util.*;

@Controller
public class UserController {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;
    private final LikeService   likeService;
    public boolean runMailSender = true;

    @Autowired

    public UserController(UserService userService, FollowService followService, TweetService tweetService,LikeService likeService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
        this.likeService=likeService;
    }

    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {
        ModelAndView mv = new ModelAndView("/index");
        String uid = (String) session.getAttribute("uid");
        if (uid != null && !uid.equals("")) mv.setViewName("redirect:/tweet");
        return mv;
    }

    @RequestMapping("/login")
    public ModelAndView loginGet() {
        return new ModelAndView();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam String email, @RequestParam String password, HttpSession session) {
        if (email.equals(""))
            return new ModelAndView("/login") {{
                addObject("msg", "Email id field cannot be left blank");
            }};

        if (password.equals(""))
            return new ModelAndView("/login") {{
                addObject("msg", "Password field cannot be left blank");
            }};

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("email", email);
        attributes.put("password", password);
        final JSONObject jsonObject = JSONParser.postData("http://localhost:8080/api/user/login", attributes);
        if (jsonObject.get("status").equals("0")) {
            return new ModelAndView("/login") {{
                addObject("msg", jsonObject.get("errorMessage"));
            }};
        }

        UserModel user = null;
        try {
            String apikey = (String) jsonObject.get("apikey");
            user = userService.getUser(APIKEYService.getUid(apikey));
            session.setAttribute("uid", "" + user.getUid());
            session.setAttribute("name", user.getName());
            session.setAttribute("apikey", apikey);
            return new ModelAndView("redirect:/tweet");
        } catch (final Exception e) {
            e.printStackTrace();
            return new ModelAndView("/login") {{
                addObject("msg", e.toString());
            }};
        }
    }

    @PostConstruct
    public void runMailSender()
    {
        Thread thread = new ActivationMail(userService);
        thread.start();
    }

    @RequestMapping("/signup")
    public ModelAndView signupGet() {
        return new ModelAndView() {{
            String errorName[] = {"nameMsg", "emailMsg", "passwordMsg", "cpasswordMsg"};
            for (int i = 0; i < 4; i++)
                addObject(errorName[i], "&nbsp;");
        }};
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signupPost(@RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String cpassword,
                                   @RequestParam String name,
                                   HttpSession session) {
        boolean invalid = false;
        String errorMsg[] = new String[4], errorName[] = {"nameMsg", "emailMsg", "passwordMsg", "cpasswordMsg"};
        for (int i = 0; i < 4; i++) errorMsg[i] = "&nbsp;";
        String msg = "";

        if (name.equals("")) {
            invalid = true;
            errorMsg[0] = "*";
            msg = "Please fill out all the fields";
        }

        if (email.equals("")) {
            invalid = true;
            errorMsg[1] = "*";
            msg = "Please fill out all the fields";
        }

        if (password.equals("")) {
            invalid = true;
            errorMsg[2] = "*";
            msg = "Please fill out all the fields";
        }

        if (cpassword.equals("")) {
            invalid = true;
            errorMsg[3] = "*";
            msg = "Please fill out all the fields";
        }

        if (!cpassword.equals(password)) {
            invalid = true;
            errorMsg[2] = errorMsg[3] = "*";
            msg = "Passwords do not match";
        }

        if (!isValidEmail(email)) {
            invalid = true;
            errorMsg[1] = "*";
            msg += "\nPlease enter a valid email id";
        }

        if (invalid) {
            ModelAndView emptyError = new ModelAndView("/signup");
            emptyError.addObject("msg", msg);
            emptyError.addObject("name", name);
            emptyError.addObject("email", email);
            for (int i = 0; i < 4; i++) emptyError.addObject(errorName[i], errorMsg[i]);
            return emptyError;
        }

        /*if(password.length() < 6)
            return new ModelAndView("/signup") {{
                addObject("msg", "The passwords is too short");
            }};*/

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("email", email);
        attributes.put("password", password);
        attributes.put("cpassword", cpassword);
        attributes.put("name", name);
        final JSONObject jsonObject = JSONParser.postData("http://localhost:8080/api/user/signup", attributes);

        if (jsonObject.get("status").equals("0")) {
            return new ModelAndView("/signup") {{
                addObject("msg", jsonObject.get("errorMessage"));
            }};
        }

        UserModel user = null;
        try {
            String uid = APIKEYService.getUid(jsonObject.get("apikey").toString());
            user = userService.getUser(uid);
        } catch (final Exception e) {
            return new ModelAndView() {{
                addObject("msg", e.toString());
            }};
        }
        session.setAttribute("uid", "" + user.getUid());
        session.setAttribute("name", user.getName());
        session.setAttribute("apikey", jsonObject.get("apikey"));
        return new ModelAndView("redirect:/tweet");
    }

    public boolean isValidEmail(String email) {
        return true;
    }

    @RequestMapping("/logout")
    ModelAndView logoutMethod(HttpSession session) {
        session.invalidate();
        return new ModelAndView() {{
            setViewName("redirect:/");
        }};
    }

    @RequestMapping("/use")
    ModelAndView loMethod(HttpSession session) {
        //session.invalidate();
        return new ModelAndView() {{
            setViewName("/temp");
        }};
    }


    @RequestMapping("/activate")
    ModelAndView activateAccount(@RequestParam String token) {
        try {
            userService.setIsActivated(token);
            return new ModelAndView("redirect:/");
        } catch (EmptyResultDataAccessException e) {
            return new ModelAndView("/signup") {{
                addObject("msg", "Your activation token is no longer valid. Please create a new account");
            }};
        }
    }

    @RequestMapping("/forgot")
    ModelAndView getForgotLink() {
        return new ModelAndView();
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    @ResponseBody
    boolean postForgotLink(@RequestParam String email) {
        UserModel userModel;
        try {
            userModel = UserService.getUserInfo(email);
            String token = UserService.addForgotToken(userModel.getUid());
            new PasswordMail(userModel, token).start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/reset")
    ModelAndView getResetPassword(@RequestParam final String token, HttpSession session) {
        try {
            UserService.getUidFromForgotToken(token);
        } catch (Exception e) {
            return new ModelAndView("/forgot") {{
                addObject("msg", "Reset Token is invalid");
            }};
        }

        return new ModelAndView() {{
            addObject("token", token);
        }};
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    @ResponseBody
    Hashtable <String, String> postResetPassword(@RequestParam String password, @RequestParam String cpassword, @RequestParam String token) {
        Hashtable <String, String> hashtable = new Hashtable<String, String>();
        try {
            String uid = UserService.getUidFromForgotToken(token);
            UserService.changePassword(uid, password);
            UserService.removeForgotToken(token);
            hashtable.put("status", "1");
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "The token is either invalid or has expired");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
        }

        return hashtable;
    }

    @RequestMapping("/user")
    ModelAndView getUserProfile(@RequestParam String uid, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String user = (String) session.getAttribute("uid");
        UserModel u = null;

        try {
            if (user == null) u = userService.getUser(uid);
            else u = userService.getUser2(user, uid);
            if (u == null) throw new Exception("Invalid User");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<UserModel> followingList = null, followerList = null;
        int tweetCount = 0, followingCount = 0, followerCount = 0;

        try {
            followingList = followService.getFollowing2(uid, user, "0", "10");
            followerList = followService.getFollower2(uid, user, "0", "10");
            tweetCount = tweetService.getTweetCount(uid);
            followingCount = followService.getFollowingCount(uid);
            followerCount = followService.getFollowerCount(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mv.addObject("uid", uid);
            mv.addObject("name", u.getName());
            mv.addObject("email", u.getEmail());
            mv.addObject("status", u.getStatus());
            mv.addObject("followingList", followingList);
            mv.addObject("followerList", followerList);
            mv.addObject("tweetCount", tweetCount);
            mv.addObject("followingCount", followingCount);
            mv.addObject("followerCount", followerCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /*@RequestMapping("/searchMore")
    @ResponseBody
    Hashtable<String, Object> searchMore(@RequestParam String query, @RequestParam String start, @RequestParam String count, HttpSession session) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        String uid = (String) session.getAttribute("uid");
        try {
            List<UserModel> searchDetails = userService.getSearch(query, uid, start, count);
            hashtable.put("status", "1");
            hashtable.put("searchResults", searchDetails);
        } catch (Exception e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
            e.printStackTrace();
        }
        return hashtable;
    }*/

    @RequestMapping("/searchMore")
    @ResponseBody
    JSONObject searchMore(@RequestParam String query, @RequestParam String start, @RequestParam String count, HttpSession session) {
        return JSONParser.searchFromJSON(query, (String)session.getAttribute("apikey"), start, count);
    }

    @RequestMapping("/search")
    ModelAndView searchInfo(@RequestParam final String query, String start, String count, HttpSession session) {
        final String Start = start == null ? "0" : start;
        final String Count = count == null ? "10" : count;

        return new ModelAndView() {{
            addObject("query", query);
            addObject("start", Start);
            addObject("count", Count);
        }};
    }

    @RequestMapping("/invite")
    @ResponseBody
    Hashtable<String, String> inviteFriends(@RequestParam String email, HttpSession httpSession) {
        if(httpSession.getAttribute("uid") == null) {
            return new Hashtable<String, String>() {{
                put("status", "0");
                put("errorMessage", "You need to login first");
            }};
        }

        Hashtable<String, String> ret = new Hashtable<String, String>();
        String[] emailAddresses = email.split(";");
        for (int index = 0; index < emailAddresses.length; index++) {
            emailAddresses[index] = emailAddresses[index].trim();
        }
        try {
            Thread thread = new InviteFriend(emailAddresses, (String) httpSession.getAttribute("name"));
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMessage", e.toString());
        }
        ret.put("status", "1");
        return ret;
    }


    @RequestMapping(value="/like",method=RequestMethod.POST)
    @ResponseBody Hashtable<String,String> like(@RequestParam String tweetId,HttpSession httpSession)
    {
        Hashtable<String,String>  hashTable= new Hashtable<String, String>();
        if(httpSession.getAttribute("uid") == null) {
            return new Hashtable<String, String>() {{
                put("status", "0");
                put("errorMessage", "You need to login first");
            }};
        }
        else
        {
            try{
                likeService.insertLike(tweetId,((String)httpSession.getAttribute("uid")));
                hashTable.put("status","1");

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return  hashTable;
    }


    @RequestMapping(value="/unlike",method=RequestMethod.POST)
    @ResponseBody Hashtable<String,String> unlike(@RequestParam String tweetId,HttpSession httpSession)
    {
        Hashtable<String,String>  hashTable= new Hashtable<String, String>();
        if(httpSession.getAttribute("uid") == null) {
            return new Hashtable<String, String>() {{
                put("status", "0");
                put("errorMessage", "You need to login first");
            }};
        }
        else
        {
            try{
                likeService.deleteLike(tweetId, ((String) httpSession.getAttribute("uid")));
                hashTable.put("status","1");

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return  hashTable;
    }

    @RequestMapping(value = "/user/getLikes",method=RequestMethod.POST)
    @ResponseBody Hashtable<String,Object> getLikes(@RequestParam String uid, String start,String count, HttpSession httpSession)
    {
        if (count == null || count.equals("")) count = "10";
        if (start == null || start.equals("")) start = "0";
        Hashtable<String,Object> hashtable = new Hashtable<String, Object>();
        try{
        if(httpSession.getAttribute("uid")==null)
        {

            hashtable.put("favourites",likeService.getLikes(uid,uid,start,count));
            hashtable.put("status","1");

            /*return new Hashtable<String,Object>(){{
                put("status","0");
                put("errorMessage","You Need to login first");
            }};*/
        }
        else{

                hashtable.put("favourites",likeService.getLikes(uid,(String)httpSession.getAttribute("uid"),start,count));
                hashtable.put("status","1");

            }
        }catch(Exception e)
            {
                e.printStackTrace();
                hashtable.put("status", "0");
                hashtable.put("errorMessage", e.getMessage());

            }

     return hashtable;
    }

}