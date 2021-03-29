package mail.mailService;

import jakarta.mail.*;
import mail.mailService.superclass.AbstractService;

import java.io.IOException;
import java.util.Properties;

public class CheckEmail extends AbstractService {

    public CheckEmail(String username, String password) {
        try {
            Properties props = new Properties();
            props.setProperty("mail.imap.ssl.enable", "true");
            Session session = Session.getInstance(props);
            Store store = session.getStore("imap");
            store.connect(host, username, password);
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
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
