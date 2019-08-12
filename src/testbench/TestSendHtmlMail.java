package testbench;

import com.sun.mail.smtp.SMTPTransport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TheOnlySmartBoy
 */
public class TestSendHtmlMail {

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    private static final String EMAIL_FROM = "From@gmail.com";
    private static final String EMAIL_TO = "email_1@yahoo.com, email_2@gmail.com"; // you can list all emails separated with comas
    private static final String EMAIL_TO_CC = "";

    private static final String EMAIL_SUBJECT = "Test Send Email via SMTP (HTML)";
    private static final String EMAIL_TEXT = "<h1>Hello Java Mail \n ABC123</h1>";

    public static void main(String[] args) {
        if (sendMailWithAttachment(EMAIL_FROM, EMAIL_TO, EMAIL_TO_CC, EMAIL_SUBJECT, EMAIL_TEXT)) {
            System.out.println("Mail sent successfully");
        } else {
            System.out.println("Mail not sent successfully");
        }

    }

    public static boolean sendMailWithAttachment(String from, String to, String cc, String subject, String message) {

        Properties prop = System.getProperties();
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            msg.setFrom(new InternetAddress(from));

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to, false));

            msg.setRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(cc, false));

            msg.setSubject(subject);

            // HTML email
            msg.setDataHandler(new DataHandler(new HTMLDataSource(message)));

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            Logger.getLogger(TestSendHtmlMail.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return false;
    }

    static class HTMLDataSource implements DataSource {

        private final String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) {
                throw new IOException("html message is null!");
            }
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
}
