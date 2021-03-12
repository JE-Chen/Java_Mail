package main;

import module.smtp.father.AbstractSMTP;
import module.smtp.AttachmentMail;


public class test {

    public static void main(String[] argc){
        AbstractSMTP Mailman = new AttachmentMail();
        Mailman.setFileName("/test.txt","test");
        Mailman.send("zenmailman@gmail.com","zenmailman@gmail.com","zenmailman@gmail.com","zenthe1397");
    }
}



