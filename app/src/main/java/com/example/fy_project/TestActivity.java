package com.example.fy_project;

import static com.example.fy_project.TakeATestActivity.listOfQ;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fy_project.Models.QuestionOptions;

import java.util.ArrayList;
import java.util.Collections;

public class TestActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i = 0;
    int timer_value = 20;

    TextView timer_count,total_question,question_no;
    ImageView back_btn;

    //test parameters
    int index = 0;
    ArrayList<QuestionOptions> all_questions_list;
    QuestionOptions questionOptions;

    RelativeLayout card_question, card_oA, card_oB, card_oC, card_oD;

    TextView question, oA, oB, oC, oD;

    int user_answers[] = {0,0,0,0,0,0,0,0,0,0};
    int user_score = 0;


    final LoadingDialog loadingDialog = new LoadingDialog(TestActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        int NO_OF_QUESTIONS = 10;
        Hooks();

        total_question = findViewById(R.id.total_question);
        total_question.setText("/" + String.valueOf(NO_OF_QUESTIONS));
        question_no = findViewById(R.id.question_no);
        back_btn = findViewById(R.id.back_btn);


        all_questions_list = listOfQ;
        Collections.shuffle(all_questions_list);
        questionOptions = all_questions_list.get(index);

        SetAllData();

        timer_count = findViewById(R.id.timer_count);
        timer_count.setText("0");
        mProgressBar = findViewById(R.id.progress_bar_horizontal);



        mCountDownTimer = new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timer_value = timer_value - 1;
                timer_count.setText(String.valueOf(timer_value));
                mProgressBar.setProgress(timer_value * 5);

            }

            @Override
            public void onFinish() {
                if (index != (NO_OF_QUESTIONS-1)) {
                    index++;
                    questionOptions = all_questions_list.get(index);
                    question_no.setText(String.valueOf(index+1));
                    //ChangeOptionCheck(true,true,true,true);
                    SetAllData();
                    mCountDownTimer.start();
                    timer_value = 20;
                } else {
                    //dialog start
                    loadingDialog.startLoadingDialog();
                    Intent intent = new Intent(getApplicationContext(), TestResultActivity.class);
                    //score calculation
                    for (i = 0; i < user_answers.length; i++)
                        user_score += user_answers[i];
                    intent.putExtra("user_score",user_score);
                    startActivity(intent);
                    //dialog stop
                    loadingDialog.dismissDialog();


                }

            }
        };
        mCountDownTimer.start();


        //options click
        card_oA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountDownTimer.cancel();
                user_answers[index] = questionOptions.getAnswer1();
                if (index != (NO_OF_QUESTIONS-1)) {
                    index++;
                    questionOptions = all_questions_list.get(index);
                    question_no.setText(String.valueOf(index+1));
                    SetAllData();
                    mCountDownTimer.start();
                    timer_value = 20;
                } else {
                    //dialog start
                    loadingDialog.startLoadingDialog();
                    Intent intent = new Intent(getApplicationContext(), TestResultActivity.class);
                    //score calculation
                    for (i = 0; i < user_answers.length; i++)
                        user_score += user_answers[i];
                    intent.putExtra("user_score",user_score);
                    startActivity(intent);
                    //dialog stop
                    loadingDialog.dismissDialog();
                }
            }
        });
        card_oB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountDownTimer.cancel();
                user_answers[index] = questionOptions.getAnswer2();
                if (index != (NO_OF_QUESTIONS-1)) {
                    index++;
                    questionOptions = all_questions_list.get(index);
                    question_no.setText(String.valueOf(index+1));
                    //ChangeOptionCheck(true,true,true,true);
                    SetAllData();
                    mCountDownTimer.start();
                    timer_value = 20;
                } else {
                    //dialog start
                    loadingDialog.startLoadingDialog();
                    Intent intent = new Intent(getApplicationContext(), TestResultActivity.class);
                    //score calculation
                    for (i = 0; i < user_answers.length; i++)
                        user_score += user_answers[i];
                    intent.putExtra("user_score",user_score);
                    startActivity(intent);
                    //dialog stop
                    loadingDialog.dismissDialog();


                }
            }
        });
        card_oC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountDownTimer.cancel();
                user_answers[index] = questionOptions.getAnswer3();
                if (index != (NO_OF_QUESTIONS-1)) {
                    index++;
                    questionOptions = all_questions_list.get(index);
                    question_no.setText(String.valueOf(index+1));
                    //ChangeOptionCheck(true,true,true,true);
                    SetAllData();
                    mCountDownTimer.start();
                    timer_value = 20;
                } else {
                    //dialog start
                    loadingDialog.startLoadingDialog();
                    Intent intent = new Intent(getApplicationContext(), TestResultActivity.class);
                    //score calculation
                    for (i = 0; i < user_answers.length; i++)
                        user_score += user_answers[i];
                    intent.putExtra("user_score",user_score);
                    startActivity(intent);
                    //dialog stop
                    loadingDialog.dismissDialog();


                }
            }
        });
        card_oD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountDownTimer.cancel();
                user_answers[index] = questionOptions.getAnswer4();
                if (index != (NO_OF_QUESTIONS-1)) {
                    index++;
                    questionOptions = all_questions_list.get(index);
                    question_no.setText(String.valueOf(index+1));
                    //ChangeOptionCheck(true,true,true,true);
                    SetAllData();
                    mCountDownTimer.start();
                    timer_value = 20;
                } else {
                    //dialog start
                    loadingDialog.startLoadingDialog();
                    Intent intent = new Intent(getApplicationContext(), TestResultActivity.class);
                    //score calculation
                    for (i = 0; i < user_answers.length; i++)
                        user_score += user_answers[i];
                    intent.putExtra("user_score",user_score);
                    startActivity(intent);
                    //dialog stop
                    loadingDialog.dismissDialog();

                }
            }
        });


        //back_btn
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
                alertDialogBuilder.setTitle("Quit Test!")
                        .setMessage("Are you sure , you want to quit  ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = new Intent(getApplicationContext(),TakeATestActivity.class);
                                finish();
                                startActivity(intent);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialogBuilder.create().show();
            }
        });


    }

    private void Hooks() {
        card_oA = findViewById(R.id.op_1);
        card_oB = findViewById(R.id.op_2);
        card_oC = findViewById(R.id.op_3);
        card_oD = findViewById(R.id.op_4);


        oA = findViewById(R.id.op1);
        oB = findViewById(R.id.op2);
        oC = findViewById(R.id.op3);
        oD = findViewById(R.id.op4);
        question = findViewById(R.id.test_question);

    }

    private void SetAllData() {
        question.setText(questionOptions.getQuestion());
        oA.setText(questionOptions.getoA());
        oB.setText(questionOptions.getoB());
        oC.setText(questionOptions.getoC());
        oD.setText(questionOptions.getoD());
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
        alertDialogBuilder.setTitle("Quit Test!")
                .setMessage("Are you sure , you want to quit  ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(getApplicationContext(),TakeATestActivity.class);
                        finish();
                        startActivity(intent);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.create().show();

    }
}