package com.je_chen.mail.Main;

import com.je_chen.mail.Module.Mailer;

public class Main {

    public static void main(String argc[]){
        Mailer Mailman = new Mailer();
        Mailman.SendMail("**@gmail.com","**@gmail.com","**@gmail.com","**********");
    }
}



