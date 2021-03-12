package module.smtp.father;

public abstract class AbstractSMTP {

    public abstract void send(String to, String from, String username, String user_password);

    public void setFileName(String fileName, String attachName){};

    public abstract void setBody(String body);

    public abstract void setSubject(String subject);

}
