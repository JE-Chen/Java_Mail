package module.mailcheck;

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
            Date date;
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
                for (int i = 0, n = messages.length; i < n; i++) {
                    Message message = messages[i];
                    date = message.getSentDate();
                    String from = InternetAddress.toString(message.getFrom());
                    if (from != null) {
                        System.out.println("From: " + from);
                    }
                    String replyTo = InternetAddress.toString(message.getReplyTo());
                    if (replyTo != null) {
                        System.out.println("Reply-to: " + replyTo);
                    }
                    String to = InternetAddress.toString(message.getRecipients(Message.RecipientType.TO));
                    if (to != null) {
                        System.out.println("To: " + to);
                    }
                    String subject = message.getSubject();
                    if (subject != null) {
                        System.out.println("Subject: " + subject);
                    }
                    Date sent = message.getSentDate();
                    if (sent != null) {
                        System.out.println("Sent: " + sent);
                    }
                    System.out.print("Do you want to reply [y/n] : ");
                    String ans = reader.readLine();
                    if ("Y".equals(ans) || "y".equals(ans)) {
                        Message replyMessage = new MimeMessage(session);
                        replyMessage = (MimeMessage) message.reply(false);
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
