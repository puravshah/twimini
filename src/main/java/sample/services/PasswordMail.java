package sample.services;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import sample.Mail;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 28/7/11
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordMail extends Mail{
    String name;

    private              String emailMsg= "Hello";
    private              String emailContent="This is a activation mail .Please click on the below given link to activate your account\n";
    private              String emailPasswordMsgTxt = "http://localhost:8080/forgotPassword";
    private              String emailSubjectTxt = "Forgot password Email";

    public PasswordMail(SimpleJdbcTemplate db) {
        super(db);
    }

    public PasswordMail(String email,String name) {
        super(email);
        this.name=name;
    }

    @Override
    public void userInfo() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
         //setEmail(getemail);
        String[] email=new String[1];
        setSendTo(email);
        getMessageText()[0]="Hello "+name+"\n" +
                                    emailContent+"\n"
                                    + emailPasswordMsgTxt+
                                     "\n\n\n\n Regards\n"+
                                     "Rakesh Kumar";
    }
}
