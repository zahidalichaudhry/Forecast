package com.example.zahidali.forecast_final.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zahidali.forecast_final.Adapters.Recycler_Adapter_All_Products;
import com.example.zahidali.forecast_final.Adapters.Recycler_Cart_Items;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.PojoClasses.All_product_pojo;
import com.example.zahidali.forecast_final.PojoClasses.cart_item_pojo;
import com.example.zahidali.forecast_final.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyCart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loading;
    Recycler_Cart_Items adapter;
    ArrayList<cart_item_pojo> arrayList=new ArrayList<>();
    String cart_no=null;
    String grand,grand2,discount;
    int b,c;
    float d=150,e;
    TextView all_total;
    Button chek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyCart.this,Home_Catogeries.class);
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
        recyclerView=(RecyclerView)findViewById(R.id.model_recyclerView);
        all_total=(TextView)findViewById(R.id.g_price);
        chek=(Button)findViewById(R.id.chekout);
        ImageView bag=(ImageView)findViewById(R.id.bag);
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
        chek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart_no==null)
                {
                    Toast.makeText(MyCart.this,"There is no item in the Cart",Toast.LENGTH_LONG).show();
                    chek.setEnabled(false);
                }else
                {
                    Intent intent =new Intent(MyCart.this,Check_Out.class);
                    startActivity(intent);
                }
            }
        });
        layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_CART, Context.MODE_PRIVATE);
        cart_no=sharedPreferences.getString(Config.SHARED_PREF_CART_NO,null);
//        cart_no="2607";
        if (cart_no==null)
        {
            Toast.makeText(MyCart.this,"There is no item in the Cart",Toast.LENGTH_LONG).show();
            chek.setEnabled(false);
        }else
        {
           GettingCArt();
        }
    }

    private void GettingCArt()
    {
        loading = ProgressDialog.show(MyCart.this,"Loading...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_SHOW_CART, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if (response.equals("[]"))
                {
                    Toast.makeText(MyCart.this,"There is no item in the Cart",Toast.LENGTH_LONG).show();
                    chek.setEnabled(false);
                }else
                    {
                        try {
                            JSONArray abc= new JSONArray(response);
                            for (int i=0;i<abc.length();i=i+2)
                            {
                                JSONObject data=abc.getJSONObject(i);
                                arrayList.add(new cart_item_pojo(data.getString("product_id"),data.getString("name")
                                        ,data.getString("image_url").replace("localhost",Config.ip),data.getString("item_qty"),data.getString("total"),
                                        data.getString("item_id"),data.getString("price"),data.getString("discount_price")));
                                grand=data.getString("total");
                                discount=data.getString("discount_price");
                                if (data.getString("discount_price").equals(null))
                                {
                                    d=d+Float.valueOf(data.getString("price"))*Float.valueOf(data.getString("item_qty"));
                                }else
                                    {
                                        d=d+Float.valueOf(data.getString("discount_price"))*Float.valueOf(data.getString("item_qty"));
                                    }
                            }
                            grand2= String.valueOf(d);
                            adapter=new Recycler_Cart_Items(arrayList,MyCart.this,cart_no);
                            recyclerView.setAdapter(adapter);
                            all_total.setText(grand2);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            loading.dismiss();
                        }
                    }
                //  tvSurah.setText("Response is: "+ response.substring(0,500));
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                //  Log.e("Error",error.printStackTrace());
//                Toast.makeText(MyCart.this.getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(MyCart.this.getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("quote_id", cart_no);//cart_no
                return params;
            }
        };
        //////to stop reties and wait for respone more than regular time/////
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MyCart.this.getApplicationContext());
        requestQueue.add(request);
    }

}
