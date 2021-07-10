package com.example.fileexplorer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileexplorer.HomeCategoy;
import com.example.fileexplorer.R;
import com.example.fileexplorer.adapter.model.Model1;
import com.example.fileexplorer.viewholder.Homearraylist;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomearraylistAdapter extends RecyclerView.Adapter<Homearraylist> {
    ArrayList<Model1> model1ArrayList;
    Context context;

    public HomearraylistAdapter(ArrayList<Model1> model1ArrayList, Context context) {
        this.model1ArrayList = model1ArrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Homearraylist onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homesingle,parent,false);
        return new Homearraylist(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Homearraylist holder, int position) {
        Model1 temp=model1ArrayList.get(position);
        holder.img.setImageResource(temp.getImage());
        holder.tx1.setText(temp.getText1());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, HomeCategoy.class);
                intent.putExtra("category",temp.getText1());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model1ArrayList.size();
    }
}
