package com.example.fit5046_a3;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Steps {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "stepstaken")
    public int steps;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @ColumnInfo(name = "time")
    public String time;


    public Steps(int steps, String time) {
        this.steps = steps;
        this.time = time;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getId() {
        return uid;
    }

 }