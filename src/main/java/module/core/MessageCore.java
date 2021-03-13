package module.core;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class MessageCore {

    public static String processMessage(Message[] messages) {
        String ans = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            if (messages.length != 0) {
                for (Message message : messages) {
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
                    ans = reader.readLine();
                }
            }
        }catch (MessagingException | IOException e){
            e.printStackTrace();
        }
        return ans;
    }
}