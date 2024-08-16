package com.example.fy_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    TextView signup_btn, login_btn, google_btn;

    TextView title1,title2;

    EditText user_email,user_password, user_fullname;


    FirebaseAuth firebaseAuth;
    DatabaseReference user_ref;

    final LoadingDialog loadingDialog = new LoadingDialog(SignupActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);
        google_btn = findViewById(R.id.google_sign_btn);
        user_fullname = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        title1 = findViewById(R.id.tv1);
        title2 = findViewById(R.id.tv2);

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user_ref = FirebaseDatabase.getInstance().getReference().child("Users");


        //login click
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(signup_btn,"next_btn");
                pairs[1] = new Pair<View,String>(login_btn,"prev_btn");
                pairs[2] = new Pair<View,String>(user_email,"email_et");
                pairs[3] = new Pair<View,String>(user_password,"password_et");
                pairs[4] = new Pair<View,String>(google_btn,"google_btn");
                pairs[5] = new Pair<View,String>(title1,"title1");
                pairs[6] = new Pair<View,String>(title2,"title2");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignupActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });


        //signup click
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!validateFullName() | !validateEmail() | !validatePassword()){
                    //Toast.makeText(getApplicationContext(),"valid",Toast.LENGTH_SHORT).show();
                    return;
                }

                //dialog start
                loadingDialog.startLoadingDialog();


                firebaseAuth.createUserWithEmailAndPassword(user_email.getText().toString(),user_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //adding data to firebase
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("fullname",user_fullname.getText().toString());
                            hashMap.put("email",user_email.getText().toString());
                            hashMap.put("uid",firebaseAuth.getCurrentUser().getUid());
                            hashMap.put("user_type","user");

                            user_ref.child(firebaseAuth.getCurrentUser().getUid()).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"Sign Up Successfull",Toast.LENGTH_SHORT).show();
                                        //dialog stop
                                        loadingDialog.dismissDialog();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        //dialog stop
                                        loadingDialog.dismissDialog();
                                    }
                                }
                            });



                        }
                        else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            //dialog stop
                            loadingDialog.dismissDialog();
                        }
                    }
                });


            }
        });
    }


    private boolean validateFullName() {
        String val = user_fullname.getText().toString().trim();

        if (val.isEmpty()) {
            user_fullname.setError("Name cannot be empty");
            return false;
        } else {
            user_fullname.setError(null);
            return true;
        }
    }
    private boolean validateEmail() {
        String val = user_email.getText().toString().trim();

        if (val.isEmpty()) {
            user_email.setError("Email cannot be empty");
            return false;
        } else {
            user_email.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String val = user_password.getText().toString().trim();

        if (val.isEmpty()) {
            user_password.setError("Password cannot be empty");
            return false;
        } else {
            user_password.setError(null);
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }
}