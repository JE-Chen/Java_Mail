package mailService.core;
import javax.mail.Session;
import java.util.Properties;

public class POP3Core {
    public static Session getSession(String username, String user_password){
        String host = "smtp.gmail.com";
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3s.host", "pop.gmail.com");
        properties.put("mail.pop3s.port", "995");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return Session.getDefaultInstance(properties);
    }
}
