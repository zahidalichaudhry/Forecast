package com.example.zahidali.forecast_final.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.zahidali.forecast_final.PojoClasses.Images_Pojo;
import com.example.zahidali.forecast_final.R;

import java.util.ArrayList;

/**
 * Created by Itpvt on 13-Nov-17.
 */

public class custom_model_swip extends PagerAdapter {


    ArrayList<Images_Pojo> arrayList = new ArrayList<>();
    private Context ctx;
    private LayoutInflater layoutInflater;
    public custom_model_swip(Context ctx, ArrayList<Images_Pojo> arrayList) {
        this.ctx = ctx;
        this.arrayList = arrayList;
        layoutInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item_view= layoutInflater.inflate(R.layout.swip_images,container,false );
        ImageView imageView=(ImageView)  item_view.findViewById(R.id.image);
        String image = arrayList.get(position).getUrl();
        Glide.with(ctx).load(image).into(imageView);
        container.addView(item_view);
        return item_view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
