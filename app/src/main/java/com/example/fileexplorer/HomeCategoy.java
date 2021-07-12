package com.example.fileexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fileexplorer.adapter.CategoryviewAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeCategoy extends AppCompatActivity  {
    String category;
    String filelink;

    TextView categorysname;
    RecyclerView categoryrecycler;
    CategoryviewAdapter categoryviewAdapter;
    List<File>  datas;


    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_categoy);
        categorysname=(TextView)findViewById(R.id.homecategory);
        categoryrecycler=(RecyclerView)findViewById(R.id.homecategoryrecycler);
        category = getIntent().getStringExtra("category");

        categoryrecycler.setLayoutManager(new LinearLayoutManager(this));
        datas=new ArrayList<>();
        categoryviewAdapter=new CategoryviewAdapter(HomeCategoy.this,datas,category);
        categoryrecycler.setAdapter(categoryviewAdapter);




        categorysname.setText(category);
        filelink = System.getenv("EXTERNAL_STORAGE");

        getshortedfiles(filelink);




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
                    datas.add(singlefile);
                    //Log.e(TAG, "displayfile: " + singlefile.getAbsolutePath());
                }

                break;
            case "video":
                if (singlefile.getName().toLowerCase().endsWith(".mp4")||singlefile.getName().toLowerCase().contains(".avi")
                        ||singlefile.getName().toLowerCase().endsWith(".mkv")||singlefile.getName().toLowerCase().endsWith(".webm")||
                        singlefile.getName().toLowerCase().endsWith(".mov")) {
                    datas.add(singlefile);
                     Log.e(TAG, "displayfile: " + singlefile.getAbsolutePath());
                }
                break;
            case "apk":
                if (singlefile.getName().toLowerCase().endsWith(".apk")) {
                    datas.add(singlefile);
                      Log.e(TAG, "displayfile: " + singlefile.getAbsolutePath());
                }

                break;
            case ("pdf"):
                if (singlefile.getName().toLowerCase().endsWith(".pdf")) {
                    datas.add(singlefile);
                    Log.e(TAG, "displayfile: " + singlefile.getAbsolutePath());
                }
                break;
            case ("downloads"):
                String filelink=System.getenv("EXTERNAL_STORAGE");
                File file=new File(filelink+"/Download");
                File[] filearray=file.listFiles();
                for (File singlefiles:filearray){
                    datas.add(singlefiles);
                }
                break;

        }
    }






}