package sample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sample.PasswordMail;
import sample.model.TweetModel;
import sample.model.UserModel;
import sample.services.FollowService;
import sample.services.TweetService;
import sample.services.UserService;

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
    public ModelAndView loginPost(@RequestParam final String email,
                                  @RequestParam final String password,
                                  HttpSession session) {

        if (email.equals(""))
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
            if (m == null) {
                throw new Exception("Invalid Email id or Password");
            }
        } catch (EmptyResultDataAccessException e) {
            return new ModelAndView("/login") {{
                addObject("msg", "Invalid Email id or Password");
            }};
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("/login") {{
                addObject("msg", "Login failed");
            }};
        }

        session.setAttribute("uid", "" + (Integer) m.getUid());
        session.setAttribute("name", m.getName());
        ModelAndView mv = new ModelAndView("/tweet");
        mv.setViewName("redirect:/tweet");
        return mv;
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
    public ModelAndView signupPost(@RequestParam final String email,
                                   @RequestParam final String password,
                                   @RequestParam final String cpassword,
                                   @RequestParam final String name,
                                   HttpSession session) {

        boolean invalid = false;
        String errorMsg[] = new String[4], errorName[] = {"nameMsg", "emailMsg", "passwordMsg", "cpasswordMsg"};
        for (int i = 0; i < 4; i++) errorMsg[i] = "&nbsp;";

        if (name.equals("")) {
            invalid = true;
            errorMsg[0] = "*";
        }

        if (email.equals("")) {
            invalid = true;
            errorMsg[1] = "*";
        }

        if (password.equals("")) {
            invalid = true;
            errorMsg[2] = "*";
        }

        if (cpassword.equals("")) {
            invalid = true;
            errorMsg[3] = "*";
        }

        if (!cpassword.equals(password)) {
            invalid = true;
            errorMsg[2] = "Passwords do not match";
        }

        if (invalid) {
            ModelAndView emptyError = new ModelAndView("/signup");
            emptyError.addObject("msg", "Please fill out all fields");
            emptyError.addObject("name", name);
            emptyError.addObject("email", email);
            for (int i = 0; i < 4; i++) emptyError.addObject(errorName[i], errorMsg[i]);
            return emptyError;
        }

        /*if(password.length() < 6)
            return new ModelAndView("/signup") {{
                addObject("msg", "The passwords is too short");
            }};*/

        UserModel m = null;
        try {
            m = userService.addUser(name, email, password);
            if (m == null) throw new Exception("Unable to register user");
        } catch (DuplicateKeyException e) {
            ModelAndView errorDuplicate = new ModelAndView("/signup");
            errorMsg[1] = "Please choose another Email id";
            for (int i = 0; i < 4; i++) errorDuplicate.addObject(errorName[i], errorMsg[i]);
            errorDuplicate.addObject("msg", "Email id already exists");
            return errorDuplicate;
        } catch (Exception e) {
            e.printStackTrace();
            final String E = e.toString();
            return new ModelAndView("/signup") {{
                addObject("msg", "Unable to Signup: " + E);
            }};
        }

        session.setAttribute("uid", "" + (Integer) m.getUid());
        session.setAttribute("name", m.getName());
        return new ModelAndView() {{
            setViewName("redirect:/tweet");
        }};
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

    @RequestMapping(value = "/forgot", method = RequestMethod.POST) @ResponseBody
    boolean postForgotLink(@RequestParam String email) {
        boolean status = true;
        Thread thread = new PasswordMail(email, UUID.randomUUID().toString());
        thread.start();
        return status;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    ModelAndView postResetPassword(@RequestParam String password, @RequestParam String cpassword, @RequestParam String token) {
        ModelAndView mv = new ModelAndView("/"), error = new ModelAndView();
        if(password.equals("") || cpassword.equals("") || token.equals("")) {
            error.addObject("msg", "Please fill out all the fields");
            return error;
        }
        if(password.equals(cpassword)) {
            error.addObject("msg", "Passwords dont match");
            return error;
        }
        return mv;
    }

    @RequestMapping("/user")
    ModelAndView getUserProfile(@RequestParam String uid, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        String user = (String) session.getAttribute("uid");
        UserModel u = null;

        try {
            u = userService.getUser(uid);

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