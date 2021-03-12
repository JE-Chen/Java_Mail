package main;


import org.json.JSONObject;

public class test {

    public static void main(String[] argc){
        try {
            String tmp = "{\"Data\":\"Test\"}";
            JSONObject jsonObject = new JSONObject(tmp);
            System.out.println(jsonObject.get("Data"));
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}



