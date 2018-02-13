package com.example.zahidali.forecast_final.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bumptech.glide.Glide;
import com.example.zahidali.forecast_final.Activity.MyCart;
import com.example.zahidali.forecast_final.Activity.Product_Details;
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

/**
 * Created by Itpvt on 20-Jan-18.
 */

public class Recycler_Cart_Items extends RecyclerView.Adapter<Recycler_Cart_Items.MyViewHolder>{

    ArrayList<cart_item_pojo> arrayList= new ArrayList<>();
    Activity activity;
    String cart_id;
    String itemid;

    public Recycler_Cart_Items(ArrayList<cart_item_pojo> arrayList, Context context,String cart_id)
    {
        this.arrayList=arrayList;
        this.cart_id=cart_id;
        activity=(Activity)context;
    }

    @Override
    public Recycler_Cart_Items.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false);
        return new Recycler_Cart_Items.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Recycler_Cart_Items.MyViewHolder holder, final int position) {
        Glide.with(activity).load(arrayList.get(position).getImg_url()).into(holder.imageView);
        holder.p_name.setText(arrayList.get(position).getName());
        holder.item_price.setText(arrayList.get(position).getPrice());
        holder.p_qty.setText(arrayList.get(position).getItem_qty());
        holder.total_price.setText(arrayList.get(position).getTotal());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               itemid=arrayList.get(position).getItem_id();
                arrayList.remove(position);
                notifyDataSetChanged();
                removeitem();

            }
        });
//        holder.name.setText(arrayList.get(position).getPro_name());
//        Glide.with(activity).load(arrayList.get(position).getImg_url()).into(holder.imageView);
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, Product_Details.class);
//                intent.putExtra("product_id",arrayList.get(position).getProduct_id());
//                activity.startActivity(intent);
//            }
//        });
//        holder.name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, Product_Details.class);
//                intent.putExtra("product_id",arrayList.get(position).getProduct_id());
//                activity.startActivity(intent);
//
////
//            }
//        });

//        holder.Model_Thumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(activity,arrayList.get(position).getUsername(),Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(activity,Home_profile_model.class);
////                intent.putExtra("Username",arrayList.get(position).getUsername());
////                activity.startActivity(intent);
//            }
//        });
    }

    private void removeitem()
    {
       final ProgressDialog loading;
        loading = ProgressDialog.show(activity,"Loading...","Please wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_REMOVE_ITEM_CART, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loading.dismiss();
                if(response.equals("removed"))
                {
                    Toast.makeText(activity,"Item Removed",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(activity,MyCart.class);
                    activity.startActivity(intent);
                }

                //  tvSurah.setText("Response is: "+ response.substring(0,500));
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                //  Log.e("Error",error.printStackTrace());
                Toast.makeText(activity.getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("quoteId", cart_id);
                params.put("item_id", itemid);
                return params;
            }
        };
        //////to stop reties and wait for respone more than regular time/////
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder
    {
        TextView p_name,item_price,p_qty,total_price;
        Button remove;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            p_name=(TextView)itemView.findViewById(R.id.p_name);
            item_price=(TextView)itemView.findViewById(R.id.item_price);
            p_qty=(TextView)itemView.findViewById(R.id.p_qty);
            total_price=(TextView)itemView.findViewById(R.id.total_price);
            imageView=(ImageView)itemView.findViewById(R.id.thumbnail);
            remove=(Button)itemView.findViewById(R.id.remove);
        }
    }
}
