package sample;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;
import sample.model.UserModel;
import sample.services.UserService;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 28/7/11
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordMail extends Mail {
    private UserService service;

    String name;
    private String emailMsg = "Hello";
    private static final String emailContent = "";
    private String emailPasswordMsgTxt = "http://localhost:8080/forgotPassword";
    private String emailSubjectTxt = "Forgot password Email";
    private String uuid;

    public PasswordMail(SimpleJdbcTemplate db) {
        super(db);
    }

    public PasswordMail(String email, String uuid) {
        super(email);
        this.uuid = uuid;
    }

    @Override
    public void userInfo() throws Exception {
        //setEmail(getemail);
        String[] emails = new String[1];
        emails[0] = email;
        setSendTo(emails);
        UserModel user = UserService.getUserInfo(emails[0]);

        getMessageText()[0] = String.format("Hello %s,\n\nThe code to reset your password is given below. Using this code, you can reset your password.\nReset code: %s\n\nNote: This token can be used only once.\n\nRegards, \nTwimini.\n\nThis email was sent because someone requested to reset the password associated with this account. If you did not ask to reset your password, then kindly ignore this email.", user.getName(), uuid);
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

