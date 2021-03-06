package twimini;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 30/7/11
 * Time: 1:37 PM
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

@Controller
public class ImageServlet extends HttpServlet {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    private String imagePath;
    public static final int WIDTH  = 600;
    public static final int HEIGHT = 600;

    public void init() throws ServletException {
        this.imagePath = "/image";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String requestedImage = request.getPathInfo();
        if (requestedImage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        //String imageCopyPath="/home/rakesh/IdeaProjects/";
        String imageCopyPath = "C:\\Users\\purav.s\\Desktop\\twimini\\";
        File image = new File(imageCopyPath+imagePath, URLDecoder.decode(requestedImage, "UTF-8"));

        if (!image.exists()) {
            requestedImage="default-profile-picture.png";
            image=new File(imageCopyPath+imagePath,URLDecoder.decode(requestedImage,"UTF-8"));
        }

        String contentType = getServletContext().getMimeType(image.getName());
        if (contentType == null || !contentType.startsWith("image")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(image), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            close(output);
            close(input);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

