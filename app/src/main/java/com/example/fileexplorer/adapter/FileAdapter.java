package com.example.fileexplorer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileexplorer.Internalfragment;
import com.example.fileexplorer.Onfileselectedlistener;
import com.example.fileexplorer.R;
import com.example.fileexplorer.viewholder.Fileviewholders;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<Fileviewholders> {

    private static final String TAG = "TAG";
    private Context context;
    private List<File> files;
    private Onfileselectedlistener onfileselectedlisteners;

    public FileAdapter(Context context, List<File> files,Onfileselectedlistener onfileselectedlistener) {
        this.context = context;
        this.files = files;
        this.onfileselectedlisteners=onfileselectedlistener;
    }

    @NonNull
    @NotNull
    @Override
    public Fileviewholders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filecontainerlayout, parent, false);
        return new Fileviewholders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Fileviewholders holder, int position) {
        File temp = files.get(position);
        holder.filename.setText(temp.getName());


        int items = 0;
        if (temp.isDirectory()) {
            File[] files1 = temp.listFiles();
            for (File singlefile : files1) {
                if (!singlefile.isHidden()) {
                    items++;

                }
            }
            holder.filesize.setText(items + " Files");
            holder.imgfile.setImageResource(R.drawable.ic_baseline_folder_24);
        } else {
            holder.filesize.setText(Formatter.formatShortFileSize(context, temp.length()));
            getimg(temp,holder);

        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (onfileselectedlisteners).onfileclick(temp);
            }
        });
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onfileselectedlisteners.onfilelongclick(temp,position);
                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    private void getimg(File temp,Fileviewholders holder){
        if (temp.getName().toLowerCase().endsWith(".jpg") || temp.getName().toLowerCase().endsWith(".png")
                || temp.getName().endsWith(".jpeg")) {
            Log.e(TAG, "onBindViewHolder: " + temp.getName());
            holder.imgfile.setImageResource(R.drawable.image);
        } else if (temp.getName().toLowerCase().contains(".pdf") && !temp.isDirectory()) {
            Log.e(TAG, "onBindViewHolder: " + temp.getName());
            holder.imgfile.setImageResource(R.drawable.pdf);
        } else if (temp.getName().toLowerCase().endsWith(".mp3") || temp.getName().toLowerCase().endsWith(".wav")) {
            holder.imgfile.setImageResource(R.drawable.music);
        } else if (temp.getName().toLowerCase().endsWith(".doc")) {
            holder.imgfile.setImageResource(R.drawable.docx);
        } else if (temp.getName().toLowerCase().endsWith(".mp4")) {
            holder.imgfile.setImageResource(R.drawable.video);
        } else if (temp.getName().toLowerCase().endsWith(".apk")) {
            holder.imgfile.setImageResource(R.drawable.apk);
        }

    }
}
