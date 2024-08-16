package com.example.fy_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fy_project.Adapters.PsychiatristAdapter;
import com.example.fy_project.Models.Psychiatrist;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PsychiatristActivity extends AppCompatActivity {

    DrawerLayout drawerLayout ;
    NavigationView navigationView;
    ImageView menu_btn;

    TextView nav_user_fullname,nav_user_email;

    FirebaseAuth firebaseAuth;
    DatabaseReference user_ref;

    final LoadingDialog loadingDialog = new LoadingDialog(PsychiatristActivity.this);


    RecyclerView workers_list;

    TextView op1,op2,op3,op4;
    int op_select;

    PsychiatristAdapter psychiatristAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychiatrist);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(Gravity.LEFT);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        menu_btn = findViewById(R.id.menu_btn);
        nav_user_fullname = headerView.findViewById(R.id.nav_user_fullname);
        nav_user_email = headerView.findViewById(R.id.nav_user_email);

        firebaseAuth = FirebaseAuth.getInstance();
        user_ref = FirebaseDatabase.getInstance().getReference();

        workers_list = findViewById(R.id.workers_list);

        op_select = 1;
        op1 = findViewById(R.id.new_btn);
        op2 = findViewById(R.id.recommended_btn);
        op3 = findViewById(R.id.nearby_btn);
        op4 = findViewById(R.id.toprated_btn);


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
                        Intent intent_test = new Intent(getApplicationContext(),TakeATestActivity.class);
                        startActivity(intent_test);
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


        //op tabs
        op1.setBackgroundResource(R.drawable.workers_category_btn_select);
        op2.setBackgroundResource(R.drawable.workers_category_btn_unselect);
        op3.setBackgroundResource(R.drawable.workers_category_btn_unselect);
        op4.setBackgroundResource(R.drawable.workers_category_btn_unselect);
        op1.setTextColor(Color.WHITE);
        op2.setTextColor(getResources().getColor(R.color.greytext));
        op3.setTextColor(getResources().getColor(R.color.greytext));
        op4.setTextColor(getResources().getColor(R.color.greytext));
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (op_select != 1){
                    op_select = 1;
                    op1.setBackgroundResource(R.drawable.workers_category_btn_select);
                    op2.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op3.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op4.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op1.setTextColor(Color.WHITE);
                    op2.setTextColor(getResources().getColor(R.color.greytext));
                    op3.setTextColor(getResources().getColor(R.color.greytext));
                    op4.setTextColor(getResources().getColor(R.color.greytext));

                }
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (op_select != 2){
                    op_select = 2;
                    op1.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op2.setBackgroundResource(R.drawable.workers_category_btn_select);
                    op3.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op4.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op2.setTextColor(Color.WHITE);
                    op1.setTextColor(getResources().getColor(R.color.greytext));
                    op3.setTextColor(getResources().getColor(R.color.greytext));
                    op4.setTextColor(getResources().getColor(R.color.greytext));

                }
            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (op_select != 3){
                    op_select = 3;
                    op1.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op2.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op3.setBackgroundResource(R.drawable.workers_category_btn_select);
                    op4.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op3.setTextColor(Color.WHITE);
                    op2.setTextColor(getResources().getColor(R.color.greytext));
                    op1.setTextColor(getResources().getColor(R.color.greytext));
                    op4.setTextColor(getResources().getColor(R.color.greytext));

                }
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (op_select != 4){
                    op_select = 4;
                    op1.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op2.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op3.setBackgroundResource(R.drawable.workers_category_btn_unselect);
                    op4.setBackgroundResource(R.drawable.workers_category_btn_select);
                    op4.setTextColor(Color.WHITE);
                    op2.setTextColor(getResources().getColor(R.color.greytext));
                    op3.setTextColor(getResources().getColor(R.color.greytext));
                    op1.setTextColor(getResources().getColor(R.color.greytext));

                }
            }
        });


        //psychiatrists list
        ArrayList<Psychiatrist> list = new ArrayList<>();
        list.add(new Psychiatrist("Jerome Bell","Dadar, Mumbai","₹ 2500/-","4.50",R.drawable.dummy1));
        list.add(new Psychiatrist("Jane Copper","Vikhroli, Mumbai","₹ 1200/-","3.67",R.drawable.dummy2));
        list.add(new Psychiatrist("Austin Moreen","Hazrat, Delhi","₹ 1980/-","4.25",R.drawable.dummy3));

        psychiatristAdapter = new PsychiatristAdapter(list);
        workers_list.setAdapter(psychiatristAdapter);


    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }
}