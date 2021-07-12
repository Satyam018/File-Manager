package com.example.fileexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeCategoy extends AppCompatActivity {
    String category;
    String filelink;

    TextView categorysname;
    RecyclerView categoryrecycler;


    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_categoy);
        categorysname=(TextView)findViewById(R.id.homecategory);
        categoryrecycler=(RecyclerView)findViewById(R.id.homecategoryrecycler);
        category = getIntent().getStringExtra("category");







        categorysname.setText(category);


        filelink = System.getenv("EXTERNAL_STORAGE");
        Managefile managefile=new Managefile(filelink,HomeCategoy.this,category,categoryrecycler);
        managefile.start();










    }



}