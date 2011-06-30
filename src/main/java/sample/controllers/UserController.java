package sample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {
        System.out.println("Here");
        System.out.println("Here");
        return new ModelAndView("/index");
    }
}