package com.example.fileexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class HomeCategoy extends AppCompatActivity {
    String category;
    String filelink;
    ArrayList<File> mainfilelist;
    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_categoy);
        category = getIntent().getStringExtra("category");

        filelink = System.getenv("EXTERNAL_STORAGE");
        File main = new File(filelink);
        File[] files = main.listFiles();
        mainfilelist = new ArrayList<>();

        for (File singlefile : files) {
            mainfilelist.add(singlefile);
            Log.e(TAG, "onCreate: " + singlefile.getName().toString());
        }

        getshortedfiles();

    }

    private void getshortedfiles() {
        for (File singlefile : mainfilelist) {
            while (singlefile.isDirectory()) {
                String path = singlefile.getAbsolutePath();
                checkfile(path);
            }
        }

    }
    private void checkfile(String path){

    }
}