package com.example.fy_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String url = "https://stress-exorcist.onrender.com/predict";


    EditText thoughts ;
    TextView predict_btn , result_text;

    final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);


    String result_string;

    ImageView smily_emoji,cry_emoji,angry_emoji,sad_emoji,very_happy_emoji;
    ImageView clear_thoughts,copy_clipboard;
    TextView week_day,date_tv;
    ImageView back_btn;

    FirebaseAuth firebaseAuth ;
    DatabaseReference user_ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.back_green));


        thoughts = findViewById(R.id.thoughts);
        predict_btn = findViewById(R.id.predict_btn);
        result_text = findViewById(R.id.result_text);
        smily_emoji = findViewById(R.id.smily_emoji);
        cry_emoji = findViewById(R.id.cry_emoji);
        angry_emoji = findViewById(R.id.angry_emoji);
        sad_emoji = findViewById(R.id.sad_emoji);
        very_happy_emoji = findViewById(R.id.very_happy_emoji);
        clear_thoughts = findViewById(R.id.clear_thoughts);
        copy_clipboard = findViewById(R.id.copy_clipboard);
        week_day = findViewById(R.id.week_day);
        week_day.setText(CurrentWeekDay());
        date_tv = findViewById(R.id.date_tv);
        date_tv.setText(CurrentDayMonth());
        back_btn = findViewById(R.id.back_btn);



        //firebase loading data
        firebaseAuth = FirebaseAuth.getInstance();
        user_ref = FirebaseDatabase.getInstance().getReference().child("Users");
        user_ref.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("current_diary").exists()){
                    thoughts.setText(snapshot.child("current_diary").getValue(String.class));
                }
                else {
                    thoughts.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        predict_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //API request
                loadingDialog.startLoadingDialog();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("result");
                            if (data.equals("0")){
                                result_string = "0";
                            }
                            else{
                                result_string = "1";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("current_diary",thoughts.getText().toString());
                        hashMap.put("current_mood",result_string);
                        user_ref.child(firebaseAuth.getCurrentUser().getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    Intent intent = new Intent(getApplicationContext(),DiaryResultActivity.class);
                                    intent.putExtra("result",result_string);
                                    startActivity(intent);
                                    loadingDialog.dismissDialog();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    loadingDialog.dismissDialog();
                                }
                            }
                        });


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                        loadingDialog.dismissDialog();
                    }
                }){
                    @Override
                    protected Map<String,String> getParams(){


                        Map<String,String> params = new HashMap<String,String>();
                        params.put("review",ReplaceEmoji(thoughts.getText().toString()));
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);

            }
        });


        smily_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thoughts.append("☺️");
            }
        });
        cry_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thoughts.append("\uD83D\uDE22");
            }
        });
        angry_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thoughts.append("\uD83D\uDE21");
            }
        });
        sad_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thoughts.append("\uD83D\uDE14");
            }
        });
        very_happy_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thoughts.append("\uD83E\uDD70");
            }
        });

        clear_thoughts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thoughts.setText("");
            }
        });
        copy_clipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("nonsense_data",
                        thoughts.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    public String ReplaceEmoji(String str){
        String replacedStr = str.replaceAll("☺️", " happy");
        replacedStr = replacedStr.replaceAll("\uD83D\uDE21", " angry");
        replacedStr = replacedStr.replaceAll("\uD83D\uDE22", " cry");
        replacedStr = replacedStr.replaceAll("\uD83D\uDE14", " sad");
        replacedStr = replacedStr.replaceAll("\uD83E\uDD70", " very happy");
        return replacedStr;
    }
    public String CurrentWeekDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String dayStr = "Sunday";
        switch (day) {
            case Calendar.SUNDAY:
                dayStr = "Sunday";
                break;
            case Calendar.MONDAY:
                dayStr = "Monday";
                break;
            case Calendar.TUESDAY:
                dayStr = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayStr = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayStr = "Thrusday";
                break;
            case Calendar.FRIDAY:
                dayStr = "Friday";
                break;
            default:
                dayStr = "Saturday";
                break;
        }
        return dayStr;
    }

    public String CurrentDayMonth(){
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        SimpleDateFormat day_date = new SimpleDateFormat("dd");
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return month_name+" "+String.valueOf(day);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }
}