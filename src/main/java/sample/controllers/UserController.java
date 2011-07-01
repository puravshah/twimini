package sample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.nio.cs.ext.DBCS_IBM_EBCDIC_Decoder;

import javax.servlet.http.HttpSession;
import javax.swing.plaf.multi.MultiViewportUI;
import java.util.Map;

@Controller
public class UserController {
    public final SimpleJdbcTemplate db;

    @Autowired
    public UserController(SimpleJdbcTemplate db) {this.db = db;}

    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {
        return new ModelAndView("/index");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam final String email,
                                  @RequestParam final String password,
                                  HttpSession session) {

        if(email.equals(""))
            return new ModelAndView("/index") {{
                addObject("loginMsg", "Email id field cannot be left blank");
            }};

        if(password.equals(""))
            return new ModelAndView("/index") {{
                addObject("loginMsg", "Password field cannot be left blank");
            }};

        String uid = "";
        try {
            Map <String, Object> m = db.queryForMap("SELECT uid FROM user WHERE email = ? and password = ?", email, password);

        }
        catch(Exception e) {
            System.out.println("This is the db exception : " + e);
        }

        /*if()
            return new ModelAndView("/index") {{
                addObject("loginMsg", "Invalid Email id or Password");
            }};*/

        //session.setAttribute("uid", );
        String firstname = email;
        //String uid = .toString();
        //session.setAttribute("uid", uid);
        session.setAttribute("firstname", firstname);

        ModelAndView mv = new ModelAndView("/tweet");
        return mv;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signupPost(@RequestParam final String email,
                                   @RequestParam final String password,
                                   @RequestParam final String cpassword,
                                   @RequestParam final String firstname,
                                   @RequestParam final String lastname,
                                   HttpSession session) {

        if(firstname.equals("") || email.equals("") || password.equals("") || cpassword.equals(""))
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

        return new ModelAndView("/index") {{
            addObject("signupMsg", "Successfully Registered!");
        }};
    }

    @RequestMapping("/logout")
    ModelAndView logoutMethod(HttpSession session) {
        /*String uid = (String)session.getAttribute("uid");
        if(uid == null) {
            return new ModelAndView("/login") {{
                addObject("msg", "You need to login first!");
            }};
        }*/

        //session.removeAttribute("uid");
        session.removeAttribute("username");
        return new ModelAndView("/index");
    }
}