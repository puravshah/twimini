package twimini;

import twimini.services.UserService;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twimini.services.UserService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 28/7/11
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ActivationMail extends Mail {

    private String emailMsg = "Hello";
    /*private String emailContent = "This is a activation mail .Please click on the below given link to activate your account\n";
    private String emailSubjectTxt = "Confirmation Email";*/
    private static String emailContent = "Thank you for signing up on Twimini. Please click on the link given below to activate your account";
    private String emailActivationMsgTxt = "http://localhost:8080/activate";
    private static String emailSubjectTxt = "Activation Link";
//    private String emailContent = "This is a activation mail .Please click on the below given link to activate your account\n";
//    private String emailActivationMsgTxt = "http://localhost:8080/activate";
//    private String emailSubjectTxt = "Confirmation Email";
    public String getEmailSubjectTxt() {
        return emailSubjectTxt;
    }

    public String getEmailMsg() {
        return emailMsg;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public String getEmailActivationMsgTxt() {
        return emailActivationMsgTxt;
    }

    public void setEmailMsg(String emailMsg) {
        this.emailMsg = emailMsg;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public void setEmailActivationMsgTxt(String emailActivationMsgTxt) {
        this.emailActivationMsgTxt = emailActivationMsgTxt;
    }

    @Autowired
    public ActivationMail(UserService userService) {
        super(userService);
    }

    @Override
    public void userInfo() throws Exception {
        user = userService.getInactiveUser();
        setSendTo(new String[user.size()]);
        setMessageText(new String[user.size()]);

        for (int index = 0; index < user.size(); index++) {

            getSendTo()[index] = user.get(index).getEmail();
            //messageText[index] = String.format("Hello %s,\n\n%s\n%s?uid=%d\n\nRegards,\nTwimini\n\n%s", user.get(index).getName(), emailContent, emailMsgTxt, user.get(index).getUid(), notYou);
            getMessageText()[index] = String.format("Hello %s,\n\n%s");/*"Hello " + user.get(index).getName() + "\n" +
                    getEmailContent() + "\n"
                    + getEmailActivationMsgTxt() + "?" + "uid" + "=" + user.get(index).getUid() +
                    "\n\n\n\n Regards\n" +
                    "Rakesh Kumar";*/
        }
    }

    @Override
    public void run() {
        try {
            while (true) {

                runMultipleMail();
                Set<String> ids= new HashSet<String>();
                for(String string: getSendTo())
                ids.add(string);
                UserService.setToNotactivated(ids);
                sleep(150000);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

}
