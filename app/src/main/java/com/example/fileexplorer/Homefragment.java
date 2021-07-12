package com.example.fileexplorer;

import android.Manifest;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fileexplorer.adapter.HomearraylistAdapter;
import com.example.fileexplorer.adapter.model.Model1;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class Homefragment extends Fragment {
        RecyclerView recyclerView1;
        ArrayList<Model1> model1ArrayList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_homefragment, container, false);
        recyclerView1=(view).findViewById(R.id.homerecycler1);

            //setrecyler();
        runtimepermission();


        return view;

    }


    private void setrecycler1(){
        model1ArrayList=new ArrayList<>();
        model1ArrayList.add(new Model1("image",R.drawable.image));
        model1ArrayList.add(new Model1("video",R.drawable.video));
        model1ArrayList.add(new Model1("apk",R.drawable.apk));
        model1ArrayList.add(new Model1("pdf",R.drawable.pdf));
        model1ArrayList.add(new Model1("downloads",R.drawable.download));

        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(),3));
        HomearraylistAdapter homearraylistAdapter=new HomearraylistAdapter(model1ArrayList,getContext());
        recyclerView1.setAdapter(homearraylistAdapter);

    }
    private void runtimepermission() {
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        setrecycler1();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();}
}