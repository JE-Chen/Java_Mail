package module.mailsend;

import module.core.SmtpCore;
import module.mailsend.father.AbstractSMTP;
import module.mailsend.father.SMTP;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HtmlMail extends AbstractSMTP implements SMTP {

    private String subject = "default",
            content = "<h1>Hello Mail</h1>";

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void send(String to, String from, String username, String user_password) {
        Session session = SmtpCore.getSession(username, user_password);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(
                    content,
                    "text/html");
            Transport.send(message);
            System.out.println("Sent html mail");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
