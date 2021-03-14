package module.mail_function;

import module.core.POP3Core;

import javax.mail.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeleteEmail {

    public void deleteEmail(String username, String user_password) {
        String host = "smtp.gmail.com";
        Session session = POP3Core.getSession(username, user_password);
        try {
            Store store = session.getStore("pop3s");
            store.connect(host, username, user_password);
            Folder emailFolder = store.getFolder("INBOX");
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
                } else if ("n".equals(ans)) {
                    break;
                }
            }
            emailFolder.close(true);
            store.close();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

}
