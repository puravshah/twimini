package sample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 6/30/11
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class TweetController {
    @RequestMapping("/tweet")
    public ModelAndView tweetGet(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("firstname", session.getAttribute("firstname").toString());
        return mv;
    }
}
