package com.example.zahidali.forecast_final.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zahidali.forecast_final.R;
import com.example.zahidali.forecast_final.Sing_up;

public class Login extends AppCompatActivity {
    TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=(TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this, Sing_up.class);
                startActivity(intent);
            }
        });
    }
}
