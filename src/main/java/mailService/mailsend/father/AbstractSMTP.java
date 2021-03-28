package mailService.mailsend.father;

public abstract class AbstractSMTP {

    public abstract void send(String to, String from);

    public void setFileName(String fileName){}

    public void setAttachName(String attachName){}

    public void setBody(String body){}

    public void setContent(String content){}

    public void setType(String type){}

    public abstract void setSubject(String subject);

}
