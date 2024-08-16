package com.example.fy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TestResultActivity extends AppCompatActivity {

    TextView user_score_tv, test_result, test_score_tv, exit_btn;


    int user_score;
    double score_percent;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        user_score = getIntent().getIntExtra("user_score",0);
        user_score_tv = findViewById(R.id.user_score_tv);
        test_result = findViewById(R.id.test_result);
        test_score_tv = findViewById(R.id.test_score);
        exit_btn = findViewById(R.id.exit_btn);
        progressBar = findViewById(R.id.test_progress_bar);
        score_percent = (double) Math.round(((30-user_score)/30.0)*100);
        user_score_tv.setText(score_percent+" %");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress((int)score_percent,true);
        }

        test_score_tv.setText((30-user_score)+"/30");
        if (user_score < 15){
            test_result.setText("Your mood look great today. Why stop here have a more better mental health by participating in many other activies.");
        }
        else{
            test_result.setText("Your mood dosen't look good today. Help yourself by participating in mental health improvement activies and have a great mental health.");
        }


        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TakeATestActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),TakeATestActivity.class);
        startActivity(intent);
    }
}