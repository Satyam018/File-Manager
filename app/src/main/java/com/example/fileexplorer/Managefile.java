package com.example.fileexplorer;

import android.content.Context;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileexplorer.adapter.CategoryviewAdapter;

import java.io.File;
import java.util.ArrayList;

public class Managefile extends Thread {
    private static final String TAG = "TAG";
    String filelink;
    ArrayList<File> fileArrayList;
    RecyclerView recyclerView;
    ArrayList<File> shorteddata;
    Context context;
    String category;
    CategoryviewAdapter categoryviewAdapter;

    Managefile(String filelink,Context context,String category,RecyclerView recyclerView){
        this.filelink=filelink;
        this.context=context;
        this.category=category;
        this.recyclerView=recyclerView;
    }


    @Override
    public void run() {
        fileArrayList=new ArrayList<>();
        shorteddata=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        categoryviewAdapter=new CategoryviewAdapter(context,shorteddata,category);
        recyclerView.setAdapter(categoryviewAdapter);

        getshortedfiles(filelink);



       // categoryviewAdapter.notifyDataSetChanged();


    }
    private void getshortedfiles(String path) {
        File file=new File(path);
        if (file.isDirectory()&&file.canRead()){
            File[] file1=file.listFiles();
            for (File singlefile:file1){
                if (singlefile.isDirectory()&&singlefile.canRead()){
                    getshortedfiles(singlefile.getAbsolutePath());
                }else if (singlefile.isFile()&&singlefile.canRead()){
                    displayfile(singlefile);
                    //Log.e(TAG, "getshortedfiles: "+singlefile.getAbsolutePath() );

                }

            }

        }else if (file.isFile()&&file.canRead()){
            displayfile(file);
            //Log.e(TAG, "getshortedfiles: "+file.getAbsolutePath() );


        }
    }

    private void displayfile(File singlefile){

        switch (category){
            case "image":
                if (singlefile.getName().toLowerCase().endsWith(".png")||singlefile.getName().toLowerCase().contains(".jpg")
                  ||singlefile.getName().toLowerCase().endsWith(".jpeg")||singlefile.getName().toLowerCase().endsWith(".gif")) {
                    shorteddata.add(singlefile);
                    //Log.e(TAG, "displayfile: " + singlefile.getAbsolutePath());
                }

                break;
            case "video":
                if (singlefile.getName().toLowerCase().endsWith(".mp4")||singlefile.getName().toLowerCase().contains(".avi")
                        ||singlefile.getName().toLowerCase().endsWith(".mkv")||singlefile.getName().toLowerCase().endsWith(".webm")||
                singlefile.getName().toLowerCase().endsWith(".mov")) {
                    shorteddata.add(singlefile);
                   // Log.e(TAG, "displayfile: " + singlefile.getAbsolutePath());
                }
                    break;
                    case "apk":
                        if (singlefile.getName().toLowerCase().endsWith(".apk")) {
                            shorteddata.add(singlefile);
                          //  Log.e(TAG, "displayfile: " + singlefile.getAbsolutePath());
                        }

                        break;
                    case ("pdf"):
                        if (singlefile.getName().toLowerCase().endsWith(".pdf")) {
                            shorteddata.add(singlefile);
                            //Log.e(TAG, "displayfile: " + singlefile.getAbsolutePath());
                        }
                        break;
                    case ("downloads"):
                        String filelink=System.getenv("EXTERNAL_STORAGE");
                        File file=new File(filelink+"/Download");
                        File[] filearray=file.listFiles();
                        for (File singlefiles:filearray){
                            shorteddata.add(singlefiles);
                        }
                        break;

                }
        }




    }

