package module.mailforward;

import module.core.POP3Core;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class ForwardEMail {

    public void forwardMail(String username, String user_password){
        Session session = POP3Core.getSession(username, user_password);
        try {
            Store store = session.getStore("pop3s");
            store.connect("pop.gmail.com", username, user_password);
            Folder folder = store.getFolder("inbox");
            folder.open(Folder.READ_ONLY);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));
            Message[] messages = folder.getMessages();
            if (messages.length != 0) {
                for (int i = 0, n = messages.length; i < n; i++) {
                    Message message = messages[i];
                    String from = InternetAddress.toString(message.getFrom());
                    if (from != null) {
                        System.out.println("From: " + from);
                    }
                    String replyTo = InternetAddress.toString(message
                            .getReplyTo());
                    if (replyTo != null) {
                        System.out.println("Reply-to: " + replyTo);
                    }
                    String to = InternetAddress.toString(message
                            .getRecipients(Message.RecipientType.TO));
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
                        Message forward = new MimeMessage(session);
                        forward.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(from));
                        forward.setSubject("Fwd: " + message.getSubject());
                        forward.setFrom(new InternetAddress(to));
                        MimeBodyPart messageBodyPart = new MimeBodyPart();
                        Multipart multipart = new MimeMultipart();
                        messageBodyPart.setContent(message, "message/rfc822");
                        multipart.addBodyPart(messageBodyPart);
                        forward.setContent(multipart);
                        forward.saveChanges();
                        Transport t = session.getTransport("smtp");
                        try {
                            t.connect(username, user_password);
                            t.sendMessage(forward, forward.getAllRecipients());
                        } finally {
                            t.close();
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