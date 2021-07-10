package com.example.fileexplorer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fileexplorer.adapter.HomearraylistAdapter;
import com.example.fileexplorer.adapter.model.Model1;

import java.util.ArrayList;

public class Homefragment extends Fragment {
        RecyclerView recyclerView1;
        ArrayList<Model1> model1ArrayList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_homefragment, container, false);
        recyclerView1=(view).findViewById(R.id.homerecycler1);

        setrecycler1();


        return view;

    }
    private void setrecycler1(){
        model1ArrayList=new ArrayList<>();
        model1ArrayList.add(new Model1("image",R.drawable.image));
        model1ArrayList.add(new Model1("video",R.drawable.video));
        model1ArrayList.add(new Model1("apk",R.drawable.apk));
        model1ArrayList.add(new Model1("pdf",R.drawable.pdf));
        model1ArrayList.add(new Model1("Downloads",R.drawable.download));

        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(),3));
        HomearraylistAdapter homearraylistAdapter=new HomearraylistAdapter(model1ArrayList,getContext());
        recyclerView1.stopScroll();
        recyclerView1.setAdapter(homearraylistAdapter);

    }
}