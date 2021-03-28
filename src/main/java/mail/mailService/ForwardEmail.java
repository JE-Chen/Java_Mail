package mail.mailService;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import mail.core.POP3Core;
import mail.mailService.superclass.AbstractService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ForwardEmail extends AbstractService {

    public ForwardEmail(String username, String user_password) {
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
                for (Message message : messages) {
                    String from = InternetAddress.toString(message.getFrom());
                    String to = InternetAddress.toString(message.getRecipients(Message.RecipientType.TO));
                    System.out.println("---------------------------------");
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.print("Do you want to reply [y/n] : ");
                    String ans = reader.readLine();
                    if ("Y".equals(ans) || "y".equals(ans)) {
                        Message forward = new MimeMessage(session);
                        forward.setRecipients(Message.RecipientType.TO, InternetAddress.parse(from));
                        forward.setSubject(message.getSubject());
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
