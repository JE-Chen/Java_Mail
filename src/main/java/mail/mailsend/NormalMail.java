package mail.mailsend;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import mail.core.SmtpCore;
import mail.mailsend.superclass.AbstractSMTP;
import mail.mailsend.superclass.SMTP;

import java.io.File;

public class NormalMail extends AbstractSMTP implements SMTP {

    private final MimeMessage message;
    private String subject = "default",
            body = "default",
            type = "default",
            content = "<html>default</html>",
            fileName,
            attachName = "default";

    /***
     * @param username Mail user's account
     * @param user_password Mail user's password
     */
    public NormalMail(String username, String user_password) {
        Session session = SmtpCore.getSession(username, user_password);
        message = new MimeMessage(session);
    }

    /**
     * @param fileName Attach file name
     */
    @Override
    public void setFileName(String fileName) {
        this.fileName = new File("").getAbsolutePath() + "/" + fileName;
    }

    /**
     * @param attachName Mail show attach name
     */
    @Override
    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    /**
     * @param body Mail's body
     */
    @Override
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @param subject Mail's subject
     */
    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @param content Mail's content
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @param type Mail's type
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param to Send to where address
     * @param from Send from where address
     */
    @Override
    public void send(String to, String from) {
        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            switch (type) {
                // type attach
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
                    // type html
                case "html":
                    message.setContent(content, "text/html");
                    Transport.send(message);
                    System.out.println("Sent html mail");
                    break;
                    //type picture
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
                    Transport.send(message);
                    System.out.println("Sent normal mail");
            }
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
