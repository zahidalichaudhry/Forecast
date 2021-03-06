package com.example.zahidali.forecast_final.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.zahidali.forecast_final.Adapters.Recycler_Adapter_All_Products;
import com.example.zahidali.forecast_final.Adapters.Recycler_Adapter_All_Products_new;
import com.example.zahidali.forecast_final.Adapters.custom_model_swip;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.PojoClasses.All_product_pojo;
import com.example.zahidali.forecast_final.PojoClasses.Images_Pojo;
import com.example.zahidali.forecast_final.R;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class FullScreenImage extends AppCompatActivity {
//    private ScaleGestureDetector scaleGestureDetector;
//    private Matrix matrix = new Matrix();
    String URl;
    private boolean exit = false;
    ViewPager viewPager;
    private ProgressDialog loading;
    custom_model_swip adapter;
    ArrayList<Images_Pojo> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        final Intent intent = getIntent();
        URl = intent.getStringExtra("URL");
        viewPager=(ViewPager)findViewById(R.id.view_pager);
//
//        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());

        loading = ProgressDialog.show(FullScreenImage.this,"Loading...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_REMOVE_HD_IMAGE, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray abc= new JSONArray(response);
                    for (int i=0;i<abc.length();i++)
                    {

                        JSONObject data=abc.getJSONObject(i);
                            arrayList.add(new Images_Pojo(data.getString("img")));
                    }
                    adapter=new custom_model_swip(FullScreenImage.this,arrayList);//now we send the name urls to adapter
                    viewPager.setAdapter(adapter);//we set that adapter to the recycerView
                    loading.dismiss();


                }

                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(FullScreenImage.this,"Nothing is Available For Time Being",Toast.LENGTH_LONG).show();
                    loading.dismiss();
                    onBackPressed();
                }


                //  tvSurah.setText("Response is: "+ response.substring(0,500));
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loading.dismiss();
                //  Log.e("Error",error.printStackTrace());
                Toast.makeText(FullScreenImage.this,"Nothing is Available For Time Being",Toast.LENGTH_LONG).show();
                loading.dismiss();
//                Toast.makeText(getActivity().getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("product_id", URl);
                return params;
            }
        };
        //////to stop reties and wait for respone more than regular time/////
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        scaleGestureDetector.onTouchEvent(ev);
//        return true;
//    }
//
//    private class ScaleListener extends ScaleGestureDetector.
//            SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            float scaleFactor = detector.getScaleFactor();
//            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
//            matrix.setScale(scaleFactor, scaleFactor);
//            fullImage.setImageMatrix(matrix);
//            return true;
//        }
//    }
}
