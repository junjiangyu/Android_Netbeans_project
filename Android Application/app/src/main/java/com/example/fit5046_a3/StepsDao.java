package com.example.fit5046_a3;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsDao {
    @Query("SELECT * FROM Steps")
    List<Steps> getAll();
    @Query("SELECT * FROM Steps WHERE stepstaken LIKE :first")
    Steps findBy(String first);

    @Query("SELECT * FROM Steps WHERE uid = :id")
    Steps findByID (int id);

    @Insert
    void insertAll(Steps... steps);
    @Insert
    long insert(Steps steps);
    @Delete
    void delete(Steps steps);
    @Update(onConflict = REPLACE)
    public void updateSteps(Steps... steps);
    @Query("DELETE FROM Steps")
    void deleteAll();
}
