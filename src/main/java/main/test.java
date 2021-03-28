package main;

import mailService.mailsend.NormalMail;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class test {

    public static void main(String[] argc) {
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
        NormalMail normalMail = new NormalMail(email, password);
        normalMail.send("zenmailman@gmail.com", email);
    }
}



