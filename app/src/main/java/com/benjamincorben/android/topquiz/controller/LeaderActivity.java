package com.benjamincorben.android.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.Element;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benjamincorben.android.topquiz.R;

import java.security.Key;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class LeaderActivity extends AppCompatActivity {

    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    public static final String theNAME = "KEY_1";
    public static final String theSCORE = "KEY_2";
    private Button mScoreButton;
    private Button mAlphaButton;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader2);
        SharedPreferences mPreferences = getSharedPreferences("game_data", MODE_PRIVATE);
        String existingNames = (mPreferences.getString(theNAME, ""));
        String existingScores = (mPreferences.getString(theSCORE, ""));
        mScoreButton = findViewById(R.id.setByScoreButton);
        mAlphaButton = findViewById(R.id.setByAlphabetButton);

        String[] namesArray = existingNames.split(", ");
        String[] scoresStringArray = existingScores.split(", ");
        ArrayList<Integer> scoresArray = new ArrayList<>();

        for (String aScoresStringArray : scoresStringArray) {
            int foo = Integer.parseInt(aScoresStringArray);
            scoresArray.add(foo);
        }
        HashMap<String, Integer> scoresMap = new HashMap<>();
        for (int i = 0; i < scoresArray.size(); i++) {
            scoresMap.put(namesArray[i], scoresArray.get(i));
        }

        final LinkedList list = new LinkedList(scoresMap.entrySet());
        // Defined Custom Comparator here

        Collections.sort(list, new Comparator() {
            public int compare(Object mapEntry1, Object mapEntry2) {
                return ((Comparable) ((Map.Entry) (mapEntry1)).getValue())
                        .compareTo(((Map.Entry) (mapEntry2)).getValue());
            }

        });

        Collections.reverse(list);

        mTextView = findViewById(R.id.textViewName);

        for (int i = 0; i < 5; i++) {
            mTextView.append(list.get(i) + "\n\n");

        }

        mScoreButton.setEnabled(false);
        mScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mScoreButton.setEnabled(false);
                mAlphaButton.setEnabled(true);
            }
        });

        mAlphaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(list, new Comparator() {
                    public int compare(Object mapEntry1, Object mapEntry2) {
                        return ((Comparable) ((Map.Entry) (mapEntry1)).getKey())
                                .compareTo(((Map.Entry) (mapEntry2)).getKey());
                    }
                });


                mTextView.setText("");
                for (
                        int i = 0;
                        i < 5; i++)

                {

                    mTextView.append(list.get(i) + "\n\n");
                }
                mScoreButton.setEnabled(true);
                mAlphaButton.setEnabled(false);
            }
        });


    }
}


