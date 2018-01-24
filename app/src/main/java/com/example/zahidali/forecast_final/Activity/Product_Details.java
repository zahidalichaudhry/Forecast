package com.example.zahidali.forecast_final.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.zahidali.forecast_final.PojoClasses.Spinner_attribute_Pojo;
import com.example.zahidali.forecast_final.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product_Details extends AppCompatActivity {
    String P_id;
    TextView name,tv_aval,tv_price;
    ImageView imageView;
    EditText ed_qty;
    Spinner s_color,s_size;
    Button Buy;
    ArrayList<Spinner_attribute_Pojo> arrayListcolor= new ArrayList<>();
    ArrayList<Spinner_attribute_Pojo> arrayListsize= new ArrayList<>();
    String value_indexc="",value_indexs="";
    String atr_id2,atr_id;
    String cart_no=null;

    private ProgressDialog loading;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          Product_Details.super.onBackPressed();
            }
        });
        ImageView bag=(ImageView)findViewById(R.id.bag);
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Product_Details.this,MyCart.class);
                startActivity(intent);
            }
        });
        final Intent intent=getIntent();
        P_id=intent.getStringExtra("product_id");
        name=(TextView)findViewById(R.id.p_name);
        imageView=(ImageView)findViewById(R.id.p_image);
        ed_qty=(EditText)findViewById(R.id.ed_qty);
        tv_price=(TextView)findViewById(R.id.tv_price);
        s_color=(Spinner)findViewById(R.id.spinner_color) ;
        s_size=(Spinner)findViewById(R.id.spinner_size);
        tv_aval=(TextView)findViewById(R.id.tv_qnty);
        Buy=(Button)findViewById(R.id.buy);
        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_CART, Context.MODE_PRIVATE);
                cart_no=sharedPreferences.getString(Config.SHARED_PREF_CART_NO,null);

                    if (cart_no==null)
                    {
                        ADDTOCART();

                    }else
                    {
                        ADDTOCARTWITHCARTNO();
                    }


            }
        });
        s_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner_attribute_Pojo country = (Spinner_attribute_Pojo) parent.getSelectedItem();
                value_indexc=country.getValue_index().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        s_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Spinner_attribute_Pojo country = (Spinner_attribute_Pojo) parent.getSelectedItem();
                value_indexs=country.getValue_index().toString();
//                Toast.makeText(context, "Country ID: "+country.getId()+",  Country Name : "+country.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
///////////////////////////////////////gettting product details////////////////////////
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
                    tv_aval.setText("In Stock");
                    tv_price.setText(p_price);
                    if (p_quantity.equals("0.0000"))
                    {
//                        tv_aval.setText("Out Of Stock");
                        tv_aval.setText("Out Of Stock");
                        tv_aval.setTextColor(R.color.red);
                        Buy.setEnabled(false);
                        ed_qty.setVisibility(View.GONE);
                        s_color.setVisibility(View.GONE);
                        s_size.setVisibility(View.GONE);
                    }
                    else if (p_type.equals("configurable") && !p_quantity.equals("0.0000"))
                    {

                            Buy.setEnabled(true);
                            s_color.setVisibility(View.VISIBLE);
                             s_size.setVisibility(View.VISIBLE);
                            ed_qty.setVisibility(View.VISIBLE);
                            productifConfigure();
                           // ed_qty.setVisibility(View.GONE);
                    }
                    else if (p_type.equals("simple") && !p_quantity.equals("0.0000"))
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
////////////////////////ADD TO CART ON EXISTING ORDER//////////////////////////
    private void ADDTOCARTWITHCARTNO()
    {
        loading = ProgressDialog.show(this,"Adding...","Please Wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject result=new JSONObject(response);
                        Toast.makeText(getApplicationContext(), "Product Added"  , Toast.LENGTH_SHORT).show();
                        ChoseOption();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
                params.put("color_option", value_indexs);
                params.put("size_option", value_indexc);
                params.put("quantity", ed_qty.getText().toString());
                params.put("prod_id", P_id);
                params.put("cart_id", cart_no);
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

    ///////////////////////Add to Cart For New Order/////////////////////////////////////////////////////////////////////
    private void ADDTOCART()
    {
        loading = ProgressDialog.show(this,"Adding...","Please Wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    JSONObject data=new JSONObject(response);
                        cart_no=data.getString("cart_id");
                        //Creating a shared preference
                        SharedPreferences sharedPreferences = Product_Details.this.getApplicationContext().
                                getSharedPreferences(Config.SHARED_PREF_CART, Context.MODE_PRIVATE);
                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Config.SHARED_PREF_CART_NO, cart_no);
                        Toast.makeText(getApplicationContext(), "Product Added"  , Toast.LENGTH_SHORT).show();
                    //Adding values to editor

                    //Saving values to editor
                    editor.commit();
                        ChoseOption();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Try Again.. Something Went Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


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
                params.put("color_option", value_indexs);
                params.put("size_option", value_indexc);
                params.put("quantity", ed_qty.getText().toString());
                params.put("prod_id", P_id);
//                params.put("cart_id", P_id);
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
///////////////////////////////////////////////////////////////////////////////////////////////////////
    private void ChoseOption()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Product_Details.this);
        LayoutInflater inflater = Product_Details.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.chose_review_chekout, null);
        builder.setView(dialogView);
        Button shop,chek;
        shop=(Button)dialogView.findViewById(R.id.shop);
        chek=(Button)dialogView.findViewById(R.id.chek);
        builder.setTitle("Product Added Chose next");
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Product_Details.this,Home_Catogeries.class);
                startActivity(intent);
            }
        });
        chek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Product_Details.this,Check_Out.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
