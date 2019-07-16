package com.example.fit5046_a3;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TrackerFragment extends android.support.v4.app.Fragment {

    int consumedvalue = 0;
    int id = 0;
    int totalcalorieburned = 0;

    TextView consumed;
    TextView totalburned;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_tracker,null);
        TextView goal =  view.findViewById(R.id.Goal);
        TextView tvsteps = view.findViewById(R.id.totalSteps);
        consumed = view.findViewById(R.id.totalconsumed);
        totalburned = view.findViewById(R.id.totalburned);
        Button btn = view.findViewById(R.id.btn_report);


        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
        final String goalstr = pref.getString("CalorieGoal","");
        final int steps = pref.getInt("TotalSteps",0);

        id  = Integer.parseInt(pref.getString("userId",""));

        goal.setText(goalstr);
        tvsteps.setText(String.valueOf(steps));
        GetCalorieConsumed get = new GetCalorieConsumed();
        get.execute();


        GetCalorieBurned burned = new GetCalorieBurned();
        burned.execute(steps);

        //insert into report
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject report = new JSONObject();
                JSONObject user = new JSONObject();
                //get current date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                Date date = new Date(System.currentTimeMillis());
                String datestr = simpleDateFormat.format(date);
                try {
                    user.put("id",id);
                    report.put("userId",user);
                    report.put("date",datestr);
                    report.put("caloriesGoal",goalstr);
                    report.put("totalCaloriesBurned",totalcalorieburned);
                    report.put("totalSteps",String.valueOf(steps));
                    report.put("totalCaloriesConsumed",consumedvalue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                InsertReport insert = new InsertReport();
                insert.execute(report.toString());
            }});
        return view;
    }

    private class GetCalorieConsumed extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String datestr = simpleDateFormat.format(date);
            consumedvalue = RestClient.findByIdDate(id,datestr);
            consumed.setText(String.valueOf(consumedvalue));
            return "";
        }
        @Override
        protected void onPostExecute(String details) {
        }
    }


    private class GetCalorieBurned extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {

            int calorierest = RestClient.findCaloriesPerDay(id);
            int caloriestep = RestClient.findCaloriesPerStep(id)*params[0];
            totalcalorieburned = calorierest+caloriestep;
            totalburned.setText(String.valueOf(totalcalorieburned));
            return "";
        }
        @Override
        protected void onPostExecute(String details) {
        }
    }



    private class InsertReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            RestClient.createReport(params[0]);
            return "";
        }
        @Override
        protected void onPostExecute(String details) {
        }
    }



}






