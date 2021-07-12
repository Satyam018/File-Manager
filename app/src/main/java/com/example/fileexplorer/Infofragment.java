package com.example.fileexplorer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Infofragment extends Fragment {
    ImageView youtube;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_infofragment, container, false);
      youtube=view.findViewById(R.id.youtube);

      youtube.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                youtubes("https://www.youtube.com/channel/UCpq0hQ-w7H7Fcx09VwfH-PQ");
          }
      });

      return view;
    }

    private void youtubes(String link){
        Uri uri=Uri.parse(link);
        getContext().startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }



}