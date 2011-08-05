package twimini.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import twimini.PasswordMail;
import twimini.model.TweetModel;
import twimini.model.UserModel;
import twimini.services.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller

public class UserController {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;

    @Autowired


    public UserController(UserService userService, FollowService followService, TweetService tweetService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
    }

    @RequestMapping("/")
    public ModelAndView index() {

        ModelAndView mv = new ModelAndView("/index");
        //if(uid != null && !uid.equals("")) mv.setViewName("redirect:/tweet");
        return mv;
    }

    @RequestMapping("/login")
    public ModelAndView loginGet() {
        return new ModelAndView();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam String email, @RequestParam String password, HttpSession session) {

        /*if (email.equals(""))
            return new ModelAndView("/login") {{
                addObject("msg", "Email id field cannot be left blank");
            }};

        if (password.equals(""))
            return new ModelAndView("/login") {{
                addObject("msg", "Password field cannot be left blank");
            }};

        UserModel m = null;
        try {
            m = userService.getUser(email, password);
        } catch (EmptyResultDataAccessException e) {
            return new ModelAndView("/login") {{
                addObject("msg", "Invalid Email id or Password");
            }};
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("/login") {{
                addObject("msg", "Login failed");
            }};
        }*/

        //session.setAttribute("uid", "" + (Integer) m.getUid());
        //session.setAttribute("name", m.getName());
        //session.setAttribute("apikey", APIKEYService.getAPIKEY(m.getUid()));

        password = password.replace(' ', '+');
        String url = String.format("http://localhost:8080/api/user/login?email=%s&password=%s", email, password);
        final JSONObject jsonObject = JSONParser.getData(url);

        if(jsonObject.get("status").equals("0")) {
            return new ModelAndView("/login") {{
                addObject("msg", jsonObject.get("errorMessage"));
            }};
        }

        UserModel user = null;
        try {
            String apikey = (String)jsonObject.get("apikey");
            user = userService.getUser(APIKEYService.getUid(apikey));
            session.setAttribute("uid", "" + user.getUid());
            session.setAttribute("name", user.getName());
            session.setAttribute("apikey", apikey);
            return new ModelAndView("redirect:/tweet");
        } catch(final Exception e) {
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

        if(!isValidEmail(email)) {
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

        /*UserModel user = null;
        try {
            user = userService.addUser(name, email, password);
            if (user == null) throw new Exception("Unable to register user");
        } catch (DuplicateKeyException e) {
            ModelAndView errorDuplicate = new ModelAndView("/signup");
            errorMsg[1] = "Please choose another Email id";
            for (int i = 0; i < 4; i++) errorDuplicate.addObject(errorName[i], errorMsg[i]);
            errorDuplicate.addObject("msg", "Email id already exists");
            return errorDuplicate;
        } catch (final Exception e) {
            e.printStackTrace();
            return new ModelAndView("/signup") {{
                addObject("msg", "Unable to Signup: " + e);
            }};
        }*/

        password = password.replace(" ", "+");
        cpassword = password.replace(" ", "+");
        name = name.replace(" ", "+");
        String url = String.format("http://localhost:8080/api/user/signup?email=%s&name=%s&password=%s&cpassword=%s", email, name, password, cpassword);
        final JSONObject jsonObject = JSONParser.getData(url);

        if(jsonObject.get("status").equals("0")) {
            return new ModelAndView("/signup") {{
                addObject("msg", jsonObject.get("errorMessage"));
            }};
        }

        UserModel user = null;
        try {
            user = userService.getUser(jsonObject.get("uid").toString());
        } catch (final Exception e) {
            return new ModelAndView() {{
                addObject("msg", e.toString());
            }};
        }
        session.setAttribute("uid", "" + user.getUid());
        session.setAttribute("name", user.getName());
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


    @RequestMapping("/activate")
    ModelAndView activateAccount(@RequestParam String uid) {
        userService.setIsActivated(uid);
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
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
            UserService.addToken(token, userModel.getUid());
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
        } catch(Exception e) {
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
            if(user == null) u = userService.getUser(uid);
            else u = userService.getUser2(user, uid);
            if (u == null) throw new Exception("Invalid User");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TweetModel> tweetList = null;
        List<UserModel> followingList = null, followerList = null;
        int tweetCount = 0, followingCount = 0, followerCount = 0;

        try {
            tweetList = tweetService.getTweetList(uid);
            followingList = followService.getFollowing2(uid, user);
            followerList = followService.getFollower2(uid, user);
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
            mv.addObject("tweetList", tweetList);
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

    @RequestMapping("/search")
    @ResponseBody
    List<UserModel> search(@RequestParam String q, HttpSession session) {
        List<UserModel> ret = null;
        try {
            ret = userService.getSearch(q);
            if (ret == null) throw new Exception("Null returned in search");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}