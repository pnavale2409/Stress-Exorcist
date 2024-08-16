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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout ;
    NavigationView navigationView;
    ImageView menu_btn;
    TextView nav_user_fullname,nav_user_email;

    TextView user_name;

    FirebaseAuth firebaseAuth;
    DatabaseReference user_ref;

    TextView specialist_register,home_btn;
    final LoadingDialog loadingDialog = new LoadingDialog(SettingsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(Gravity.LEFT);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        menu_btn = findViewById(R.id.menu_btn);
        nav_user_fullname = headerView.findViewById(R.id.nav_user_fullname);
        nav_user_email = headerView.findViewById(R.id.nav_user_email);
        specialist_register = findViewById(R.id.specialist_register);
        home_btn = findViewById(R.id.home_btn);
        user_name = findViewById(R.id.user_name);



        firebaseAuth = FirebaseAuth.getInstance();
        user_ref = FirebaseDatabase.getInstance().getReference();


        user_ref.child("Users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child("fullname").getValue(String.class);
                String useremail = snapshot.child("email").getValue(String.class);
                user_name.setText(username);
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
                    case R.id.test:{
                        Intent intent_setting = new Intent(getApplicationContext(),TakeATestActivity.class);
                        startActivity(intent_setting);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    }
                    case R.id.settings:{
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


        specialist_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SpecialistRegisterActivity.class);
                startActivity(intent);
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }
}