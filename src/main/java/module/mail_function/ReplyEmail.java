package module.mail_function;

import module.core.POP3Core;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class ReplyEmail {

    public void reply(String username, String user_password) {
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
                    System.out.print("Do you want to reply [y/n] : ");
                    String ans = reader.readLine();
                    if ("Y".equals(ans) || "y".equals(ans)) {
                        Message replyMessage;
                        replyMessage =  message.reply(false);
                        replyMessage.setFrom(new InternetAddress(to));
                        replyMessage.setText("Thanks");
                        replyMessage.setReplyTo(message.getReplyTo());
                        Transport t = session.getTransport("smtp");
                        try {
                            t.connect(username, user_password);
                            t.sendMessage(replyMessage,
                                    replyMessage.getAllRecipients());
                        } finally {
                            t.close();
                        }
                        folder.close(false);
                        store.close();
                    } else if ("n".equals(ans)) {
                        break;
                    }
                }

            } else {
                System.out.println("There is no msg....");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
