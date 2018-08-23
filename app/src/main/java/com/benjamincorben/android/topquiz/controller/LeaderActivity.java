package com.benjamincorben.android.topquiz.controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.benjamincorben.android.topquiz.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LeaderActivity extends AppCompatActivity {

    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    public static final String theNAME = "KEY_1";
    public static final String theSCORE = "KEY_2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader2);
        SharedPreferences mPreferences = getSharedPreferences("game_data", MODE_PRIVATE);
        String existingNames = (mPreferences.getString(theNAME, ""));
        String existingScores = (mPreferences.getString(theSCORE, ""));

        String[] namesArray = existingNames.split(", ");
        String[] scoresStringArray = existingScores.split(", ");
        ArrayList<Integer> scoresArray = new ArrayList();

        for (int j = 0; j < scoresStringArray.length; j++) {
            int foo = Integer.parseInt(scoresStringArray[j]);
            scoresArray.add(foo);
        }
        HashMap<String, Integer> scoresMap = new HashMap<>();
        for (int i = 0; i < namesArray.length; i++)
            scoresMap.put(namesArray[i], scoresArray.get(i));

        Log.d("unsorted", scoresMap.entrySet().toString());
        Toast.makeText(this, "unsorted" + scoresMap.entrySet().toString(), Toast.LENGTH_SHORT).show();

        LinkedList list = new LinkedList(scoresMap.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object mapEntry1, Object mapEntry2) {
                return ((Comparable) ((Map.Entry) (mapEntry1)).getValue())
                        .compareTo(((Map.Entry) (mapEntry2)).getValue());
            }

        });
        Log.d("sorted", scoresMap.entrySet().toString());
        Toast.makeText(this, "sorted" + scoresMap.entrySet().toString(), Toast.LENGTH_SHORT).show();
    }
}

