package com.example.fy_project.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fy_project.Models.Psychiatrist;
import com.example.fy_project.PsychiatristProfileActivity;
import com.example.fy_project.R;

import java.util.ArrayList;

public class PsychiatristAdapter extends RecyclerView.Adapter<PsychiatristAdapter.MyViewHolder>{

    ArrayList<Psychiatrist> list;

    public PsychiatristAdapter(ArrayList<Psychiatrist> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_psychiatrist,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Psychiatrist workers = list.get(position);
        holder.worker_name.setText(workers.getPsychiatrist_name());
        holder.location.setText(workers.getLocation());
        int image_id = workers.getPsychiatrist_image();
        holder.worker_pic.setImageResource(image_id);
        holder.rating.setText(workers.getRating());
        holder.salary.setText(workers.getFees());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), PsychiatristProfileActivity.class);
                intent.putExtra("psychiatrist_name",workers.getPsychiatrist_name());
                intent.putExtra("rating",workers.getRating());
                intent.putExtra("fees",workers.getFees());
                intent.putExtra("image",String.valueOf(image_id));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView worker_name,location,salary,rating;
        ImageView worker_pic,bookmark_btn;
        int bookmark_select = 0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            worker_name = itemView.findViewById(R.id.worker_name);
            location = itemView.findViewById(R.id.worker_location);
            worker_pic = itemView.findViewById(R.id.worker_image);
            bookmark_btn = itemView.findViewById(R.id.bookmark_btn);
            salary = itemView.findViewById(R.id.salary);
            rating = itemView.findViewById(R.id.rating);
            worker_pic.setClipToOutline(true);

            bookmark_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bookmark_select == 0){
                        bookmark_select = 1;
                        bookmark_btn.setImageResource(R.drawable.ic_baseline_bookmark_24);
                    }
                    else {
                        bookmark_select = 0;
                        bookmark_btn.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                    }
                }
            });

        }
    }
}
