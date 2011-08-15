package twimini;

import twimini.services.UserService;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 28/7/11
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActivationMail extends Mail {

    private String emailMsg = "Hello";
    private static String emailContent = "Thank you for signing up on Twimini. Please click on the link given below to activate your account";
    private String emailActivationMsgTxt = "http://localhost:8080/activate";
    private static String emailSubjectTxt = "Activation Link";
    private String note = "Note: If you do not activate your account within 2 days, then your account will be deleted.";
    public String getEmailSubjectTxt() {
        return emailSubjectTxt;
    }


    public String getEmailActivationMsgTxt() {
        return emailActivationMsgTxt;
    }

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
            String token = userService.getActivationToken("" + user.get(index).getUid());
            getMessageText()[index] = String.format("Hello %s,\n\n%s\n%s?token=%s\n\nRegards,\nTwimini\n\n%s\n%s", user.get(index).getName(), emailContent, getEmailActivationMsgTxt(), token, note, getNotYou());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                sleep(10000);
                //sleep(5 * 60 * 1000);
                runMultipleMail();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
