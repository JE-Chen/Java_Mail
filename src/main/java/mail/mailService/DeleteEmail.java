package mail.mailService;

import jakarta.mail.*;
import mail.core.POP3Core;
import mail.mailService.superclass.AbstractService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteEmail extends AbstractService {

    /**
     * @param username Mail user's account
     * @param password Mail user's password
     */
    public DeleteEmail(String username, String password) {
        Session session = POP3Core.getSession();
        try {
            // POP3S protocol
            Store store = session.getStore("pop3s");
            // connect use mail user's account and password
            store.connect(host, username, password);
            // Get user's mail inbox
            Folder emailFolder = store.getFolder("INBOX");
            // Right read and write
            emailFolder.open(Folder.READ_WRITE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Message[] messages = emailFolder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                String subject = message.getSubject();
                System.out.print("Do you want to delete this message [y/n] ? ");
                String ans = reader.readLine();
                if ("Y".equals(ans) || "y".equals(ans)) {
                    // set the DELETE flag to true
                    message.setFlag(Flags.Flag.DELETED, true);
                    System.out.println("Marked DELETE for message: " + subject);
                }
            }
            emailFolder.close(true);
            store.close();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

}
