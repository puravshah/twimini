package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import sample.model.UserModel;
import sample.services.UserService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: rakesh
 * Date: 28/7/11
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */

public class Mail extends Thread
{
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static       String emailMsg= "Hello";
    private static       String emailContent="This is a activation mail .Please click on the below given link to activate your account\n";
    private static final String emailMsgTxt = "http://localhost:8080/activate";
    private static final String emailSubjectTxt = "Confirmation Email";
    private static final String emailFromAddress = "rakesh.sentmailid@gmail.com";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    private  String[] sendTo ;
    private final SimpleJdbcTemplate db;
    List<UserModel> user;
    UserService userService;
    List<String> EmailList;
    String[] messageText;

    @Autowired
    Mail(SimpleJdbcTemplate db)
    {
        this.db=db;
    }

    @Async
    public  void RunMail()
    {
        try
        {

            user= UserService.getInactiveUser();

            sendTo=new String[user.size()];
            messageText=new String[user.size()];
            for(int index=0;index<user.size();index++)
            {

                sendTo[index]=user.get(index).getEmail();
                messageText[index]="Hello "+user.get(index).getName()+"\n" +
                                    emailContent+"\n"
                                    +emailMsgTxt+"?"+"uid"+"="+user.get(index).getUid()+
                                     "\n\n\n\n Regards\n"+
                                     "Rakesh Kumar";

            }

            if(sendTo.length>0)
            {
                Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

                sendSSLMessage(sendTo, emailSubjectTxt,
                        messageText, emailFromAddress);
                UserService.setToPartialState();
            }


        }
        catch(Exception e)
        {

            System.out.println(""+e);
        }

    }

    public void sendSSLMessage(String recipients[], String subject,String[] message, String from) throws MessagingException
        {
            boolean debug = true;
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST_NAME);
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.socketFactory.port", SMTP_PORT);
            props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.put("mail.smtp.socketFactory.fallback", "false");
            Session session = Session.getDefaultInstance
                                    (
                                        props,
                                        new javax.mail.Authenticator()
                                            {
                                                protected PasswordAuthentication getPasswordAuthentication()
                                                {
                                                    return new PasswordAuthentication("rakesh.sentmailid", "shivamjun");
                                                }
                                            }
                                    );

           // session.setDebug(debug);

            Message msg = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(from);
            msg.setFrom(addressFrom);

            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++)
            {
                addressTo[i] = new InternetAddress(recipients[i]);

                msg.setRecipient(Message.RecipientType.TO, addressTo[i]);

                // Setting the Subject and Content Type
                msg.setSubject(subject);

                msg.setContent(message[i], "text/plain");
                Transport.send(msg);
                }
    }

    public void run()
    {
        try
        {
            while(true)
            {

                RunMail();
                sleep(150000);
            }
        }
        catch (Exception e1)
        {
          e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}