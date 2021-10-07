package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class Score extends AppCompatActivity {

    private TextView textViewScoreNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        textViewScoreNow=findViewById(R.id.textViewScoreNow);
        Intent intent=getIntent();
        if(intent != null && intent.hasExtra("result")){
            int result=intent.getIntExtra("result",0);
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(Score.this);
            int max=preferences.getInt("max",0);
            int wrongAnswer=intent.getIntExtra("wrongAnswer",0);
            String score = String.format("Ваш результат: %s\n( Ошибок: %s )\n Лучший результат: %s",result,wrongAnswer,max);
            textViewScoreNow.setText(score);
        }
    }

    public void OnClickStartNewGame(View view) {
        Intent intent=new Intent(Score.this,MainActivity.class);
        startActivity(intent);
    }
}