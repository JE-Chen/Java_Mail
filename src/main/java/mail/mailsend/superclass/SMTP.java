package mail.mailsend.superclass;

public interface SMTP {

    /**
     * @param to Send to where address
     * @param from Send from where address
     */
    public void send(String to, String from);

}
