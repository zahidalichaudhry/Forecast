package com.example.zahidali.forecast_final.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zahidali.forecast_final.Activity.All_Products;
import com.example.zahidali.forecast_final.Activity.Sub_Categories;
import com.example.zahidali.forecast_final.Activity.Web_View;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.PojoClasses.Categories;
import com.example.zahidali.forecast_final.R;

import java.util.ArrayList;

public class Recycler_Adapter_Main_Catagories extends RecyclerView.Adapter<Recycler_Adapter_Main_Catagories.MyViewHolder>{

    ArrayList<Categories> arrayList= new ArrayList<>();
    Activity activity;
    String WEB_URL=Config.URL_BASE_WEBVIEW;

    public Recycler_Adapter_Main_Catagories(ArrayList<Categories> arrayList, Context context)
    {
        this.arrayList=arrayList;
        activity=(Activity)context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        if (arrayList.get(position).getIs_active()=="1")
        {
            holder.name.setText(arrayList.get(position).getName());
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int length=arrayList.get(position).getChild();
                    if (length!=0)
                    {
                        String Subpart=WEB_URL+"/"+arrayList.get(position).getName();
                        Intent intent = new Intent(activity,Sub_Categories.class);
                        intent.putExtra("id",arrayList.get(position).getCategory_id());
                        intent.putExtra("weburl",Subpart);
                        activity.startActivity(intent);
                    }
                    else
                    {
                        String Subpart=WEB_URL+"/"+arrayList.get(position).getName().toLowerCase();
                        Intent intent=new Intent(activity,Web_View.class);
                        intent.putExtra("Id",arrayList.get(position).getCategory_id());
                        intent.putExtra("weburl",Subpart);
                        activity.startActivity(intent);
//                   Toast.makeText(activity,"Open Product Activity",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder
    {
        TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
        }
    }
}
