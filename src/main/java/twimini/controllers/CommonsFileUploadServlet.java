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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class CommonsFileUploadServlet extends HttpServlet {
    private static final String TMP_DIR_PATH = "c:\\tmp";
    private File tmpDir;
    private static final String DESTINATION_DIR_PATH = "/files";
    private File destinationDir;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    /*@RequestMapping("/user/edit")
    ModelAndView getEdit(HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("/edit");
        UserModel user = userService.getUser(session.getAttribute("uid").toString());
        mv.addObject("email", user.getEmail());
        return mv;
    }*/

    @RequestMapping(value = "/user/imageInfo", method = RequestMethod.POST)
    public ModelAndView handleImageUpload(@RequestParam("uid") String uid, @RequestParam("file") MultipartFile file) {
        //String path="/home/rakesh/IdeaProjects/image/";
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
                ModelAndView mv = new ModelAndView("/crop");
                mv.addObject(uid);
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

    /*@RequestMapping(value = "/user/accountInfo", method = RequestMethod.POST)
    public ModelAndView handleAccountUpload(@RequestParam("name") String name, @RequestParam("email") String email, HttpSession session) {
        try {
            UserService.setAccountInfo(name, email, (String) session.getAttribute("uid"));
            return new ModelAndView("redirect:/user/edit");
        } catch (Exception e) {
            return new ModelAndView("/user/edit");
        }

    }

    @RequestMapping(value = "/user/passwordInfo", method = RequestMethod.POST)
    public ModelAndView handlePasswordUpload(@RequestParam("old_password") String oldPassword, @RequestParam("new_password") String newPassword, @RequestParam("confirm_new_password") String confirmPassword, HttpSession session) {
        try {
            String uid = (String)session.getAttribute("uid");
            if(newPassword.equals(confirmPassword)) {
                return new ModelAndView("/user/edit") {{
                    addObject("passwordMsg", "The passwords dont match");
                    addObject("active", 1);
                }};
            }
            if (oldPassword.equals((String) UserService.getPassword(uid))) {
                UserService.setPassword(newPassword, uid);
                return new ModelAndView("/user/edit");
            } else {
                ModelAndView mv = new ModelAndView("/user/edit");

                mv.addObject("wrongPassword", "password is wrong please try again");
                return mv;
            }

        } catch (Exception e) {
            return new ModelAndView("/edit");
        }

    }*/


}
