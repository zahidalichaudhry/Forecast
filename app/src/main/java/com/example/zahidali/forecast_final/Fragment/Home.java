package com.example.zahidali.forecast_final.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.zahidali.forecast_final.Activity.Login;
import com.example.zahidali.forecast_final.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements BaseSliderView.OnSliderClickListener{
        SliderLayout sliderLayout ;
      static String path0;
      String menimage,womenimage,saleimage;
    static String path1;
    ImageView men,women,sale;
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
        path0="http://www.forecast.com.pk/media/wysiwyg/porto/homepage/slider/02/banner3.jpg";
        path1="http://www.forecast.com.pk/media/wysiwyg/porto/homepage/slider/02/13.jpg";
        menimage="http://www.forecast.com.pk/media/wysiwyg/porto/homepage/slider/02/samllbanner.jpg";
        womenimage="http://www.forecast.com.pk/media/wysiwyg/porto/homepage/slider/02/smallbanner2%20(1).jpg";
        saleimage="http://www.forecast.com.pk/media/wysiwyg/porto/homepage/slider/02/probanner3.jpg";
        men=(ImageView)view.findViewById(R.id.men);
        women=(ImageView)view.findViewById(R.id.women);
        sale=(ImageView)view.findViewById(R.id.sale);
        Glide.with(getActivity()).load(menimage).into(men);
        Glide.with(getActivity()).load(womenimage).into(women);
        Glide.with(getActivity()).load(saleimage).into(sale);

        AddImagesUrlOnline();

        return view;
    }

       private void AddImagesUrlOnline()

       {

        HashMapForURL = new HashMap<String, String>();

           HashMapForURL.put(" ", path0);
           HashMapForURL.put("  ", path1);
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
}
