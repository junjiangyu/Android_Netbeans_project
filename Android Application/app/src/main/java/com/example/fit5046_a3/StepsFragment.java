package com.example.fit5046_a3;

import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.fit5046_a3.R.layout.fragment_steps;

public class StepsFragment extends android.support.v4.app.Fragment{

    StepsDatabase db = null;
    SimpleAdapter myListAdapter;
    List<HashMap<String, String>> unitListArray;
    HashMap<String,String> map = new HashMap<String,String>();
    ListView lv;
    String time;
    int totalSteps;
    SharedPreferences pref;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

         View view=inflater.inflate(fragment_steps,null);
         final EditText input = view.findViewById(R.id.stepsinput);
         Button btn = view.findViewById(R.id.btn_step);
         lv = view.findViewById(R.id.listview);
         Button delbtn = view.findViewById(R.id.btn_delete);
         pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);

         db = Room.databaseBuilder(getActivity(),
               StepsDatabase.class, "StepsDatabase")
                .fallbackToDestructiveMigration()
                .build();


        ReadDatabase readDatabase = new ReadDatabase();
        readDatabase.execute();


        delbtn.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                GetId getId = new GetId();
                getId.execute(position);
                getFragmentManager().beginTransaction().replace(R.id.mainfrag, new EditFragment()).commit();

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            //including onClick() method
            public void onClick(View v) {
                //get current time
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                time = simpleDateFormat.format(date);
                int steps = Integer.parseInt(input.getText().toString());
                InsertDatabase insertDatabase = new InsertDatabase();
                insertDatabase.execute(steps);
            }
        });




        return view;

    }


    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            totalSteps = 0;
            List<Steps> users = db.StepsDao().getAll();
            unitListArray = new
                    ArrayList<HashMap<String, String>>();
            if (!(users.isEmpty() || users == null) ){
                String allUsers = "";

                for (Steps temp : users) {
                    map = new HashMap<String,String>();
                    map.put("Time", String.valueOf(temp.getTime()));
                    map.put("STEPS", String.valueOf(temp.getSteps()));
                    unitListArray.add(map);
                    totalSteps = totalSteps + temp.getSteps();
                }
                System.out.println("TotalSteps"+totalSteps);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("TotalSteps",totalSteps);
                editor.commit();



                return "suceed";
            }
            else
                return "";
        }

        protected void onPostExecute (String account){
            String[] colHEAD = new String[] {"Time","STEPS"};
            int[] dataCell = new int[] {R.id.UnitCode,R.id.UnitName};
            myListAdapter = new SimpleAdapter(getActivity(), unitListArray,R.layout.list_view ,colHEAD,dataCell);
            lv.setAdapter(myListAdapter);
        }
}

    private class InsertDatabase extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
                    Steps steps = new Steps(params[0],time);
                    long id = db.StepsDao().insert(steps);
                    ReadDatabase readDatabase = new ReadDatabase();
                    readDatabase.execute();
                    return ("");
        }
        @Override
        protected void onPostExecute(String details) {

        }
    }


    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.StepsDao().deleteAll();
            //update the totalsteps value
            totalSteps = 0;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("TotalSteps",totalSteps);
            editor.commit();

            return null;
        }
        protected void onPostExecute(Void param) {
            //update the list view
            ReadDatabase readDatabase = new ReadDatabase();
            readDatabase.execute();
        }
    }



    private class GetId extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            //pase the value into edit fragment
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("ID",db.StepsDao().getAll().get(params[0]).getId());
            editor.commit();
            return "";
        }
        @Override
        protected void onPostExecute(String details) {
        }
    }






}
