package com.example.fileexplorer.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileexplorer.R;

import org.jetbrains.annotations.NotNull;

public class Fileviewholders extends RecyclerView.ViewHolder {
   public TextView filename,filesize;
   public CardView container;
   public ImageView imgfile;
    public Fileviewholders(@NonNull @NotNull View itemView) {
        super(itemView);
        filename=itemView.findViewById(R.id.filecontainerfilename);
        filesize=itemView.findViewById(R.id.filecontainerfilesize);
        container=itemView.findViewById(R.id.container);
        imgfile=itemView.findViewById(R.id.filecontainerfiletype);


    }
}
