package com.example.fileexplorer;

import android.Manifest;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        LinearLayout gotolocal,disable;
        TextView permission,back1;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_homefragment, container, false);
        recyclerView1=(view).findViewById(R.id.homerecycler1);
        back1=view.findViewById(R.id.fi);
        disable=view.findViewById(R.id.lineardisabble);
        permission=view.findViewById(R.id.textpermission);
        gotolocal=view.findViewById(R.id.gotolocal);

            disable.setVisibility(View.INVISIBLE);
            permission.setVisibility(View.INVISIBLE);
            //setrecyler();
        runtimepermission(disable,permission);
        gotolocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.framelayout,new Internalfragment()).addToBackStack(null).commit();
            }
        });


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
    private void runtimepermission(LinearLayout disable,TextView permission) {
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()){
                        setrecycler1();
                        disable.setVisibility(View.VISIBLE);
                        }else{
                            permission.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

}