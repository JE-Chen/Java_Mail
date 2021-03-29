package mail.mailService;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import mail.core.POP3Core;
import mail.mailService.superclass.AbstractService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ReplyEmail extends AbstractService {

    public ReplyEmail(String username, String user_password) {
        try {
            Session session = POP3Core.getSession(username, user_password);
            Store store = session.getStore("pop3s");
            store.connect("pop.gmail.com", username, user_password);
            Folder folder = store.getFolder("inbox");
            if (!folder.exists()) {
                System.out.println("inbox not found");
                System.exit(0);
            }
            folder.open(Folder.READ_ONLY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Message[] messages = folder.getMessages();
            if (messages.length != 0) {
                for (Message message : messages) {
                    String to = InternetAddress.toString(message.getRecipients(Message.RecipientType.TO));
                    System.out.println("---------------------------------");
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.print("Do you want to reply [y/n] : ");
                    String ans = reader.readLine();
                    if ("Y".equals(ans) || "y".equals(ans)) {
                        Message replyMessage;
                        replyMessage = message.reply(false);
                        replyMessage.setFrom(new InternetAddress(to));
                        replyMessage.setText("Thanks");
                        replyMessage.setReplyTo(message.getReplyTo());
                        try (Transport t = session.getTransport("smtp")) {
                            t.connect(username, user_password);
                            t.sendMessage(replyMessage,
                                    replyMessage.getAllRecipients());
                        }
                        folder.close(false);
                        store.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
