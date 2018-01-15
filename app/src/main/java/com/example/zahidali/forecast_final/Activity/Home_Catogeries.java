package com.example.zahidali.forecast_final.Activity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

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

        viewPager = (ViewPager)findViewById(R.id.viewpager);


        setupViewPager(viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        Home_Cat_Pager_Adapter adapter = new Home_Cat_Pager_Adapter(getSupportFragmentManager());
        adapter.addFragment(new Home(), "Models");
        adapter.addFragment(new Categoris(), "Categories");
        viewPager.setAdapter(adapter);
    }
}
