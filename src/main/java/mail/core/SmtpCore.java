package mail.core;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import mail.core.superclass.AbstractCore;

import java.util.Properties;

/**
 * Get SMTP session
 */
public class SmtpCore extends AbstractCore {
    private static String
            smtpHost = "smtp.gmail.com",
            smtpPort = "465";

    /**
     * @param setSmtpHost Set SMTP host
     */
    @Override
    public void setSmtpHost(String setSmtpHost) {
        smtpHost = setSmtpHost;
    }

    /**
     * @param setSmtpPort set SMTP port
     */
    @Override
    public void setSmtpPort(String setSmtpPort) {
        smtpPort = setSmtpPort;
    }

    /**
     * @param username Mail user's name
     * @param user_password Mail user's password
     * @return  Mail session
     */
    public static Session getSession(String username, String user_password) {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, user_password);
            }
        });
    }
}
