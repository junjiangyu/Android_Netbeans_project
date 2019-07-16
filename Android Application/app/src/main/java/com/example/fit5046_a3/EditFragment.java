package com.example.fit5046_a3;

import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditFragment extends android.support.v4.app.Fragment {
    StepsDatabase db = null;
    Steps steps;
    TextView txt;
     int id;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_edit,null);
        txt= view.findViewById(R.id.stepsinput);
        Button btn = view.findViewById(R.id.btn_stepedit);

        db = Room.databaseBuilder(getActivity(),
                StepsDatabase.class, "StepsDatabase")
                .fallbackToDestructiveMigration()
                .build();

        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
        id = pref.getInt("ID",0);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDatabase update = new UpdateDatabase();
                 update.execute();
                getFragmentManager().beginTransaction().replace(R.id.mainfrag, new StepsFragment()).commit();
            }});

        return view;

    }

    private class UpdateDatabase extends AsyncTask<Void, Void, String> {
        @Override protected String doInBackground(Void... params) {

            Steps steps = db.StepsDao().findByID(id);
            steps.setSteps(Integer.parseInt(txt.getText().toString()));
            db.StepsDao().updateSteps(steps);
             return "";
        }
        @Override
        protected void onPostExecute(String details) {

        }
    }
}




