package sample.controllers;

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
    ModelAndView getEditProfile( @RequestParam String uid,HttpSession session)
    {
        ModelAndView mv= new ModelAndView("/editProfile");
        mv.addObject(uid);
        return mv;
    }

    @RequestMapping(value="/user/editProfile" ,method = RequestMethod.POST)
    public ModelAndView handleFormUpload(@RequestParam("uid")String uid,@RequestParam("file") MultipartFile file)
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
                mv.addObject(uid);
                return mv;
            }
            else
            {
                return new ModelAndView("/editProfile");
            }

        }catch(Exception e)
        {
            return new ModelAndView("/editProfile");
        }

    }



}