package twimini;

import twimini.model.UserModel;
import twimini.services.UserService;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 28/7/11
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class PasswordMail extends Mail {
    private UserModel userModel;
    private String emailSubjectTxt = "Twimini Password Reset";
    private String uuid;


    public PasswordMail(UserModel userModel, String uuid) {
        super(userModel.getEmail());
        this.userModel=userModel;
        this.uuid = uuid;
    }

    @Override
    public void userInfo() throws Exception {
        String[] email = new String[1];
        email[0]=getEmail();
        setSendTo(email);
        String url = "http://localhost:8080/reset?token=" + uuid;
        String message=(String.format("Hello %s,\n\nYou have requested to reset your password.\nClick on the link below to do so.\n%s\n\nNote: This token can be used only once, and it will expire in 2 days.\n\nRegards, \nTwimini.\n\nThis email was sent because someone requested to reset the password associated with this account. If you did not ask to reset your password, then kindly ignore this email.",userModel.getName(), url));
        setMessageText(message);
        setEmailSubjectTxt(emailSubjectTxt);
    }

    @Override
    public void run() {
        try {
            runMultipleMail();
            //sleep(150000);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

