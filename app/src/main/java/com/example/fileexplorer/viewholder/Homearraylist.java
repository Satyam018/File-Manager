package com.example.fileexplorer.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileexplorer.R;

import org.jetbrains.annotations.NotNull;

public class Homearraylist extends RecyclerView.ViewHolder {
  public   ImageView img;
   public TextView tx1;

    public Homearraylist(@NonNull @NotNull View itemView) {
        super(itemView);
        img=itemView.findViewById(R.id.singleimghome);
        tx1=itemView.findViewById(R.id.singletexthome);
    }
}
