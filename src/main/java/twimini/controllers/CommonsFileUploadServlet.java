package twimini.controllers;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 29/7/11
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import twimini.services.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class CommonsFileUploadServlet extends HttpServlet {
	private static final String TMP_DIR_PATH = "c:\\tmp";
	private File tmpDir;
	private static final String DESTINATION_DIR_PATH ="/files";
	private File destinationDir;

//	public void CommonsFileUploadServlet(ServletConfig config) throws ServletException {
//		super.init(config);
//		tmpDir = new File(TMP_DIR_PATH);
//		if(!tmpDir.isDirectory()) {
//			throw new ServletException(TMP_DIR_PATH + " is not a directory");
//		}
//		String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
//		destinationDir = new File(realPath);
//		if(!destinationDir.isDirectory()) {
//			throw new ServletException(DESTINATION_DIR_PATH+" is not a directory");
//		}
//
//	}

    @RequestMapping("/user/editProfile")
    ModelAndView getEditProfile(HttpSession session)
    {
        ModelAndView mv= new ModelAndView("/editProfile");
        mv.addObject((String)session.getAttribute("uid"));
        return mv;
    }

    @RequestMapping(value="/user/imageInfo" ,method = RequestMethod.POST)
    public ModelAndView handleImageUpload(@RequestParam("uid")String uid,@RequestParam("file") MultipartFile file)
    {
        String path="/home/rakesh/IdeaProjects/image/";
        try
        {
            File picture= new File(path+uid+".jpg");

            if(picture.exists())
            {
                picture.delete();
            }
            picture.createNewFile();
            if (!file.isEmpty())
            {
                byte[] bytes = file.getBytes();
                FileOutputStream fileOutputStream= new FileOutputStream(picture);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                ModelAndView mv= new ModelAndView("/crop");
                System.out.println("crop");
                mv.addObject(uid);
                return mv;
            }
            else
            {
                return new ModelAndView("redirect:/user/editProfile");
            }

        }catch(Exception e)
        {
            System.out.println("exception handled");
            return new ModelAndView("redirect:/user/editProfile");
        }

    }

    @RequestMapping(value="/user/accountInfo" ,method = RequestMethod.POST)
        public ModelAndView handleAccountUpload(@RequestParam("name")String name,@RequestParam("email") String email,HttpSession session)
        {
            try
            {
                UserService.setAccountInfo(name,email,(String)session.getAttribute("uid"));
               // UserService.setAccountInfo(name,email,(String)session.getAttribute("uid"));
                return  new ModelAndView("redirect:/user/editProfile");
            }catch(Exception e)
            {
                return new ModelAndView("/user/editProfile");
            }

        }

    @RequestMapping(value="/user/passwordInfo" ,method = RequestMethod.POST)
        public ModelAndView handlePasswordUpload(@RequestParam("old_password") String oldPassword,@RequestParam("new_password") String newPassword,HttpSession session)
        {
            try
            {
                String uid = (String)session.getAttribute("uid");
                if(oldPassword.equals((String) UserService.getPassword(uid)))
                {
                    UserService.setPassword(newPassword, uid);

                   return new ModelAndView("/editProfile");
                }
                else
                {
                      ModelAndView mv = new ModelAndView("/editProfile");

                      mv.addObject("wrongPassword","password is wrong please try again");
                    return  mv;
                }

            }catch(Exception e)
            {
                return new ModelAndView("/editProfile");
            }

        }












}