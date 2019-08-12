package testbench;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author TheOnlySmartBoy
 */
public class TestSendMailWithAttachement {

   private static final String SMTP_SERVER = "smtp.gmail.com"; // smtp server adress/ip
    private static final String USERNAME = ""; //Your gmail adress
    private static final String PASSWORD = ""; //Your gmail password
    private static final String EMAIL_FROM = "info@otemainc.com";
    private static final String EMAIL_TO = "o2jose43@gmail.com";
    private static final String EMAIL_TO_CC = "tosby@otemainc.com";
    private static final String EMAIL_SUBJECT = "Test Send Email via SMTP";
    private static final String EMAIL_TEXT = "Hello Java Mail \n It works";

    public static void main(String[] args) {
    }

    public static boolean sendMailWithAttachment(String from, String to, String cc, String subject, String message) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_SERVER);
        props.put("mail.smtp.port", "25");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage mesage = new MimeMessage(session);

            // Set From: header field of the header.
            mesage.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            mesage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            mesage.setSubject("Testing Subject");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("This is message body");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "/home/manisha/file.txt";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            mesage.setContent(multipart);

            // Send message
            Transport.send(mesage);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            Logger.getLogger(TestSendMailWithAttachement.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return true;
    }
}
