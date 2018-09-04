package com.benjamincorben.android.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.benjamincorben.android.topquiz.R;
import com.benjamincorben.android.topquiz.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;
    private Button mLeaderButton;
    private SharedPreferences mSharedPreferences;

    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    public static final String theNAME = "KEY_1";
    public static final String theSCORE = "KEY_2";
    private SharedPreferences mPreferences;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GAME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            String alreadyExistingScores = mPreferences.getString(theSCORE, "");
            if (alreadyExistingScores.equals("")) {
                alreadyExistingScores += score;
            } else {
                alreadyExistingScores += ", " + score;
            }
            mPreferences.edit().putString(theSCORE, alreadyExistingScores).apply();

            String name = data.getStringExtra(GameActivity.BUNDLE_EXTRA_NAME);
            String alreadyExistingNames = mPreferences.getString(theNAME, "");
            if (alreadyExistingNames.equals("")) {
                alreadyExistingNames += name;
            } else {
                alreadyExistingNames += ", " + name;
            }
            mPreferences.edit().putString(theNAME, alreadyExistingNames).apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mLeaderButton = findViewById(R.id.activity_main_leaderboard);
        mUser = new User();
        mPlayButton.setEnabled(false);
        mLeaderButton.setEnabled(false);
        mPreferences = getSharedPreferences("game_data", MODE_PRIVATE);


        mLeaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent leaderActivityIntent = new Intent(MainActivity.this, LeaderActivity.class);
                startActivity(leaderActivityIntent);
            }
        });

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPlayButton.setEnabled(charSequence.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                gameActivityIntent.putExtra("name", mNameInput.getText().toString());
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
                
            }
        });

        mLeaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent leaderActivityIntent = new Intent(MainActivity.this, LeaderActivity.class);
                startActivity(leaderActivityIntent);
            }
        });


    }

}




