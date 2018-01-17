package com.example.zahidali.forecast_final.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product_Details extends AppCompatActivity {
    String P_id;
    TextView name,tv_aval,tv_price;
    ImageView imageView;
    EditText ed_qty;
    Spinner s_color,s_size;
    Button Buy;
    ArrayList<String> arrayListcolor= new ArrayList<>();
    ArrayList<String> arrayListsize= new ArrayList<>();

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details);

        Intent intent=getIntent();
        P_id=intent.getStringExtra("product_id");
        name=(TextView)findViewById(R.id.p_name);
        imageView=(ImageView)findViewById(R.id.p_image);
        ed_qty=(EditText)findViewById(R.id.ed_qty);
        tv_price=(TextView)findViewById(R.id.tv_price);
        s_color=(Spinner)findViewById(R.id.spinner_color) ;
        s_size=(Spinner)findViewById(R.id.spinner_size);
        tv_aval=(TextView)findViewById(R.id.tv_qnty);
        Buy=(Button)findViewById(R.id.buy);
        loading = ProgressDialog.show(this,"Fetching...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_PRODUCT_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();


                try {
                    JSONObject object = new JSONObject(response);
                    String product_id = object.getString("id");
                    String p_price = object.getString("price");
                    String p_sku = object.getString("sku");
                    String p_img_url=object.getString("img");
                    String p_des=object.getString("des");
                    String p_type=object.getString("type_id");
                    String p_quantity=object.getString("product_quantity");



                    Glide.with(Product_Details.this).load(p_img_url).into(imageView);
                    name.setText(p_des);
                    tv_aval.setText(p_quantity);
                    tv_price.setText(p_price);
                    if (p_quantity.equals("0"))

                    {
//                        tv_aval.setText("Out Of Stock");
                        tv_aval.setText(p_quantity);
                        Buy.setEnabled(false);
                        ed_qty.setVisibility(View.GONE);


                    }
                    else if (p_type.equals("configurable"))
                    {
                        if (p_quantity.equals("0"))
                        {
                            tv_aval.setText(p_quantity);
                            Buy.setEnabled(false);
                            ed_qty.setVisibility(View.GONE);
                        }else
                            {
                                ed_qty.setEnabled(true);
                                productifConfigure();
                            }

                    }
                    else
                    {
                        ed_qty.setEnabled(true);
                        s_color.setVisibility(View.GONE);
                        s_size.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_id", P_id);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void productifConfigure()
    {
        loading = ProgressDialog.show(this,"Getting...","Configure Details...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_PRODUCT_DETAILS_CONFIGURE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loading.dismiss();


                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray arraycolor=object.getJSONArray("color");
                    JSONArray arraysize=object.getJSONArray("size");
                    int count=0;
                    if (arraycolor!=null)
                    {
                   
                        while (count<arraycolor.length())
                        {
                            try {
                                JSONObject jsonObject=arraycolor.getJSONObject(count);
                                arrayListcolor.add(jsonObject.getString("option_title"));

                                count++ ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Product_Details.this, R.layout.spinner_back, arrayListcolor);
                        // Apply the adapter to the spinner
                        s_size.setAdapter(adapter);
                        s_size.setPrompt("Select Color");
                    }
                    if (arraysize!=null)
                    {
                        while (count<arraysize.length())
                        {
                            try {
                                JSONObject jsonObject=arraysize.getJSONObject(count);
                                arrayListsize.add(jsonObject.getString("option_title"));

                                count++ ;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Product_Details.this, R.layout.spinner_back, arrayListsize);
                        // Apply the adapter to the spinner
                        s_size.setAdapter(adapter);
                        s_size.setPrompt("Select Size");
                    }
                

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_id", P_id);
//
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

}
