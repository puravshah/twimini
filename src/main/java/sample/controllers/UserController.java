package sample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sample.model.UserModel;
import sample.services.UserService;
import sun.nio.cs.ext.DBCS_IBM_EBCDIC_Decoder;

import javax.servlet.http.HttpSession;
import javax.swing.plaf.multi.MultiViewportUI;
import javax.xml.ws.Service;
import java.util.Map;

@Controller
public class UserController {

    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {
        return new ModelAndView("/index");
    }

     @RequestMapping("/login")
    public ModelAndView loginGet(HttpSession session) {
         String uid = (String)session.getAttribute("uid");
         if(uid == null || uid.equals("")) return new ModelAndView();
         return new ModelAndView() {{
             setViewName("redirect:/tweet");
         }};
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

        UserModel m = null;
        try {
            m = UserService.getUser(email, password);
            if(m == null) {
                throw new Exception("Invalid Email id or Password");
            }
        }
        catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ModelAndView("/index") {{
                addObject("loginMsg", "Invalid Email id or Password");
            }};
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ModelAndView("/index") {{
                addObject("loginMsg", "Login failed");
            }};
        }

        session.setAttribute("uid", "" + m.getUid());
        session.setAttribute("firstname", m.getFirstName());

        ModelAndView mv = new ModelAndView("");
        mv.setViewName("redirect:/tweet");
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

        UserModel m = null;
        try {
            m = UserService.addUser(firstname, lastname, email, password);
            if(m == null) throw new Exception("Unable to register user");
        }
        catch(Exception e) {
            e.printStackTrace();
            final String E = e.toString();
            return new ModelAndView("/index") {{
                addObject("signupMsg", "Unable to Signup" + E);
            }};
        }

        return new ModelAndView("/index") {{
            //addObject("signupMsg", "Successfully Registered!");
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
}