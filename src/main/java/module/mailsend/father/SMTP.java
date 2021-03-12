package module.mailsend.father;

public interface SMTP {

    public void send(String to, String from, String username, String user_password);

}
