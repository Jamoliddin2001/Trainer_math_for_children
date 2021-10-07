package com.example.braintrainer;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewTimer;
    private TextView textViewScore;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private ArrayList<TextView> options = new ArrayList<>();

    private String question;
    private int countOfQuestions = 1;
    private int countRightAnswers = 0;
    private int wrongAnswer=0;
    private int rightAnswer;
    private int rightAnswerPosition;
    private boolean isPositive;
    private int min = 5;
    private int max = 30;
    private int seconds;
    private boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewScore = findViewById(R.id.textViewScore);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        options.add(textView1);
        options.add(textView2);
        options.add(textView3);
        options.add(textView4);

        textViewScore.setText("Ответов: " + Integer.toString(countRightAnswers));
        playNext();

        CountDownTimer timer=new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long l) {
                if(l<=6000){
                    textViewTimer.setTextColor(Color.RED);
                    textViewTimer.setTextSize(30);
                }
                textViewTimer.setText(getTime(l));
            }

            @Override
            public void onFinish() {
                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int max=preferences.getInt("max",0);
                if(countRightAnswers >=max && wrongAnswer<30){
                    preferences.edit().putInt("max",countRightAnswers).apply();
                }
                gameOver=true;
                Intent intent=new Intent(MainActivity.this,Score.class);
                intent.putExtra("result",countRightAnswers);
                intent.putExtra("wrongAnswer",wrongAnswer);
                startActivity(intent);
            }
        };
        timer.start();


    }

    private void playNext() {
        generateQuestion();

        textView1.setTextColor(Color.WHITE);
        textView2.setTextColor(Color.WHITE);
        textView3.setTextColor(Color.WHITE);
        textView4.setTextColor(Color.WHITE);

        for (int i = 0; i < options.size(); i++) {
            if (i == rightAnswerPosition) {
                options.get(i).setText(Integer.toString(rightAnswer));
            } else {
                options.get(i).setText(Integer.toString(generateWrongAnswer()));
            }
        }
    }

    private void generateQuestion() {
        int a = (int) (Math.random() * (max - min) + 1 + min);
        int b = (int) (Math.random() * (max - min) + 1 + min);
        int mark = (int) (Math.random() * 2);
        isPositive = mark == 1;
        if (isPositive) {
            rightAnswer = a + b;
            question = String.format("%s + %s", a, b);
        } else {
            rightAnswer = a - b;
            question = String.format("%s - %s", a, b);
        }

        textViewQuestion.setText(question);

        rightAnswerPosition = (int) (Math.random() * 4);

    }

    private int generateWrongAnswer() {
        int result;
        do {
            result = (int) (Math.random() * max * 2 + 1) - (max - min);
        } while (result == rightAnswer);
        return result;
    }


    private String getTime(long millis) {
        seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    public void onClickAnswer(View view) {
        if (!gameOver) {
            TextView textView = (TextView) view;
            String answer = textView.getText().toString();
            if (answer == Integer.toString(rightAnswer)) {
                countRightAnswers++;
                textViewScore.setText("Ответов: " + Integer.toString(countRightAnswers));
                playNext();
            } else {
                wrongAnswer++;
                textView.setTextColor(Color.RED);
            }
        }

    }

    /*private void Time(int timeCount) {

        new CountDownTimer(timeCount, 1000) {
            @Override
            public void onTick(long l) {
                if(timeCount<=5000)textViewTimer.setTextColor(Color.RED);
                textViewTimer.setText(getTime(l));
            }

            @Override
            public void onFinish() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int max = preferences.getInt("max", 0);
                if (countRightAnswers >= max) {
                    preferences.edit().putInt("max", countRightAnswers).apply();
                }
                gameOver = true;
                Intent intent = new Intent(MainActivity.this, Score.class);
                intent.putExtra("result", countRightAnswers);
                startActivity(intent);
            }
        }.start();
    }*/
}