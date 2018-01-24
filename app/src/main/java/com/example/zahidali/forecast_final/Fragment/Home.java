package com.example.zahidali.forecast_final.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.zahidali.forecast_final.Activity.All_Products;
import com.example.zahidali.forecast_final.Activity.Login;
import com.example.zahidali.forecast_final.Activity.Sub_Categories;
import com.example.zahidali.forecast_final.Adapters.Recycler_Adapter_All_Products;
import com.example.zahidali.forecast_final.Adapters.Recycler_Adapter_All_Products_new;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.PojoClasses.All_product_pojo;
import com.example.zahidali.forecast_final.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements BaseSliderView.OnSliderClickListener{
    SliderLayout sliderLayout ;
    static String path0;
    LinearLayout image;
    String id;
    ArrayList<All_product_pojo> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    String newArr_cat_id="60";
    Recycler_Adapter_All_Products_new adapter;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loading;

      String menimage,womenimage,saleimage,bajiImage;
    static String path1,path2;
    ImageView men,women,sale,baji;
        HashMap<String, String> HashMapForURL ;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);
        path0=Config.BANNER1;
        path1=Config.BANNER2;
        path2=Config.BANNER3;
        menimage=Config.HOME_MEN;
        womenimage=Config.HOME_WOMEN;
        saleimage=Config.HOMW_SALE;
        bajiImage  = Config.HOME_FOOTWARE;
        men=(ImageView)view.findViewById(R.id.men);
        women=(ImageView)view.findViewById(R.id.women);
        sale=(ImageView)view.findViewById(R.id.sale);
        baji=(ImageView)view.findViewById(R.id.baji);
        Glide.with(getActivity()).load(menimage).into(men);
        Glide.with(getActivity()).load(womenimage).into(women);
        Glide.with(getActivity()).load(saleimage).into(sale);
        Glide.with(this).load(bajiImage).into(baji);
        recyclerView=(RecyclerView)view.findViewById(R.id.model_recyclerView);
//        layoutManager=new GridLayoutManager(getActivity(),1);

//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        GetAllProducts();
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),All_Products.class);
//                    Intent intent=new Intent(Sub_Categories.this,Web_View.class);
//                    intent.putExtra("weburl",WEB_URL);
                intent.putExtra("Id","57");
                startActivity(intent);
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),All_Products.class);
//                    Intent intent=new Intent(Sub_Categories.this,Web_View.class);
//                    intent.putExtra("weburl",WEB_URL);
                intent.putExtra("Id","58");
                startActivity(intent);
            }
        });
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),All_Products.class);
//                    Intent intent=new Intent(Sub_Categories.this,Web_View.class);
//                    intent.putExtra("weburl",WEB_URL);
                intent.putExtra("Id","56");
                startActivity(intent);
            }
        });
        baji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//60
                Intent intent=new Intent(getActivity(),All_Products.class);
//                    Intent intent=new Intent(Sub_Categories.this,Web_View.class);
//                    intent.putExtra("weburl",WEB_URL);
                intent.putExtra("Id","55");
                startActivity(intent);
            }
        });
        AddImagesUrlOnline();

        return view;
    }

       private void AddImagesUrlOnline()

       {

        HashMapForURL = new HashMap<String, String>();

           HashMapForURL.put(" ", path0);
           HashMapForURL.put("  ", path1);
           HashMapForURL.put("   ", path2);
           callSlider();
    }
    private void callSlider() {

        for(String name : HashMapForURL.keySet()){

            TextSliderView textSliderView = new TextSliderView(getActivity().getApplicationContext());

            textSliderView
                    .description(name)
                    .image(HashMapForURL.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(5000);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
    private void GetAllProducts()
    {

        loading = ProgressDialog.show(getActivity(),"Loading...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_ALL_PRODUCTS, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                loading.dismiss();




                try {
                    JSONObject abc= new JSONObject(response);
                    for (int i=1;i<=abc.length();i++)
                    {
                        String num= String.valueOf(i);
                        JSONObject data=abc.getJSONObject(num);
                        arrayList.add(new All_product_pojo(data.getString("product_id"),data.getString("pro_name")
                                ,data.getString("img_url").replace("localhost",Config.ip)));
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
                    adapter=new Recycler_Adapter_All_Products_new(arrayList,getActivity());
                    recyclerView.setAdapter(adapter);


                }

                catch (JSONException e) {
                    e.printStackTrace();
                    loading.dismiss();
                }



                //  tvSurah.setText("Response is: "+ response.substring(0,500));
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                //  Log.e("Error",error.printStackTrace());
                Toast.makeText(getActivity().getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("category_id", newArr_cat_id);
                return params;
            }
        };
        //////to stop reties and wait for respone more than regular time/////
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);
    }
}
