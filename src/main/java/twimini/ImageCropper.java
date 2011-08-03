package twimini;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 31/7/11
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Controller
public class ImageCropper extends HttpServlet {
    public static final int X1MIN_VALUE = 0;
    public static final int Y1MIN_VALUE = 0;
    public static final int X2MAX_VALUE = 599;
    public static final int Y2MAX_VALUE = 799;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    @RequestMapping(value = "/crop", method = RequestMethod.POST)
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int x1;
        int y1;
        int x2;
        int y2;
        int w;
        int h;

        try {
            x1 = Integer.parseInt(request.getParameter("x1"));
            y1 = Integer.parseInt(request.getParameter("y1"));
            x2 = Integer.parseInt(request.getParameter("x2"));
            y2 = Integer.parseInt(request.getParameter("y2"));
            w = Integer.parseInt(request.getParameter("width"));
            h = Integer.parseInt(request.getParameter("height"));
        } catch (Exception e) {
            x1 = X1MIN_VALUE;
            y1 = Y1MIN_VALUE;
            x2 = X2MAX_VALUE;
            y2 = Y2MAX_VALUE;
            w = WIDTH;
            h = HEIGHT;
        }

        String file = request.getParameter("file");
        String serverPath = "/home/rakesh/IdeaProjects/image/";
        //String serverPath = "C:\\Users\\purav.s\\Desktop\\twimini\\image\\";
        String sourceFile = serverPath + file + ".jpg";
        System.out.println(sourceFile);
        BufferedImage image = ImageIO.read(new File(sourceFile));
        System.out.println(image);
        BufferedImage out = image.getSubimage(x1, y1, w, h);
        ImageIO.write(out, "jpg", new File(serverPath + file + ".jpg"));
    }
}
