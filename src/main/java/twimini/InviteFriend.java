package twimini;

import twimini.model.UserModel;
import twimini.services.UserService;

import javax.mail.Message;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 10/8/11
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class InviteFriend extends Mail {
    String name;
    private String emailMsg = "Hello";
    private static final String emailContent = "Your Friend has send you an invitation to join twimini";
    private String emailSignupMsgTxt = "http://localhost:8080/signup";
    //private String emailSubjectTxt = "twimini join invitaion";
    private String uuid;
    private String userName;
    public InviteFriend(String[] email, String userName) {
        super(email);
        this.userName=userName;
    }

    @Override
    public void userInfo() throws Exception {
        String[] message= new String[1];
        setEmailSubjectTxt("twimini join invitaion");
        message[0] =("Hello "+",\n\n\n"+emailContent +"Click  the link below to signup.\n"+emailSignupMsgTxt+"\n\n.\n\nRegards, \nTwimini.\n\nThis email was sent because your friend"+userName+" sent you this mail. If already a user of twimini, then kindly ignore this email.");
        setMessageText(message);
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
