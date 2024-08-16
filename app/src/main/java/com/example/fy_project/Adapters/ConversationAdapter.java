package com.example.fy_project.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fy_project.Models.Conversation;
import com.example.fy_project.R;

import java.util.ArrayList;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder>{
    ArrayList<Conversation> list ;
    String chat_user,current_user;

    public ConversationAdapter(ArrayList<Conversation> list, String chat_user, String current_user) {
        this.list = list;
        this.chat_user = chat_user;
        this.current_user = current_user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_chat,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Conversation conversation = list.get(position);
        if (conversation.getSender_id().equals(current_user)){
            holder.chat_user_message.setVisibility(View.GONE);
            holder.user_message.setVisibility(View.VISIBLE);
            holder.user_message.setText(conversation.getMessage());
        }
        else if (conversation.getSender_id().equals("o")){
            holder.user_message.setVisibility(View.GONE);
            holder.chat_user_message.setVisibility(View.VISIBLE);
            holder.chat_user_message.setText(conversation.getMessage());
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView chat_user_message,user_message;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chat_user_message = itemView.findViewById(R.id.chat_user_message);
            user_message = itemView.findViewById(R.id.user_message);
        }
    }
}
