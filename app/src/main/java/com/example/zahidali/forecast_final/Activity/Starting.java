package com.example.zahidali.forecast_final.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.R;

public class Starting extends AppCompatActivity {
    ImageView men,women,kids,home,footware,newarr,sale;
    LinearLayout footer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_starting);

        men=(ImageView)findViewById(R.id.men);
        women=(ImageView)findViewById(R.id.women);
        kids=(ImageView)findViewById(R.id.kids);
        home=(ImageView)findViewById(R.id.home);
        footware=(ImageView)findViewById(R.id.footware);
        newarr=(ImageView)findViewById(R.id.newarr);
        sale=(ImageView)findViewById(R.id.sale);
        footer=(LinearLayout)findViewById(R.id.footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://itpvt.net/"));
                startActivity(myIntent);
            }
        });
        Glide.with(Starting.this).load(Config.Staringmen).into(men);
        Glide.with(Starting.this).load(Config.Staringwomen).into(women);
        Glide.with(Starting.this).load(Config.Staringkids).into(kids);
        Glide.with(Starting.this).load(Config.Staringhome).into(home);
        Glide.with(Starting.this).load(Config.Staringshoes).into(footware);
        Glide.with(Starting.this).load(Config.Staringnew).into(newarr);
        Glide.with(Starting.this).load(Config.HOMW_SALE).into(sale);
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Starting.this,All_Products.class);
                intent.putExtra("Id","61");
                startActivity(intent);
            }
        });
        ImageView bag=(ImageView)findViewById(R.id.bag);
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Starting.this,MyCart.class);
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
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Starting.this,All_Products.class);
                intent.putExtra("Id","48");
                startActivity(intent);
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Starting.this,All_Products.class);
                intent.putExtra("Id","38");
                startActivity(intent);
            }
        });
        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Starting.this,All_Products.class);
                intent.putExtra("Id","39");
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Starting.this,Home_Catogeries.class);
                startActivity(intent);
            }
        });
        footware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Starting.this,All_Products.class);
                intent.putExtra("Id","36");
                startActivity(intent);
            }
        });
        newarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Starting.this,All_Products.class);
                intent.putExtra("Id","61");
                startActivity(intent);
            }
        });

    }
}
