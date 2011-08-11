package twimini;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 10/8/11
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class InviteFriend extends Mail {
    private static final String emailContent = "has sent you an invitation to join Twimini.";
    private String emailSignupMsgTxt = "http://localhost:8080";
    private String userName;
    public InviteFriend(String[] email, String userName) {
        super(email);
        this.userName = userName;
    }

    @Override
    public void userInfo() throws Exception {
        String[] message= new String[1];
        setEmailSubjectTxt("Invitation to join Twimini");
        message[0] = String.format("Hello,\n\nYour friend %s %s Click on the link below to join your friend.\n%s\n\nRegards,\nTwimini.\n\nNote: If you don't know %s, then kindly ignore this email.", userName,  emailContent, emailSignupMsgTxt, userName);
        setMessageText(message);
    }

    @Override
    public void run() {
        try {
            runMultipleMail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
