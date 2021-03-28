package checkMail.mailsend.father;

public abstract class AbstractSMTP {

    public abstract void send(String to, String from, String username, String user_password);

    public void setFileName(String fileName){}

    public void setAttachName(String attachName){}

    public void setBody(String body){};

    public void setContent(String content){};

    public abstract void setSubject(String subject);

}
