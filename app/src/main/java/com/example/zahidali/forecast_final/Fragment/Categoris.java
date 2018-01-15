package com.example.zahidali.forecast_final.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zahidali.forecast_final.Activity.Login;
import com.example.zahidali.forecast_final.Activity.MainActivity;
import com.example.zahidali.forecast_final.Adapters.Recycler_Adapter_Main_Catagories;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.PojoClasses.Categories;
import com.example.zahidali.forecast_final.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Categoris extends Fragment {
    ArrayList<Categories> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    Recycler_Adapter_Main_Catagories adapter;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loading;

    public Categoris() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_categoris, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.model_recyclerView);
        layoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        MainCategories();



        return view;
    }

    private void MainCategories()

    {

        loading = ProgressDialog.show(getActivity(),"Loading...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.GET, Config.URL_All_Categories, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj_level0 = new JSONObject(response);

                    JSONArray data_level0 = obj_level0.getJSONArray("children");
                    for (int i=0;i<data_level0.length();i++)
                    {
                        JSONObject obj_level1=data_level0.getJSONObject(i);
                        JSONArray data_level1 =obj_level1.getJSONArray("children");

                        for (int j =0 ;j< data_level1.length(); j++){

                            JSONObject cat = data_level1.getJSONObject(j);
                            JSONArray data=cat.getJSONArray("children");
                            int childs=data.length();
                            arrayList.add(new Categories(cat.getString("category_id"),cat.getString("parent_id"),cat.getString("name"),
                                    cat.getString("is_active"),cat.getString("position"),cat.getString("level"),childs));
                        }
                    }
                    adapter=new Recycler_Adapter_Main_Catagories(arrayList,getActivity());
                    recyclerView.setAdapter(adapter);
                    loading.dismiss();

                }

                catch (JSONException e) {
                    e.printStackTrace();
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
//
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);
    }
}
