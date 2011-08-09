package twimini.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import twimini.model.TweetModel;
import twimini.model.TweetWrapper;
import twimini.model.UserModel;
import twimini.services.APIKEYService;
import twimini.services.FollowService;
import twimini.services.TweetService;
import twimini.services.UserService;

import java.util.Hashtable;
import java.util.List;

@Controller
public class User {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;

    @Autowired
    public User(UserService userService, FollowService followService, TweetService tweetService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
    }

    @RequestMapping(value = "/api/user/signup", method = RequestMethod.POST)
    @ResponseBody
    Hashtable<String, String> apiSignup(@RequestParam String email, @RequestParam String password, @RequestParam String cpassword, @RequestParam String name) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        if (email == null || password == null || cpassword == null || name == null || email.equals("") || password.equals("") || cpassword.equals("") || name.equals("")) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Some fields were empty");
        } else if (!cpassword.equals(password)) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Passwords dont match");
        } else {
            try {
                UserModel user = userService.addUser(name, email, password);
                hashtable.put("status", "1");
                hashtable.put("apikey", "" + APIKEYService.getAPIKEY(user.getUid()));
            } catch (DuplicateKeyException e) {
                hashtable.put("status", "1");
                hashtable.put("errorMessage", "" + "Email id already exists");
            } catch (Exception e) {
                e.printStackTrace();
                hashtable.put("status", "0");
                hashtable.put("errorMessage", e.toString());
            }
        }
        return hashtable;
    }

    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
    @ResponseBody
    Hashtable<String, String> apiLogin(@RequestParam String email, @RequestParam String password) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        if (email == null || email.equals("")) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Email id cannot be empty");
        } else if (password == null || password.equals("")) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Password cannot be empty");
        } else {
            try {
                UserModel user = userService.getUser(email, password);
                hashtable.put("status", "1");
                hashtable.put("apikey", "" + APIKEYService.getAPIKEY(user.getUid()));
            } catch (Exception e) {
                e.printStackTrace();
                hashtable.put("status", "0");
                hashtable.put("errorMessage", "Invalid email id or password");
            }
        }

        return hashtable;
    }

    /* REST API for getting the apikey */
    @RequestMapping("/api/getAPIKEY")
    @ResponseBody
    Hashtable<String, String> getAPIKEY(@RequestParam String email, @RequestParam String password) {
        Hashtable<String, String> ret = new Hashtable<String, String>();

        try {
            UserModel u = userService.getUser(email, password);
            ret.put("status", "1");
            ret.put("apikey", APIKEYService.getAPIKEY(u.getUid()));
        } catch (EmptyResultDataAccessException e) {
            ret.put("status", "0");
            ret.put("errorMessage", "Invalid email id or password");
        } catch (Exception e) {
            e.printStackTrace();
            ret.put("status", "0");
            ret.put("errorMessage", e.toString());
        }
        return ret;
    }

    /* REST API for getting tweet list of a user */
    @RequestMapping("/api/user/{uid}/getTweetList")
    @ResponseBody
    Hashtable<String, Object> getTweetList(@PathVariable String uid, String apikey, String start, String count) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();

        try {
            APIKEYService.getUid(apikey);
        } catch (Exception e) {
            return new Hashtable<String, Object>() {{
                put("status", "0");
                put("errorMessage", "Invalid apikey");
            }};
        }

        if (count == null || count.equals("")) count = "10";
        if (start == null || start.equals("")) start = "0";
        try {
            Integer.parseInt(count);
        } catch (Exception e) {
            return new Hashtable<String, Object>() {{
                put("status", "0");
                put("errorMessage", "count attribute should be a valid number");
            }};
        }

        try {
            Integer.parseInt(start);
        } catch (Exception e) {
            return new Hashtable<String, Object>() {{
                put("status", "0");
                put("errorMessage", "start attribute should be a valid number");
            }};
        }

        try {
            List<TweetModel> list = tweetService.getTweetList(uid, start, count);
            hashtable.put("status", "1");
            hashtable.put("tweets", list);
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
        }
        return hashtable;
    }

    /* REST API for getting tweet feed of a user */
    @RequestMapping("/api/user/{uid}/getFeed")
    @ResponseBody
    Hashtable<String, Object> getFeed(@PathVariable String uid, @RequestParam String apikey, String start, String count) {
        Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        if (count == null || count.equals("")) count = "10";
        if (start == null || start.equals("")) start = "0";
        try {
            Integer.parseInt(count);
        } catch (Exception e) {
            return new Hashtable<String, Object>() {{
                put("status", "0");
                put("errorMessage", "count attribute should be a valid number");
            }};
        }

        try {
            Integer.parseInt(start);
        } catch (Exception e) {
            return new Hashtable<String, Object>() {{
                put("status", "0");
                put("errorMessage", "start attribute should be a valid number");
            }};
        }


        try {
            String user = APIKEYService.getUid(apikey);
            List<TweetWrapper> list = tweetService.getFeed(uid, start, count);
            hashtable.put("status", "1");
            hashtable.put("feed", list);
        } catch (NullPointerException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "You need to login first");
        } catch (EmptyResultDataAccessException e) {
            hashtable.put("status", "0");
            hashtable.put("errorMessage", "Invalid apikey");
        } catch (Exception e) {
            e.printStackTrace();
            hashtable.put("status", "0");
            hashtable.put("errorMessage", e.toString());
        }

        return hashtable;
    }
}