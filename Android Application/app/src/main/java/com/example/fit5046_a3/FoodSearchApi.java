package com.example.fit5046_a3;


import android.util.Log;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.net.URL;

public class FoodSearchApi {

    private static final String APP_ID = "1703c37a";
    private static final String APP_KEY = "79e724d19a42c935b0b2b46ca043a328";

    public static String food(String food) {
        String textResult = "";
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://api.edamam.com/api/food-database/parser?ingr=" + food +
                    "&app_id="+APP_ID+"&app_key="+APP_KEY);
            Log.i("url",url.toString());
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
}