/////////////////////////////////Getting Details For configurable products//////////////////////////////
    private void productifConfigure()
    {
        loading = ProgressDialog.show(this,"Getting...","Configure Details...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_PRODUCT_DETAILS_CONFIGURE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loading.dismiss();
                try {
                    JSONArray config=new JSONArray(response);
                    int i=0;
                    JSONObject attribute1=config.getJSONObject(i);
                    String label=attribute1.getString("label");
                    atr_id=attribute1.getString("attribute_id");
                    if (atr_id.equals("92"))
                    {
                        JSONArray value=attribute1.getJSONArray("values");
                        for (int j=0;j<value.length();j++)
                        {
                            JSONObject data=value.getJSONObject(j);
                            String value_index=data.getString("value_index");
                            String label_index=data.getString("label");
                            arrayListcolor.add(new Spinner_attribute_Pojo(label_index,value_index));

                        }
                        ArrayAdapter<Spinner_attribute_Pojo> adapter = new
                                ArrayAdapter<Spinner_attribute_Pojo>(Product_Details.this,
                                android.R.layout.simple_spinner_dropdown_item, arrayListcolor);
                        s_color.setPrompt("Color");
                        s_color.setAdapter(adapter);

                        //s_color.setSelection(adapter.getPosition(myItem));//Optional to set the selected item.



                    }else if (atr_id.equals("144"))
                    {
                        JSONArray value=attribute1.getJSONArray("values");
                        for (int j=0;j<value.length();j++)
                        {
                            JSONObject data=value.getJSONObject(j);
                            String value_index=data.getString("value_index");
                            String label_index=data.getString("label");
                            arrayListsize.add(new Spinner_attribute_Pojo(label_index,value_index));


                        }
                        ArrayAdapter<Spinner_attribute_Pojo> adapter = new
                                ArrayAdapter<Spinner_attribute_Pojo>(Product_Details.this,
                                android.R.layout.simple_spinner_dropdown_item, arrayListsize);
                        s_size.setPrompt("Size");
                        s_size.setAdapter(adapter);

                    }

                    int k=1;
                    JSONObject attribute2=config.getJSONObject(k);
                    String label2=attribute2.getString("label");
                     atr_id2=attribute2.getString("attribute_id");
                    if (atr_id2.equals("92"))
                    {
                        JSONArray value=attribute2.getJSONArray("values");

                        for (int j=0;j<value.length();j++)
                        {
                            JSONObject data=value.getJSONObject(j);
                            String value_index=data.getString("value_index");
                            String label_index=data.getString("label");
                            arrayListcolor.add(new Spinner_attribute_Pojo(label_index,value_index));

                        }
                        ArrayAdapter<Spinner_attribute_Pojo> adapter = new
                                ArrayAdapter<Spinner_attribute_Pojo>(Product_Details.this,
                                android.R.layout.simple_spinner_dropdown_item, arrayListcolor);
                        s_color.setPrompt("Color");
                        s_color.setAdapter(adapter);


                    }else if (atr_id2.equals("144"))
                    {
                        JSONArray value=attribute2.getJSONArray("values");
                        for (int j=0;j<value.length();j++)
                        {
                            JSONObject data=value.getJSONObject(j);
                            String value_index=data.getString("value_index");
                            String label_index=data.getString("label");
                            arrayListsize.add(new Spinner_attribute_Pojo(label_index,value_index));
                        }
                        ArrayAdapter<Spinner_attribute_Pojo> adapter = new
                                ArrayAdapter<Spinner_attribute_Pojo>(Product_Details.this,
                                android.R.layout.simple_spinner_dropdown_item, arrayListsize);
                        s_size.setPrompt("Size");
                        s_size.setAdapter(adapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

////////////////////////////////////////////////////////////////////////////////////////////////


































//        loading = ProgressDialog.show(this,"Getting...","Configure Details...",false,false);
//        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_PRODUCT_DETAILS_CONFIGURE, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                loading.dismiss();
//
//
//                try {
//                    JSONObject object = new JSONObject(response);
//                    JSONArray arraycolor=object.getJSONArray("color");
//                    JSONArray arraysize=object.getJSONArray("size");
//                    int count=0;
//                    if (arraycolor!=null)
//                    {
//
//                        while (count<arraycolor.length())
//                        {
//                            try {
//                                JSONObject jsonObject=arraycolor.getJSONObject(count);
//                                arrayListcolor.add(jsonObject.getString("option_title"));
//
//                                count++ ;
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Product_Details.this, R.layout.spinner_back, arrayListcolor);
//                        // Apply the adapter to the spinner
//                        s_size.setAdapter(adapter);
//                        s_size.setPrompt("Select Color");
//                    }
//                    if (arraysize!=null)
//                    {
//                        while (count<arraysize.length())
//                        {
//                            try {
//                                JSONObject jsonObject=arraysize.getJSONObject(count);
//                                arrayListsize.add(jsonObject.getString("option_title"));
//
//                                count++ ;
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Product_Details.this, R.layout.spinner_back, arrayListsize);
//                        // Apply the adapter to the spinner
//                        s_size.setAdapter(adapter);
//                        s_size.setPrompt("Select Size");
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                // object.get("");
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //  Log.e("Error",error.printStackTrace());
//                loading.dismiss();
//                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();
//
//            }
//        }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("product_id", P_id);
////
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(request);
    }

}
