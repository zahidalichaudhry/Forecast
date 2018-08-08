package com.example.zahidali.forecast_final.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zahidali.forecast_final.Adapters.Recycler_Adapter_All_Products;
import com.example.zahidali.forecast_final.Adapters.Recycler_Adapter_Sub_catagories;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.PojoClasses.All_product_pojo;
import com.example.zahidali.forecast_final.PojoClasses.Sub_Categories_pojo;
import com.example.zahidali.forecast_final.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class All_Products extends AppCompatActivity {
    ArrayList<All_product_pojo> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    Recycler_Adapter_All_Products adapter;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loading;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all__products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(All_Products.this,Home_Catogeries.class);
                startActivity(intent);
            }
        });
        ImageView bag=(ImageView)findViewById(R.id.bag);
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(All_Products.this,MyCart.class);
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
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Intent intent = getIntent();
        id = intent.getStringExtra("Id");
        GetAllProducts();
    }
//////getting all product by vollay/////
    private void GetAllProducts()
    {
        loading = ProgressDialog.show(All_Products.this,"Loading...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_ALL_PRODUCTS, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                loading.dismiss();

                    try {
                        JSONObject abc= new JSONObject(response);
                        int j=abc.length();
                        for (int i=j;i>=1;i--)
                        {

                            String num= String.valueOf(i);
                            JSONObject data=abc.getJSONObject(num);
                            if (data.getString("product_quantity").equals("1")&&data.getString("type_id").equals("configurable"))
                            {
                                arrayList.add(new All_product_pojo(data.getString("product_id"),data.getString("pro_name")
                                        ,data.getString("img_url").replace("localhost",Config.ip),data.getString("sku")
                                        ,data.getString("product_quantity"),data.getString("price").replace(".0000","Rs")));
                            }

                        }

//                        do {JSONObject data = new getJSONObject.JSONObject("abc");
//                            String num= String.valueOf(i);
//                            obj_level1=data.getJSONObject(num);
//
//                            arrayList.add(new All_product_pojo(obj_level1.getString("product_id"),obj_level1.getString("pro_name")
//                                    ,obj_level1.getString("img_url")));
//                            i++;
//
//                        }while (obj_level1.getJSONObject(String.valueOf(i))==null);
//                        {
//                            i++;
//
//                        }
                        adapter=new Recycler_Adapter_All_Products(arrayList,All_Products.this);
                        recyclerView.setAdapter(adapter);


                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(All_Products.this,"Nothing is Available For Time Being",Toast.LENGTH_LONG).show();
                        loading.dismiss();
                        onBackPressed();
                    }



                //  tvSurah.setText("Response is: "+ response.substring(0,500));
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                //  Log.e("Error",error.printStackTrace());
                Toast.makeText(All_Products.this.getApplicationContext(), "Network Connection Error" , Toast.LENGTH_SHORT).show();
//                Toast.makeText(All_Products.this.getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("category_id", id);
                return params;
            }
        };
        //////to stop reties and wait for respone more than regular time/////
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(All_Products.this.getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(All_Products.this,Starting.class);
        finish();
        startActivity(intent);
    }
}
