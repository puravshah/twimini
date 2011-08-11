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
import twimini.InviteFriend;
import twimini.PasswordMail;
import twimini.model.UserModel;
import twimini.services.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;
    public boolean runMailSender = true;

    @Autowired
    public UserController(UserService userService, FollowService followService, TweetService tweetService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
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

        /*if (runMailSender) {
            Thread thread = new ActivationMail(userService);
            thread.start();
            runMailSender = false;
        }*/
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
            String token = UUID.randomUUID().toString();
            Thread thread = new PasswordMail(userModel, token);
            thread.start();
            UserService.addForgotToken(token, userModel.getUid());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @RequestMapping("/reset")
    ModelAndView getResetPassword(@RequestParam String token) {
        final int uid;
        try {
            uid = UserService.getUidFromForgotToken(token);
            UserService.removeForgotToken(token);
        } catch (Exception e) {
            return new ModelAndView("/forgot") {{
                addObject("msg", "Reset Token is invalid");
            }};
        }

        return new ModelAndView() {{
            addObject("uid", uid);
        }};
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    @ResponseBody
    boolean postResetPassword(@RequestParam String password, @RequestParam String cpassword, @RequestParam String uid) {
        try {
            UserService.changePassword(uid, password);
        } catch (Exception e) {
            return false;
        }
        return true;
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

    @RequestMapping("/searchData")
    @ResponseBody
    Hashtable<String, Object> search(@RequestParam String query, String start, String count, HttpSession session) {
        if (count == null || count.equals("")) count = "10";
        if (start == null || start.equals("")) start = "0";

        List<UserModel> searchDetails = null;
        Hashtable<String, Object> ret = new Hashtable<String, Object>();
        String uid = (String) session.getAttribute("uid");
        try {
            searchDetails = userService.getSearch(query, uid, start, count);
            if (ret == null) throw new Exception("Null returned in search.jsp");
        } catch (Exception e) {
            ret.put("status", 0);
            ret.put("error", "No user with this name");
            return ret;
        }
        ret.put("status", 1);
        ret.put("searchDetails", searchDetails);
        return ret;
    }

    @RequestMapping("/searchMore")
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
    }

    @RequestMapping("/search")
    ModelAndView searchInfo(@RequestParam final String query, @RequestParam String start, @RequestParam String count, HttpSession session) {
        final String uid = (String) session.getAttribute("uid");
        return new ModelAndView() {{
            addObject("query", query);
        }};
    }

    @RequestMapping("/invite")
    @ResponseBody
    Hashtable<String, String> inviteFriends(@RequestParam String email, HttpSession httpSession) {
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
        }
        ret.put("status", "1");
        return ret;
    }
}