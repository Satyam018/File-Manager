package com.example.fileexplorer;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fileexplorer.adapter.FileAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;


public class Internalfragment extends Fragment implements Onfileselectedlistener {
    private static final String TAG ="TAG" ;
    private RecyclerView recyclerView;
    private TextView showfilepath;
    private ImageView back;
    private List<File> fileList;
    File storage;
    String data;
    FileAdapter fileAdapter;
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
                showfilepath.setText(file.getAbsolutePath());

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

         fileAdapter=new FileAdapter(getContext(),fileList,this);
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
    public void onfilelongclick(File file,int position) {
        final Dialog optiondialogue=new Dialog(getContext());
        optiondialogue.setContentView(R.layout.option_dialogue);
        optiondialogue.setTitle("select a option");
        ListView optiondiaglogues=(ListView)optiondialogue.findViewById(R.id.list);
        Customadapter customadapter=new Customadapter();
        optiondiaglogues.setAdapter(customadapter);
        optiondialogue.show();

        optiondiaglogues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String optionselected=parent.getItemAtPosition(position).toString();


                switch (optionselected){

                    case "Details":
                        Log.e(TAG, "onItemClick: "+"Details" );
                        AlertDialog.Builder alertdialogue=new AlertDialog.Builder(getContext());
                        alertdialogue.setTitle("Details");
                        TextView details=new TextView(getContext());
                        alertdialogue.setView(details);
                        Date lastmodfieddate=new Date(file.lastModified());
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                        String dateformatted=simpleDateFormat.format(lastmodfieddate);
                        String size= android.text.format.Formatter.formatShortFileSize(getContext(),file.length());

                        details.setText("File Name: "+file.getName()+"\n"+"Path: "+file.getAbsolutePath()+"\n"+"File size: "+size
                        +"\n"+"Last modified date: "+dateformatted);

                        alertdialogue.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();

                            }
                        });
                        AlertDialog alertDialogdetails= alertdialogue.create();
                        alertdialogue.show();
                            break;
                    case "Rename":
                        AlertDialog.Builder renamealertdialogue=new AlertDialog.Builder(getContext());
                        renamealertdialogue.setTitle("Rename File");
                        EditText name=new EditText(getContext());
                        renamealertdialogue.setView(name);
                        renamealertdialogue.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newfilename=name.getEditableText().toString();
                                String extension=file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
                                Log.e(TAG, "onClick: "+extension );
                                File current=new File(file.getAbsolutePath());
                                File destination=new File(file.getAbsolutePath().replace(file.getName(),newfilename)+extension);
                                if (current.renameTo(destination)){
                                    fileList.set(position,destination);
                                    fileAdapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(),"file name changed",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getContext(),"Filed name could not be changed",Toast.LENGTH_LONG).show();
                                }
                               dialog.dismiss();

                            }
                        });
                        renamealertdialogue.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertdrename=renamealertdialogue.create();
                        alertdrename.show();

                        break;
                    case "Delete":
                        deletefile(file,position);
                        break;
                    case "Share":
                        sharefile(file);
                        break;
                }

            }
        });

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
    public void deletefile(File file,int position) {
        AlertDialog.Builder deltefile = new AlertDialog.Builder(getContext());
        deltefile.setTitle("Delete " + file.getName() + "?");
        deltefile.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                file.delete();
                fileList.remove(position);
                fileAdapter.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(getContext(), "File deleted successfully", Toast.LENGTH_LONG).show();
            }

        });
        deltefile.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog deletedialogue = deltefile.create();
        deletedialogue.show();
    }
    public void sharefile(File file){
        String filename=file.getName();
        Intent share=new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        Uri photoURI = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", file);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.putExtra(Intent.EXTRA_STREAM,photoURI);
        startActivity(Intent.createChooser(share,"Send" +filename+"?"));
    }
}