package mailService.mailsend;

import mailService.core.SmtpCore;
import mailService.mailsend.father.AbstractSMTP;
import mailService.mailsend.father.SMTP;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;

public class NormalMail extends AbstractSMTP implements SMTP {

    private String subject = "default",
            body = "default",
            type = "default",
            content = "<html>default</html>",
            fileName,
            attachName = "default";

    private final MimeMessage message;

    public NormalMail(String username, String user_password) {
        Session session = SmtpCore.getSession(username, user_password);
        message = new MimeMessage(session);
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = new File("").getAbsolutePath() + "/" + fileName;
    }

    @Override
    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void send(String to, String from) {
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            switch (type) {
                case "attach":
                    try {
                        if (fileName == null || attachName == null) throw new NullPointerException();
                        BodyPart messageBodyPart = new MimeBodyPart();
                        messageBodyPart.setText(body);
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(messageBodyPart);
                        messageBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(fileName);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(attachName);
                        multipart.addBodyPart(messageBodyPart);
                        message.setContent(multipart);
                        Transport.send(message);
                        System.out.println("Attach mail sent");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "html":
                    message.setContent(content, "text/html");
                    Transport.send(message);
                    System.out.println("Sent html mail");
                    break;
                case "picture":
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
                    break;
                default:
                    message.setText(body);
                    System.out.println("Send Mail");
                    Transport.send(message);
                    System.out.println("Mail Sent");
            }
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
