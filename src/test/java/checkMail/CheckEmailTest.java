package checkMail;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class CheckEmailTest {

    @Test
    public void checkEmailTest() {
        String host = "smtp.gmail.com";
        String email = "*****", password = "*****";
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
        CheckEmail checkEmail = new CheckEmail();
        checkEmail.checkMail(host, email, password);
    }

}