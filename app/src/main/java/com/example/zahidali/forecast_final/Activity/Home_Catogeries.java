package com.example.zahidali.forecast_final.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.zahidali.forecast_final.Adapters.Home_Cat_Pager_Adapter;
import com.example.zahidali.forecast_final.Fragment.Categoris;
import com.example.zahidali.forecast_final.Fragment.Home;
import com.example.zahidali.forecast_final.R;

public class Home_Catogeries extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    Home_Cat_Pager_Adapter pageradapter;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home__catogeries);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // change toolbar background image and title texr color
//        toolbar.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.actionbar_bg));
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
//        toolbar.setNavigationIcon(R.drawable.lock);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView bag=(ImageView)findViewById(R.id.bag);
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_Catogeries.this,MyCart.class);
                startActivity(intent);
            }
        });
        ImageView whatsapp=(ImageView)findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("smsto:"+"+923111101102");
                Intent i =new Intent(Intent.ACTION_SENDTO,uri);
                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });
        viewPager = (ViewPager)findViewById(R.id.viewpager);


        setupViewPager(viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        Home_Cat_Pager_Adapter adapter = new Home_Cat_Pager_Adapter(getSupportFragmentManager());
        adapter.addFragment(new Home(), "HOME");
        adapter.addFragment(new Categoris(), "CATEGORIES");
        viewPager.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
