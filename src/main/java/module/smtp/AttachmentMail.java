package module.smtp;

import module.smtp.father.AbstractSMTP;
import module.smtp.father.SMTP;
import module.smtp.core.SmtpCore;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;

public class AttachmentMail extends AbstractSMTP implements SMTP {

    private String fileName, attachName, subject = "default", body = "default";

    @Override
    public void setFileName(String fileName, String attachName) {
        this.fileName = new File("").getAbsolutePath() + fileName;
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
    public void send(String to, String from, String username, String user_password) {
        Session session = SmtpCore.getSession(username, user_password);
        if (fileName != null && attachName != null) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                message.setSubject(subject);
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
                System.out.println("Sent attach mail successfully....");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Pls set all detail");
        }
    }
}