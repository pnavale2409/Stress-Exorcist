package com.example.fy_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fy_project.Adapters.ConversationAdapter;
import com.example.fy_project.Models.Conversation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    String psychiatrist_name;

    //hooks
    RecyclerView chatlist;
    CircleImageView profile_image;
    TextView username;
    ImageView send_btn;
    EditText text_message;

    //adapters
    ConversationAdapter conversationAdapter;

    //lists
    ArrayList<Conversation> list;

    //Intent strings
    String user_name,profile_image_url;


    //Database ref
    DatabaseReference messages_ref;
    DatabaseReference conv_ref;
    DatabaseReference user_ref;

    //firebase auth
    FirebaseAuth firebaseAuth;

    String current_user,chat_user,current_username;

    final LoadingDialog loadingDialog = new LoadingDialog(ChatActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        psychiatrist_name = getIntent().getStringExtra("psychiatrist_name");

        username = findViewById(R.id.username);
        send_btn = findViewById(R.id.send_btn);
        text_message = findViewById(R.id.message);
        username.setText(psychiatrist_name);
        profile_image = findViewById(R.id.profile_image);
        chatlist = findViewById(R.id.chatlist);

        firebaseAuth = FirebaseAuth.getInstance();
        current_user = firebaseAuth.getCurrentUser().getUid();
        user_ref = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();

        //dialog start
        loadingDialog.startLoadingDialog();
        user_ref.child("Users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current_username = getFirstWord(snapshot.child("fullname").getValue(String.class));

                Conversation conversation1 = new Conversation("Hello, "+current_username+"!","abc","o");
                Conversation conversation2 = new Conversation("Please share your issues here, so that I can help you ","abc","o");
                list.add(conversation1);
                list.add(conversation2);
                conversationAdapter = new ConversationAdapter(list,chat_user,current_user);
                chatlist.setAdapter(conversationAdapter);
                chatlist.scrollToPosition(list.size()-1);

                //dialog stop
                loadingDialog.dismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (text_message.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Cannot send Empty message",Toast.LENGTH_SHORT).show();
                }
                else {
                    String message = text_message.getText().toString();
                    text_message.getText().clear();

                    Conversation conversation = new Conversation(message,"abc",current_user);
                    list.add(conversation);
                    refreshlist();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void refreshlist(){
        chatlist.setAdapter(conversationAdapter);
        chatlist.scrollToPosition(list.size()-1);
    }

    private String getFirstWord(String text) {

        int index = text.indexOf(' ');

        if (index > -1) { // Check if there is more than one word.

            return text.substring(0, index).trim(); // Extract first word.

        } else {

            return text; // Text is the first word itself.
        }
    }

}