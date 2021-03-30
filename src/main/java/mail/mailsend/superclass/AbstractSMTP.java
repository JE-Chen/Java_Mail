package mail.mailsend.superclass;

public abstract class AbstractSMTP {

    public abstract void send(String to, String from);

    /**0
     * @param fileName Attach file name
     */
    public void setFileName(String fileName) {
    }

    /**
     * @param attachName Mail show attach name
     */
    public void setAttachName(String attachName) {
    }

    /**
     * @param body Mail's body
     */
    public void setBody(String body) {
    }

    /**
     * @param content Mail's content
     */
    public void setContent(String content) {
    }

    /**
     * @param type Mail's type
     */
    public void setType(String type) {
    }

    /**
     * @param subject Mail's subject
     */
    public abstract void setSubject(String subject);

}
