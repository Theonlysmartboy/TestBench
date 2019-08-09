package testbench;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author TheOnlySmartBoy
 */
public class TestEmail {

    private static final String SMTP_SERVER = "smtp.gmail.com"; // smtp server adress/ip
    private static final String USERNAME = "o2jsmartboy@gmail.com"; //Your gmail adress
    private static final String PASSWORD = "MasterTosby2#"; //Your gmail password

    private static final String EMAIL_FROM = "info@otemainc.com";
    private static final String EMAIL_TO = "tosby@otemainc.com";
    private static final String EMAIL_TO_CC = "";

    private static final String EMAIL_SUBJECT = "Test Send Email via SMTP";
    private static final String EMAIL_TEXT = "Hello Java Mail \n ABC123";

    public static void main(String[] args) {
        if (sendMail(EMAIL_FROM, EMAIL_TO, EMAIL_TO_CC, EMAIL_SUBJECT, EMAIL_TEXT)) {
            System.out.println("Mail sent successfully");
        } else {
            System.out.println("Unable to send mail");
        }

    }

    public static boolean sendMail(String from, String to, String cc, String subject, String message) {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", "true"); // Gmail nees tls to be called first
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587"); // default port is 25 but to use TLS we need port 587

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(from));

            // to 
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to, false));

            // cc
            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(cc, false));

            // subject
            msg.setSubject(subject);

            // content 
            msg.setText(message);

            msg.setSentDate(new Date());

            // connect
            try ( // Get SMTPTransport
                    SMTPTransport t = (SMTPTransport) session.getTransport("smtp")) {
                // connect
                t.connect(SMTP_SERVER, USERNAME, PASSWORD);

                // send
                t.sendMessage(msg, msg.getAllRecipients());

                System.out.println("Response: " + t.getLastServerResponse());
            }

        } catch (MessagingException e) {
           e.printStackTrace();
            return false;

        }
        return true;
    }
}
