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
public class ImageCropper extends HttpServlet
{
  @RequestMapping(value="/crop", method= RequestMethod.POST)
  public void service(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException
  {
    int x1=Integer.parseInt(request.getParameter("x1"));
    int y1=Integer.parseInt(request.getParameter("y1"));
    int x2=Integer.parseInt(request.getParameter("x2"));
    int y2=Integer.parseInt(request.getParameter("y2"));
    int w=Integer.parseInt(request.getParameter("width"));
    int h=Integer.parseInt(request.getParameter("height"));

    String file=request.getParameter("file");
    String serverPath="/home/rakesh/IdeaProjects/image/";
    String sourceFile=serverPath+file+".jpg";
    BufferedImage image=ImageIO.read(new File(sourceFile));
    BufferedImage out=image.getSubimage(x1,y1,w,h);
    ImageIO.write(out,"jpg",new File(serverPath+file+".jpg"));
//    PrintWriter printer=response.getWriter();
//    response.setContentType("text/html");
//    printer.println("Photo cropped from "+x1+","+y1+" to the width of "+w+" and height of "+h);
//    printer.println("<img src=\""+serverPath+"HC"+file+"t.jpg"+"\" />");
  }
}
