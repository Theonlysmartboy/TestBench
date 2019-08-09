package testbench;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;

/**
 *
 * @author TheOnlySmartBoy
 */
public class TestSendEmail {

    private static final String SMTP_SERVER = "smtp.gmail.com"; // smtp server adress/ip
    private static final String USERNAME = ""; //Your gmail adress
    private static final String PASSWORD = ""; //Your gmail password
    private static final String EMAIL_FROM = "info@otemainc.com";
    private static final String EMAIL_TO = "o2jose43@gmail.com";
    private static final String EMAIL_TO_CC = "tosby@otemainc.com";
    private static final String EMAIL_SUBJECT = "Test Send Email via SMTP";
    private static final String EMAIL_TEXT = "Hello Java Mail \n It works";

    public static void main(String[] args) {
        if (sendMail(EMAIL_FROM, EMAIL_TO, EMAIL_TO_CC, EMAIL_SUBJECT, EMAIL_TEXT)) {
            System.out.println("Mail sent successfully");
        } else {
            System.out.println("Unable to send mail");
        }
    }

    public static boolean sendMail(String from, String to, String cc, String subject, String message) {
        Properties prop = new Properties();

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.debug", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // Gmail nees tls to be called first
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.port", "465"); // default port is 25 but to use TLS we need port 587
        Session session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        MimeMessage msg = new MimeMessage(session);
        try {
            // from
            msg.setFrom(new InternetAddress(from));
            // to 
            msg.addRecipient(Message.RecipientType.TO,
                   new InternetAddress(to));
            // cc
            msg.addRecipient(Message.RecipientType.CC,
                   new InternetAddress(cc));
            // subject
            msg.setSubject(subject);
            // content 
            msg.setText(message);
            msg.setSentDate(new Date());
            // connect
            // Get SMTPTransport
            //SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            // connect
            // send
            Transport.send(msg, msg.getAllRecipients());
            System.out.println("Mail sent successfuly ");
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
