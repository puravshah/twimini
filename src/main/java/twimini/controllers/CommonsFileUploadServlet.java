package twimini.controllers;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 29/7/11
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import twimini.model.UserModel;
import twimini.services.UserService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class CommonsFileUploadServlet {
    private File destinationDir;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;
    private final UserService userService;

    @Autowired
    public CommonsFileUploadServlet(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/imageInfo", method = RequestMethod.POST)
    public ModelAndView handleImageUpload(@RequestParam("uid") String uid, @RequestParam("file") MultipartFile file,HttpSession session) {
        //String path="/home/rakesh/IdeaProjects/image/";
        //if(session.getAttribute("uid") == null) return new ModelAndView("/login");
        String path = "C:\\Users\\purav.s\\Desktop\\twimini\\image\\";
        try {
            File picture = new File(path + uid + ".png");
            if (picture.exists()) {
                picture.delete();
            }
            picture.createNewFile();
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                FileOutputStream fileOutputStream = new FileOutputStream(picture);
                fileOutputStream.write(bytes);
                fileOutputStream.close();

                BufferedImage originalImage = ImageIO.read(picture);
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

                BufferedImage resizeImageJpg = resizeImage(originalImage, type);
                picture.delete();
                ImageIO.write(resizeImageJpg, "png", picture);
                ModelAndView mv = new ModelAndView("/edit");
                mv.addObject("active", 3);
                final UserModel user = userService.getUser(session.getAttribute("uid").toString());
                mv.addObject("email", user.getEmail());
                mv.addObject("src","/image/"+uid+".png");
                return mv;
            } else {
                return new ModelAndView("redirect:/user/edit");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception handled");
            return new ModelAndView("redirect:/user/edit");
        }

    }

    private BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(WIDTH, HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }
}
