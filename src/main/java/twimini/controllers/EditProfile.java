package twimini.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import twimini.model.UserModel;
import twimini.services.FollowService;
import twimini.services.TweetService;
import twimini.services.UserService;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 8/5/11
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class EditProfile {
    private final UserService userService;
    private final TweetService tweetService;
    private final FollowService followService;

    @Autowired
    public EditProfile(UserService userService, FollowService followService, TweetService tweetService) {
        this.userService = userService;
        this.followService = followService;
        this.tweetService = tweetService;
    }

    @RequestMapping("/user/edit")
    ModelAndView getEdit(HttpSession session) throws Exception {
        final UserModel user = userService.getUser(session.getAttribute("uid").toString());
        return new ModelAndView("/edit") {{
            addObject("active", 0);
            addObject("email", user.getEmail());
        }};
    }

    @RequestMapping(value = "/user/edit/personalDetails", method = RequestMethod.POST)
    ModelAndView editPersonalDetails(@RequestParam String name, @RequestParam String email, HttpSession session) throws Exception{

        if (name == null || email == null || name.equals("") || email.equals("")) {
            final UserModel user = userService.getUser(session.getAttribute("uid").toString());
            return new ModelAndView("/edit") {{
                addObject("personalMessage", "Please fill out both the fields");
                addObject("active", "0");
                addObject("email", user.getEmail());
            }};
        }
        try {
            UserService.setAccountInfo(name, email, session.getAttribute("uid").toString());
            final UserModel user = userService.getUser(session.getAttribute("uid").toString());
            return new ModelAndView("/edit") {{
                addObject("personalMessage", "account information successfully updated");
                addObject("active", "0");
                addObject("email", user.getEmail());
            }};
        } catch (DuplicateKeyException e) {
            final UserModel user = userService.getUser(session.getAttribute("uid").toString());
            return new ModelAndView("/edit") {{
                addObject("personalMessage", "This email id already exists");
                addObject("active", "0");
                addObject("email", user.getEmail());
            }};
        } catch (final Exception e) {
            final UserModel user = userService.getUser(session.getAttribute("uid").toString());
            return new ModelAndView("/edit") {{
                addObject("personalMessage", e.toString());
                addObject("active", "0");
                addObject("email", user.getEmail());
            }};
        }
    }

    @RequestMapping(value = "/user/edit/password", method = RequestMethod.POST)
    ModelAndView editPassword(@RequestParam("old_password") String oldPassword, @RequestParam("new_password") String newPassword, @RequestParam("confirm_password") String confirmPassword, HttpSession session) {
        try {
            String uid = (String) session.getAttribute("uid");
            if (oldPassword == null || newPassword == null || confirmPassword == null || oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
                return new ModelAndView("/edit") {{
                    addObject("passwordMessage", "Please fill in all the fields");
                    addObject("active", 1);
                }};
            }
            if (!newPassword.equals(confirmPassword)) {
                return new ModelAndView("/edit") {{
                    addObject("passwordMessage", "The passwords dont match");
                    addObject("active", 1);
                }};
            }
            int rowsUpdated = UserService.changePassword(uid, oldPassword, newPassword);
            if (rowsUpdated == 0) {
                return new ModelAndView("/edit") {{
                    addObject("passwordMessage", "Your old password is incorrect");
                    addObject("active", 1);
                }};
            } else {
                return new ModelAndView("/edit") {{
                    addObject("passwordMessage", "Password changed successfully");
                    addObject("active", 1);
                }};
            }
        } catch (final Exception e) {
            return new ModelAndView("/edit") {{
                addObject("passwordMessage", e.toString());
                addObject("active", 1);
            }};
        }
    }
}
