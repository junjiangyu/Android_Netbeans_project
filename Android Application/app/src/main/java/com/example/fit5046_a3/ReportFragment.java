package com.example.fit5046_a3;


import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportFragment extends android.support.v4.app.Fragment {


    String date;
    PieChart pieChart;
    BarChart barChart;
    int id;
    String startdate = "";
    String enddate = "";
    int goal;

        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_report,null);
        Button btn = view.findViewById(R.id.btn_pickdate);
        Button startbtn = view.findViewById(R.id.btn_pickstartdate);
        Button endbtn = view.findViewById(R.id.btn_pickenddate);
        pieChart = view.findViewById(R.id.piechart);
        barChart = view.findViewById(R.id.barchart);




        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
        id  = Integer.parseInt(pref.getString("userId",""));
        goal = Integer.parseInt(pref.getString("CalorieGoal",""));


        btn.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                showDatePickDlg();

            }});

        startbtn.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
             pickStartDate();
            }});

        endbtn.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                pickEndDate();
            }});

        return view;

    }

    protected void showDatePickDlg(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Becasue the month start at 0, so we need to add 1 into the right
                date = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                GetByDate dataAsync = new GetByDate();
                dataAsync.execute(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH +1), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    protected void pickStartDate(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startdate = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH +1), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    protected void pickEndDate(){
        if (startdate.equals("")){
            Toast.makeText(getActivity(),
                   "Pick Start Date First", Toast.LENGTH_SHORT).show();
        }
        else{
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                enddate = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date st = sdf.parse(startdate);
                    Date et = sdf.parse(enddate);
                    if (st.before(et)){
                        Toast.makeText(getActivity(),
                                "Succeed!", Toast.LENGTH_SHORT).show();
                        FindInRange find = new FindInRange();
                        find.execute();
                    }
                    else {
                        Toast.makeText(getActivity(),
                                "Select Again!", Toast.LENGTH_SHORT).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH +1), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    }

    private class GetByDate extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.findReportByIdAndDate(id,params[0]);
        }
        @Override
        protected void onPostExecute(String details) {
            try {
                JSONArray jsonArray = new JSONArray(details);
                int cBurned = Integer.parseInt(jsonArray.getJSONObject(0).getString("totalCaloriesBurned"));
                int cConsumed = Integer.parseInt(jsonArray.getJSONObject(0).getString("totalCaloriesConsumed"));
                int cCRemaining = goal - cConsumed;
                if (cCRemaining<0){
                    cCRemaining = 0;
                }
                createPieChart(cBurned,cConsumed,cCRemaining);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void createPieChart(int cBurned, int cConsumed, int remaining){
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(cBurned,"Calorie Burned"));
        strings.add(new PieEntry(cConsumed,"Calorie Consumed"));
        strings.add(new PieEntry(remaining,"Remaining Calorie"));
        //set data
        PieDataSet dataSet = new PieDataSet(strings,"");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        dataSet.setValueTextSize(15);
        dataSet.setFormSize(20);

        //set description of chart into null
        Description description = new Description();
        description.setText("");


        pieChart.setDescription(description);
        pieChart.setData(pieData);
        pieChart.invalidate();

        //set the central circle
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(0f);
    }



    public void createBarChart(String details){
      if (details.equals("[]")){
          Toast.makeText(getActivity(),
                  "No Value insde pick Range, Select Again plz!", Toast.LENGTH_SHORT).show();
      }
      else {
        ArrayList<BarEntry> yVals = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        final ArrayList<String> date = new ArrayList<>();

          try {

              JSONArray jsonArray = new JSONArray(details);

              for (int i=0;i<jsonArray.length();i++){
                  yVals.add(new BarEntry(i+1, Float.parseFloat(jsonArray.getJSONObject(i).getString("CaloriesConsumed"))));
                  yVals2.add(new BarEntry(i+1, Float.parseFloat(jsonArray.getJSONObject(i).getString("CaloriesBurned"))));
                  date.add(jsonArray.getJSONObject(i).getString("Date"));
              }


        BarDataSet set = new BarDataSet(yVals,"Calorie Consumed");
        set.setColors(Color.GREEN);
        set.setDrawValues(true);
        set.setValueTextSize(10);

        BarDataSet set1 = new BarDataSet(yVals2,"Calorie Burned");
        set1.setColors(Color.YELLOW);
        set1.setValueTextSize(10);

        BarData data = new BarData(set,set1);
        data.setBarWidth(0.1f);
        barChart.setData(data);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));
        xAxis.setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.getAxisLeft().setAxisMinimum(0);


        //not showing the value on the top and right
        barChart.getAxisRight().setEnabled(false);

        barChart.groupBars(0,0.6f,0.08f);
        barChart.invalidate();
    }   catch (JSONException e) {
            e.printStackTrace();
        }
      }
        }


    private class FindInRange extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
              return RestClient.findInRange(id,startdate,enddate);
        }
        @Override
        protected void onPostExecute(String details) {
            createBarChart(details);
        }
    }


}




