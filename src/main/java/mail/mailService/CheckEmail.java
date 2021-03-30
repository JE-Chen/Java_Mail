package mail.mailService;

import jakarta.mail.*;
import mail.mailService.superclass.AbstractService;

import java.io.IOException;
import java.util.Properties;

public class CheckEmail extends AbstractService {

    /**
     * @param username Mail user's account
     * @param password Mail user's password
     */
    public CheckEmail(String username, String password) {
        try {
            Properties props = new Properties();
            props.setProperty("mail.imap.ssl.enable", "true");
            Session session = Session.getInstance(props);
            // IMAP protocol
            Store store = session.getStore("imap");
            // connect use mail user's account and password
            store.connect(host, username, password);
            // Get user's mail inbox
            Folder emailFolder = store.getFolder("INBOX");
            // right -> read only
            emailFolder.open(Folder.READ_ONLY);
            // all mail
            Message[] messages = emailFolder.getMessages();
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("Email index " + (i + 1) + "\n");
                System.out.println("Subject: " + message.getSubject() + "\n");
                System.out.println("From: " + message.getFrom()[0] + "\n");
                System.out.println("Text: " + message.getContent().toString() + "\n");
            }
            emailFolder.close(false);
            store.close();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

}
