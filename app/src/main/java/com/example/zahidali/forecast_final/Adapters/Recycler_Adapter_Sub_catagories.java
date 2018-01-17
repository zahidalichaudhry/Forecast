package com.example.zahidali.forecast_final.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zahidali.forecast_final.Activity.Sub_Categories;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.PojoClasses.Categories;
import com.example.zahidali.forecast_final.PojoClasses.Sub_Categories_pojo;
import com.example.zahidali.forecast_final.R;

import java.util.ArrayList;

/**
 * Created by Itpvt on 13-Jan-18.
 */

public class Recycler_Adapter_Sub_catagories extends RecyclerView.Adapter<Recycler_Adapter_Sub_catagories.MyViewHolder>{

    ArrayList<Sub_Categories_pojo> arrayList= new ArrayList<>();
    Activity activity;
    String WebUrl;

    public Recycler_Adapter_Sub_catagories(ArrayList<Sub_Categories_pojo> arrayList, Context context,String WebUrl)
    {
        this.arrayList=arrayList;
        activity=(Activity)context;
        this.WebUrl=WebUrl;

    }

    @Override
    public Recycler_Adapter_Sub_catagories.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item,parent,false);
        return new Recycler_Adapter_Sub_catagories.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Recycler_Adapter_Sub_catagories.MyViewHolder holder, final int position) {


        holder.name.setText(arrayList.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String Subpart=WebUrl+"/"+arrayList.get(position).getName().toLowerCase();
                    Intent intent = new Intent(activity,Sub_Categories.class);
                    intent.putExtra("id",arrayList.get(position).getCategory_id());
                    intent.putExtra("weburl",Subpart);
                    activity.startActivity(intent);

//
            }
        });

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
