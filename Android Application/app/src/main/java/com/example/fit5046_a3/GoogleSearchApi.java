package com.example.fit5046_a3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class GoogleSearchApi {
    private static final String API_KEY = "AIzaSyC_7K6F9L4omgbD2HrR8wlkQf8C2llc_64";
    private static final String SEARCH_ID_cx = "004521765094562197487:ihxceny7mhy";

    public static String search(String keyword, String[] params, String[] values) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                    API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

    public static Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    public static String getLongLat(String address) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        try {
            url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyCr1kUerZzHxBDzHzoj-zzkaXLm9JKnsHY");
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }


    public static String getParks(String lat, String lng) {
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        try {
            url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=5000&types=park&key=AIzaSyCr1kUerZzHxBDzHzoj-zzkaXLm9JKnsHY");
            System.out.println(url);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }

}
