package sample;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import sample.model.UserModel;
import sample.services.UserService;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 28/7/11
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
    public  class PasswordMail extends Mail{
        private UserService service;

        String name;

        private              String emailMsg= "Hello";
        private              String emailContent="This is a activation mail .Please click on the below given link to activate your account\n";
        private              String emailPasswordMsgTxt = "http://localhost:8080/forgotPassword";
        private              String emailSubjectTxt = "Forgot password Email";

        public PasswordMail(SimpleJdbcTemplate db) {
            super(db);
        }

        public PasswordMail(String email) {
            super(email);

        }

        @Override
        public void userInfo() throws Exception {
            //To change body of implemented methods use File | Settings | File Templates.
             //setEmail(getemail);
            String[] email=new String[1];
            setSendTo(email);
            UserModel user=UserService.getUserInfo(email[0]);

            getMessageText()[0]="Hello "+user.getName()+"\n" +
                                        emailContent+"\n"
                                        + emailPasswordMsgTxt+
                                         "\n\n\n\n Regards\n"+
                                         "Rakesh Kumar";
        }

        @Override
        public void run() {
            //To change body of implemented methods use File | Settings | File Templates.
            {
            try
            {

                    runMultipleMail();
                    //sleep(150000);

            }
            catch (Exception e1)
            {
              e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

        }
    }

