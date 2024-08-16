package com.example.fy_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fy_project.Models.QuestionOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TakeATestActivity extends AppCompatActivity {

    DrawerLayout drawerLayout ;
    NavigationView navigationView;
    ImageView menu_btn;
    TextView nav_user_fullname,nav_user_email;

    CardView test1,test2,test3;

    FirebaseAuth firebaseAuth;
    DatabaseReference user_ref;

    final LoadingDialog loadingDialog = new LoadingDialog(TakeATestActivity.this);

    public static ArrayList<QuestionOptions> listOfQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_atest);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(Gravity.LEFT);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        menu_btn = findViewById(R.id.menu_btn);
        nav_user_fullname = headerView.findViewById(R.id.nav_user_fullname);
        nav_user_email = headerView.findViewById(R.id.nav_user_email);

        test1 = findViewById(R.id.test1);
        test2 = findViewById(R.id.test2);
        test3 = findViewById(R.id.test3);



        firebaseAuth = FirebaseAuth.getInstance();
        user_ref = FirebaseDatabase.getInstance().getReference();


        user_ref.child("Users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("fullname").getValue(String.class);
                String useremail = snapshot.child("email").getValue(String.class);
                nav_user_fullname.setText(username);
                nav_user_email.setText(useremail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //menu btn click
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        //navigation item select
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:{
                        Intent intent_home = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent_home);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    }
                    case R.id.experts:{
                        Intent intent_experts = new Intent(getApplicationContext(),PsychiatristActivity.class);
                        startActivity(intent_experts);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    }
                    case R.id.settings:{
                        Intent intent_setting = new Intent(getApplicationContext(),SettingsActivity.class);
                        startActivity(intent_setting);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    }
                    case R.id.test:{
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    }
                    case R.id.logout:{
                        //dialog start
                        loadingDialog.startLoadingDialog();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        //dialog stop
                        loadingDialog.dismissDialog();
                        break;
                    }

                }

                return false;
            }
        });


        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TestActivity.class);
                startActivity(intent);
            }
        });
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TestActivity.class);
                startActivity(intent);
            }
        });
        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TestActivity.class);
                startActivity(intent);
            }
        });



        //static data

        listOfQ = new ArrayList<>();
        listOfQ.add(new QuestionOptions("Do you often get headache ?","Strongly Disagree","Disagree","Agree","Strongly Agree",1,0,3,2));
        listOfQ.add(new QuestionOptions("You like staying alone","Strongly Disagree","Disagree","Agree","Strongly Agree",1,0,3,2));
        listOfQ.add(new QuestionOptions("Trouble falling or staying asleep, or sleeping too much","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",1,0,3,2));
        listOfQ.add(new QuestionOptions("Little interest or pleasure in doing things","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",1,0,3,2));
        listOfQ.add(new QuestionOptions("Feeling down, depressed, or hopeless","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",1,0,3,2));
        listOfQ.add(new QuestionOptions("Feeling tired or having little energy","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",2,0,1,3));
        listOfQ.add(new QuestionOptions("Poor appetite or overeating","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",2,3,1,0));
        listOfQ.add(new QuestionOptions("Moving or speaking so slowly that other people could have noticed Or the opposite - being so fidgety or restless that you have been moving around a lot more than usual","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",3,0,1,2));
        listOfQ.add(new QuestionOptions("Thoughts that you would be better off dead, or of hurting yourself","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",2,0,1,3));
        listOfQ.add(new QuestionOptions("If you checked off any problems, how difficult have these problems made it for you at work, home, or with other people?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",2,0,1,3));
        listOfQ.add(new QuestionOptions("Feeling bad about yourself - or that you are a failure or have let yourself or your family down","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",2,3,1,0));
        listOfQ.add(new QuestionOptions("Trouble concentrating on things, such as reading the newspaper or watching television","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",2,3,1,0));
        listOfQ.add(new QuestionOptions("Do you often get headache ?","Strongly Disagree","Disagree","Agree","Strongly Agree",1,0,3,2));
        listOfQ.add(new QuestionOptions("You like staying alone","Strongly Disagree","Disagree","Agree","Strongly Agree",1,0,3,2));
        listOfQ.add(new QuestionOptions("Trouble falling or staying asleep, or sleeping too much","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Little interest or pleasure in doing things","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Feeling tired or having little energy","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Feeling bad about yourself - or that you are a failure or have let yourself or your family down","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Trouble concentrating on things, such as reading the newspaper or watching television","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Moving or speaking so slowly that other people could have noticed ","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Thoughts that you would be better off dead, or of hurting yourself","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Do you feel guilty or tearful for no reason?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Do you find yourself avoiding friends and family?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("How often have you been bothered that you have poor appetite, weight loss, or overeating over the last two weeks?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Feeling down, depressed, or hopeless","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("How often did you feel so nervous that nothing could calm you down?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("How often did you feel so restless you could not sit still?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("How often did you feel that everything was an effort?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("How often did you feel so sad that nothing could cheer you up?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("How often have you been bothered by worrying too much about different things?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("How often have you been bothered by becoming easily annoyed or irritable?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("How often have you been bothered by feeling afraid as if something awful might happen?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Have you been bothered by worrying about any financial problems?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Have you been bothered by worrying about any difficulties with your partner?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Have you been bothered by worrying about little or no sexual desire or pleasure during sex?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Have you been bothered by worrying about how you look?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Have you slowed down in ways that other people have noticed?","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("Are you losing your temper or fighting more than you used to? ","NOT AT ALL","SEVERAL DAYS","MORE THAN HALF THE DAYS","NEARLY EVERY DAY",0,1,2,3));
        listOfQ.add(new QuestionOptions("All the tasks you have performed, are taking much more time than usual?","Strongly Disagree","Disagree","Agree","Strongly Agree",0,1,2,3));
        listOfQ.add(new QuestionOptions("You are feeling you have no future.","Strongly Disagree","Disagree","Agree","Strongly Agree",0,1,2,3));
        listOfQ.add(new QuestionOptions("You are facing problems with making decisions","Strongly Disagree","Disagree","Agree","Strongly Agree",0,1,2,3));
        listOfQ.add(new QuestionOptions("You are having trust issues with everyone around you.","Strongly Disagree","Disagree","Agree","Strongly Agree",0,1,2,3));





    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }
}