package com.example.omar.project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
Button declare;
BottomNavigationView nav_bar;
FrameLayout main_frame;
HomeFragment homeFragment;


FilterFragment filterFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        declare =(Button)findViewById(R.id.declare_btn);
        declare.setOnClickListener(this);
        homeFragment=new HomeFragment();
        filterFragment=new FilterFragment();


        main_frame=(FrameLayout) findViewById(R.id.main_frame);
        nav_bar=(BottomNavigationView)findViewById(R.id.nav_bar);
        nav_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        //nav_bar.setItemBackgroundResource(R.color.colorPrimary);
                        setFragement(homeFragment);
                        return true;

                        case R.id.nav_filter:
                        //nav_bar.setItemTextAppearanceActive();
                            startActivity(new Intent(HomeActivity.this, FilterActivity.class));
                        return true;

                        case R.id.nav_map:
                        //nav_bar.setItemBackgroundResource(R.color.colorPrimary);
                            startActivity(new Intent(HomeActivity.this, MapsViewerActivity.class));
                        return true;
                }
                return false;
            }


        });}

        private void setFragement(Fragment fragment) {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_frame,fragment,"");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.declare_btn:
                startActivity(new Intent(HomeActivity.this, DeclareActivity.class));
        }
    }
}
