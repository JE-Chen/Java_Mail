package mail.core;

import jakarta.mail.Session;
import mail.core.superclass.AbstractCore;

import java.util.Properties;

/**
 * Get POP3 session
 */
public class POP3Core extends AbstractCore {
    private static String
            smtpHost = "smtp.gmail.com",
            pop3Host = "pop.gmail.com",
            smtpPort = "465",
            pop3Port = "995";

    /**
     * @param setHost Set SMTP host
     */
    @Override
    public void setSmtpHost(String setHost) {
        smtpHost = setHost;
    }

    /**
     * @param setPop3Host Set POP3 host
     */
    @Override
    public void setPop3Host(String setPop3Host) {
        pop3Host = setPop3Host;
    }

    /**
     * @param setSmtpPort Set SMTP port
     */
    @Override
    public void setSmtpPort(String setSmtpPort) {
        smtpPort = setSmtpPort;
    }

    /**
     * @param setPop3Port Set POP3 port
     */
    @Override
    public void setPop3Port(String setPop3Port) {
        pop3Port = setPop3Port;
    }

    /**
     * @return POP3 session
     */
    public static Session getSession() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3s.host", pop3Host);
        properties.put("mail.pop3s.port", pop3Port);
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return Session.getDefaultInstance(properties);
    }
}
