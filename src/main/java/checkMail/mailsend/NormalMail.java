package checkMail.mailsend;

import checkMail.mailsend.father.AbstractSMTP;
import checkMail.mailsend.father.SMTP;
import checkMail.core.SmtpCore;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NormalMail extends AbstractSMTP implements SMTP {

    private String subject = "default",
            body = "default";

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void send(String to, String from, String username, String user_password) {
        Session session = SmtpCore.getSession(username,user_password);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            System.out.println("Send Mail");
            Transport.send(message);
            System.out.println("Mail Sent");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
