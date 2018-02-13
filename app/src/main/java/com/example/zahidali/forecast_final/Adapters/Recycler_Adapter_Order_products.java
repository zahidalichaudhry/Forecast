package com.example.zahidali.forecast_final.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zahidali.forecast_final.Activity.Product_Details;
import com.example.zahidali.forecast_final.PojoClasses.All_product_pojo;
import com.example.zahidali.forecast_final.PojoClasses.Order_product_pojo;
import com.example.zahidali.forecast_final.R;

import java.util.ArrayList;

/**
 * Created by Itpvt on 13-Jan-18.
 */

public class Recycler_Adapter_Order_products extends RecyclerView.Adapter<Recycler_Adapter_Order_products.MyViewHolder>{

    ArrayList<Order_product_pojo> arrayList= new ArrayList<>();
    Activity activity;

    public Recycler_Adapter_Order_products(ArrayList<Order_product_pojo> arrayList, Context context)
    {
        this.arrayList=arrayList;
        activity=(Activity)context;
    }

    @Override
    public Recycler_Adapter_Order_products.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_order_details,parent,false);
        return new Recycler_Adapter_Order_products.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Recycler_Adapter_Order_products.MyViewHolder holder, final int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.sku.setText(arrayList.get(position).getSku());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder
    {
        TextView name,sku;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.p_name);
            sku=(TextView)itemView.findViewById(R.id.p_SKU);
        }
    }
}
