package com.example.fit5046_a3;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.net.wifi.WifiConfiguration.Status.strings;


public class DailyDietFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener {

    Spinner catspinner;
    String category;
    Spinner foodspinner;
    List<String> list;
    String foodname;
    Food food;
    TextView googletxt;
    ImageView googleimg;
    private String url;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       //Allow main thread to do the network search
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View view=inflater.inflate(R.layout.fragment_dailydiet,null);
        list = new ArrayList<String>();
        catspinner = view.findViewById(R.id.foodcategory);
        foodspinner = view.findViewById(R.id.food);
        final EditText tv = view.findViewById(R.id.newfood);
        Button btn = view.findViewById(R.id.submitbtn);
        Button add = view.findViewById(R.id.addconsumption);
        googletxt = view.findViewById(R.id.googletxt);
        googleimg = view.findViewById(R.id.foodimage);
        String url;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodname = tv.getText().toString();
                FoodAsyncTask asyncTask=new FoodAsyncTask();
                asyncTask.execute();

            }});


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodname = tv.getText().toString();
                AddToConsumption asyncTask=new AddToConsumption();
                asyncTask.execute();

            }});

       //set category adapter
        ArrayAdapter<CharSequence> dataAdapter =ArrayAdapter.createFromResource(getActivity(), R.array.food_category,android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catspinner.setAdapter(dataAdapter);
        catspinner.setOnItemSelectedListener(this);

        //set food onclick listener, food adapter determine by the category onclick listener
        foodspinner.setOnItemSelectedListener(this);
        return view;

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.foodcategory)
        {
            list.clear();
            //get the new list for the food spinner
            category = catspinner.getSelectedItem().toString();
            CategoryAsyncTask asyncTask=new CategoryAsyncTask();
            asyncTask.execute();
        }
        else if(parent.getId() == R.id.food)
        {
          GoogleSearch google = new GoogleSearch();
          google.execute();

            ImageSearch img = new ImageSearch();
            img.execute();
    }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    private class CategoryAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.findByCategory(category);
        }
        @Override
        protected void onPostExecute (String response){
            try {
                JSONArray jsonArray = new JSONArray(response);
                ArrayAdapter<String> foodadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,list);
                foodadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                if (response.equals("[]")){
                   //If got no result, not show anything inside spinner
                    foodspinner.setAdapter(foodadapter);
                }
                else {
                    for (int i = 0; i< jsonArray.length();i++)
                    {
                        list.add(jsonArray.getJSONObject(i).getString("name"));
                    }

                    foodspinner.setAdapter(foodadapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private class FoodAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
           return FoodSearchApi.food(foodname);
        }
        @Override
        protected void onPostExecute (String response){
            try {
                JSONObject json = new JSONObject(response);
                String calorieAmount = json.getJSONArray("hints").getJSONObject(0).getJSONObject("food").getJSONObject("nutrients").getString("ENERC_KCAL");
                String fat = json.getJSONArray("hints").getJSONObject(0).getJSONObject("food").getJSONObject("nutrients").getString("FAT");
                //convert to integer
                int calorie = Math.round(Float.parseFloat(calorieAmount));
                int fatint = Math.round(Float.parseFloat(fat));
                food = new Food(foodname,category,String.valueOf(calorie),"1","100g",String.valueOf(fatint));
                //post them to database
                PostNewFood post=new PostNewFood();
                post.execute();
                list.add(foodname);
            }  catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ImageSearch extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params) {
         return GoogleSearchApi.search(foodspinner.getSelectedItem().toString(), new String[]{"num","searchType"}, new String[]{"1","image"});
        }
        @Override
        protected void onPostExecute (String response){

            try {
                JSONObject json = new JSONObject(response);

                url = json.getJSONArray("items").getJSONObject(0).getString("link");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            googleimg.setImageBitmap(GoogleSearchApi.getImageBitmap(url));
        }
    }


    private class GoogleSearch extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params) {
            String food = foodspinner.getSelectedItem().toString();
            if (food.equals("")) {
                return "";
            } else {
                return GoogleSearchApi.search(food, new String[]{"num"}, new
                        String[]{"1"});
            }
        }
        @Override
        protected void onPostExecute (String response){
            Log.i("googleresponse", response);

            try {
                JSONObject json = new JSONObject(response);
                String result = json.getJSONArray("items").getJSONObject(0).getString("snippet");
                googletxt.setText(result);

                 url = json.getJSONArray("items").getJSONObject(0).getJSONObject("pagemap").getJSONArray("cse_image")
                        .getJSONObject(0).getString("src");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            googleimg.setImageBitmap(GoogleSearchApi.getImageBitmap(url));
        }
    }


    private class PostNewFood extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            RestClient.createFood(food);
            return ("New Food has been added successfully!");
        }
        @Override
        protected void onPostExecute (String response){

        }
    }

    private class AddToConsumption extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){

            addIntoConsumption();
            return ("New consumption has been added successfully!");
        }
        @Override
        protected void onPostExecute (String response){

        }
    }



    public void addIntoConsumption(){
        String userinfo = getActivity().getIntent().getStringExtra("userinfo");
        String email = "";
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(userinfo);
            email = jsonArray.getJSONObject(0).getJSONObject("userId").getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String userJsonArray = RestClient.email(email);
        String foodJsonArray = RestClient.findFood(foodspinner.getSelectedItem().toString());

        JSONObject userJsonObject = null;
        JSONObject foodJsonObject = null;

        try {
            JSONArray jsonArrayuser = new JSONArray(userJsonArray);
            JSONArray jsonArrayfood = new JSONArray(foodJsonArray);

            userJsonObject = jsonArrayuser.getJSONObject(0);
            foodJsonObject = jsonArrayfood.getJSONObject(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

       JSONObject consumption = new JSONObject();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date date = new Date(System.currentTimeMillis());
            String datestr = simpleDateFormat.format(date);
            consumption.put("foodId",foodJsonObject);
            consumption.put("userId",userJsonObject);
            consumption.put("date",datestr);
            consumption.put("quantity","1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

       RestClient.createConsumption(consumption.toString());
    }

}

