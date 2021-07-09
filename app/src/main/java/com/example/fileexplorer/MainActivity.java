package com.example.fileexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayouts;
    private FrameLayout frameLayouts;
    private NavigationView navigationView;
    private Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayouts=(DrawerLayout)findViewById(R.id.drawerlayout);
        frameLayouts=(FrameLayout)findViewById(R.id.framelayout);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=(NavigationView)findViewById(R.id.navview);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        toggle=new ActionBarDrawerToggle(this,drawerLayouts,toolbar,R.string.opendrawer,R.string.closeddrawer);
        drawerLayouts.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Homefragment()).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
        Fragment temp=null;
        switch (item.getItemId()){
            case R.id.navhome:
                temp=new Homefragment();
                break;
            case R.id.navinternalstorage:
                temp=new Internalfragment();
                break;
            case R.id.navsdcard:
                temp=new Sdcardfragment();
                break;
            case R.id.navinfo:
                temp=new Infofragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,temp).addToBackStack(null).commit();
        drawerLayouts.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayouts.isDrawerOpen(GravityCompat.START)){
            drawerLayouts.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
}