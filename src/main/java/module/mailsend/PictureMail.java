package module.mailsend;

import module.core.SmtpCore;
import module.mailsend.father.AbstractSMTP;
import module.mailsend.father.SMTP;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class PictureMail extends AbstractSMTP implements SMTP {

    private String subject = "default",
            content = "<H1>Hello World</H1>" + "<img src=cid:image>",
            fileName,
            attachName = "default";

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setAttachName(String attachName) {
        this.attachName = attachName;
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
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = content;
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            messageBodyPart.setFileName(attachName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Sent image mail");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
