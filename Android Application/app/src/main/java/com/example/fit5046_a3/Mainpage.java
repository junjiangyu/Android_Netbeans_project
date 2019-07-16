package com.example.fit5046_a3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Mainpage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);


        Toolbar toolbar = findViewById(R.id.toolbar);
        //initialize the page with fragment login
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new MainpageFragment()).commit();
        }



        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new MainpageFragment()).commit();
        } else if (id == R.id.nav_diet ){

            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new DailyDietFragment()).commit();

        } else if (id == R.id.nav_steps) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new StepsFragment()).commit();

        } else if (id == R.id.nav_tracker) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new TrackerFragment()).commit();

        }else if (id == R.id.nav_report) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new ReportFragment()).commit();
        }else if (id == R.id.nav_map) {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainfrag, new MapFragment()).commit();
        }else if (id == R.id.logout) {
            Intent myIntent = new Intent(Mainpage.this, Login.class);
            startActivity(myIntent);

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
