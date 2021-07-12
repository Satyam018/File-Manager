package com.example.fileexplorer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileexplorer.Fileopener;
import com.example.fileexplorer.R;
import com.example.fileexplorer.viewholder.CategoryViewholder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CategoryviewAdapter extends RecyclerView.Adapter<CategoryViewholder> {
    private Context context;
    private List<File> fileList;
    private String category;
    private String TAG="TAG";

    public CategoryviewAdapter(Context context, List<File> fileList, String category) {
        this.context = context;
        this.fileList = fileList;
        this.category = category;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryviewholdersingle,parent,false);
        return new CategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewholder holder, int position) {
        File temp=fileList.get(position);
        holder.textView.setText(temp.getName());

            setimage(holder,temp);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   Fileopener.openfile(context,temp);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });

    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
    private void setimage(CategoryViewholder categoryViewholder,File singlefile){
        switch (category){
            case "image":
                categoryViewholder.img.setImageResource(R.drawable.image);
                break;
            case "video":
                categoryViewholder.img.setImageResource(R.drawable.video);
                break;
            case "apk":
                categoryViewholder.img.setImageResource(R.drawable.apk);
                break;
            case "pdf":
                categoryViewholder.img.setImageResource(R.drawable.pdf);
                break;
            case "downloads":
                if (singlefile.getName().toLowerCase().endsWith(".png")||singlefile.getName().toLowerCase().contains(".jpg")
                        ||singlefile.getName().toLowerCase().endsWith(".jpeg")||singlefile.getName().toLowerCase().endsWith(".gif")) {
                    categoryViewholder.img.setImageResource(R.drawable.image);

                } else if (singlefile.getName().toLowerCase().endsWith(".mp4")||singlefile.getName().toLowerCase().contains(".avi")
                        ||singlefile.getName().toLowerCase().endsWith(".mkv")||singlefile.getName().toLowerCase().endsWith(".webm")||
                        singlefile.getName().toLowerCase().endsWith(".mov")) {
                    categoryViewholder.img.setImageResource(R.drawable.video);

                } else if (singlefile.getName().toLowerCase().endsWith(".apk")) {
                    categoryViewholder.img.setImageResource(R.drawable.apk);

                }if (singlefile.getName().toLowerCase().endsWith(".pdf")) {
                    categoryViewholder.img.setImageResource(R.drawable.pdf);

                }
                break;


        }
    }
}
