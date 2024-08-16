package com.example.fy_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PsychiatristProfileActivity extends AppCompatActivity {

    String psychiatrist_name,fees,rating;
    int image_id;

    TextView pname,salary,rating_tv;
    ImageView profile_image;
    RelativeLayout send_req_btn;


    DrawerLayout drawerLayout ;
    NavigationView navigationView;
    ImageView menu_btn;

    TextView nav_user_fullname,nav_user_email;

    FirebaseAuth firebaseAuth;
    DatabaseReference user_ref;

    final LoadingDialog loadingDialog = new LoadingDialog(PsychiatristProfileActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychiatrist_profile);

        psychiatrist_name = getIntent().getStringExtra("psychiatrist_name");
        fees = getIntent().getStringExtra("fees");
        rating = getIntent().getStringExtra("rating");
        image_id = Integer.parseInt(getIntent().getStringExtra("image"));

        pname = findViewById(R.id.psychiatrist_name);
        salary = findViewById(R.id.salary);
        rating_tv = findViewById(R.id.rating);
        profile_image = findViewById(R.id.worker_image);
        send_req_btn = findViewById(R.id.send_req_btn);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(Gravity.LEFT);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        menu_btn = findViewById(R.id.menu_btn);
        nav_user_fullname = headerView.findViewById(R.id.nav_user_fullname);
        nav_user_email = headerView.findViewById(R.id.nav_user_email);


        firebaseAuth = FirebaseAuth.getInstance();
        user_ref = FirebaseDatabase.getInstance().getReference();
        if (firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }


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


        //send req btn
        send_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                intent.putExtra("psychiatrist_name",psychiatrist_name);
                startActivity(intent);
            }
        });

        pname.setText(psychiatrist_name);
        salary.setText(fees);
        rating_tv.setText(rating);

        profile_image.setImageResource(image_id);
        profile_image.setClipToOutline(true);



    }
}