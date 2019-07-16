package com.example.fit5046_a3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MainpageFragment extends android.support.v4.app.Fragment {

    String userinfo;
    SharedPreferences pref;
    TextView goalset;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view=inflater.inflate(R.layout.fragment_mainpage,null);
        final EditText et = view.findViewById(R.id.edittextgoal);
        Button btn1 = view.findViewById(R.id.setgoal);
        TextView welcome =  view.findViewById(R.id.welcome);
        goalset = view.findViewById(R.id.goal);
        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        String goalstr = pref.getString("CalorieGoal","");

        goalset.setText("Your Calorie Goal for today is: "+goalstr);



        //get value from intent
        userinfo = getActivity().getIntent().getStringExtra("userinfo");
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(userinfo);
            String firstname = jsonArray.getJSONObject(0).getJSONObject("userId").getString("name");
            String userid = jsonArray.getJSONObject(0).getJSONObject("userId").getString("id");
            String address = jsonArray.getJSONObject(0).getJSONObject("userId").getString("address");
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("userId",userid);
            editor.putString("address",address);
            editor.commit();
            welcome.setText("Welcome " + firstname);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String caloriegoal = et.getText().toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("CalorieGoal",caloriegoal);
                editor.commit();
                goalset.setText("Your Calorie Goal for today is: "+caloriegoal);
            }});






        return view;

    }


}
