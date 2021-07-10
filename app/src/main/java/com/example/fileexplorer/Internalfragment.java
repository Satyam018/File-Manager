package com.example.fileexplorer;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fileexplorer.adapter.FileAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Internalfragment extends Fragment implements Onfileselectedlistener {
    private RecyclerView recyclerView;
    private TextView showfilepath;
    private ImageView back;
    private List<File> fileList;
    File storage;
    String data;
    String items[]={"Details","Rename","Delete","Share"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_internalfragment, container, false);
        showfilepath = view.findViewById(R.id.internalfragmnetfilepath);
        back = view.findViewById(R.id.internalfragmentbackimg);
        recyclerView = view.findViewById(R.id.internalfragmentrecyclerview);


        String internalstorage = System.getenv("EXTERNAL_STORAGE");
        storage = new File(internalstorage);
        showfilepath.setText(storage.getAbsolutePath());

        try {
                data=getArguments().getString("path");
                File file=new File(data);
                storage=file;

        }catch (Exception e){
            e.printStackTrace();
        }

        runtimepermission();


        return view;
    }

    private void runtimepermission() {
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        displayfiles();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> searchfile(File file) {
        ArrayList<File> fileArrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File singlefile : files) {
            if (singlefile.isDirectory() && !singlefile.isHidden()) {
                fileArrayList.add(singlefile);
            }
        }
        for (File singlefile : files) {
            if (singlefile.getName().toLowerCase().endsWith(".jpeg") || singlefile.getName().toLowerCase().endsWith(".jpg") ||
                    singlefile.getName().toLowerCase().endsWith(".png") || singlefile.getName().toLowerCase().endsWith(".mp3") ||
                    singlefile.getName().toLowerCase().endsWith(".wav") || singlefile.getName().toLowerCase().endsWith(".mp4") ||
                    singlefile.getName().toLowerCase().endsWith(".pdf") || singlefile.getName().toLowerCase().endsWith(".doc") ||
                    singlefile.getName().toLowerCase().endsWith(".apk")){
                fileArrayList.add(singlefile);
            }
        }
       return fileArrayList;
    }

    private void displayfiles() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        fileList=new ArrayList<>();
        fileList.addAll(searchfile(storage));

        FileAdapter fileAdapter=new FileAdapter(getContext(),fileList,this);
        recyclerView.setAdapter(fileAdapter);

    }

    @Override
    public void onfileclick(File file) {
        if(file.isDirectory()){
            Bundle bundle=new Bundle();
            bundle.putString("path",file.getAbsolutePath());
            Internalfragment internalfragment=new Internalfragment();
            internalfragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.framelayout,internalfragment).addToBackStack(null).commit();
        }else {
            try {
                Fileopener.openfile(getContext(),file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onfilelongclick(File file) {
        final Dialog optiondialogue=new Dialog(getContext());
        optiondialogue.setContentView(R.layout.option_dialogue);
        optiondialogue.setTitle("select a option");
        ListView optiondiaglogues=(ListView)optiondialogue.findViewById(R.id.list);
        Customadapter customadapter=new Customadapter();
        optiondiaglogues.setAdapter(customadapter);
        optiondialogue.show();

    }
    class Customadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.option_layout,null);
            TextView textoption=view.findViewById(R.id.txtoption);
            ImageView img=view.findViewById(R.id.imgoption);
            textoption.setText(items[position]);
            if (items[position].equals("Details")){
                img.setImageResource(R.drawable.ic_launcher_background);
            }

            return view;
        }
    }
}