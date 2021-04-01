package normalMailTest;

import mail.mailsend.NormalMail;
import mail.mailsend.superclass.AbstractSMTP;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class NormalMailTest {

    private static String email, password;
    private AbstractSMTP mailer;

    @BeforeAll
    static void init() {
        String fileName = new File("").getAbsolutePath() + "/testData.json";
        StringBuilder data = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                data.append(bufferedReader.readLine());
            }
            String tmp = data.toString();
            System.out.println(tmp);
            JSONObject jsonObject = new JSONObject(tmp);
            email = (String) jsonObject.get("email");
            password = (String) jsonObject.get("password");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        if (email == null)
            email = "jetestmailman@gmail.com";
        if (password == null)
            password = "jetestpassword";
    }

    @Test
    void testTextMail() {
        mailer = new NormalMail(email, password);
        mailer.send("zenmailman@gmail.com", email);
    }

    @Test
    void testHtmlMail() {
        mailer = new NormalMail(email, password);
        mailer.setType("html");
        mailer.send("zenmailman@gmail.com", email);
    }

    @Test
    void testAttachMail() {
        mailer = new NormalMail(email, password);
        mailer.setType("attach");
        mailer.setFileName("firefox.png");
        mailer.setAttachName("test");
        mailer.send("zenmailman@gmail.com", email);
    }

    @Test
    void testPictureMail() {
        mailer = new NormalMail(email, password);
        mailer.setType("picture");
        mailer.setFileName("firefox.png");
        mailer.send("zenmailman@gmail.com", email);
    }
}