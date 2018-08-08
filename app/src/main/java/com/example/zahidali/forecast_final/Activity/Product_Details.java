package com.example.zahidali.forecast_final.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.zahidali.forecast_final.PojoClasses.All_product_pojo;
import com.example.zahidali.forecast_final.PojoClasses.Spinner_attribute_Pojo;
import com.example.zahidali.forecast_final.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product_Details extends AppCompatActivity {
    String P_id,sku;
    TextView name,tv_aval,tv_price,tv_disprice,tv_website;
    ImageView imageView;
    EditText ed_qty;
    Spinner s_color,s_size;
    Button Buy;
    String quantity1,orig,disco;
    LinearLayout footer;
    LinearLayout spinners;
    String p_type;
    float given;
    float enter;
    String Build_Sku;
    String Image_Url=null;
    ArrayList<Spinner_attribute_Pojo> arrayListcolor= new ArrayList<>();
    ArrayList<Spinner_attribute_Pojo> arrayListsize= new ArrayList<>();
    String value_indexc="",value_indexs="";
    String color_name="",size_name="";
    String atr_id2,atr_id;
    String cart_no=null;

    private ProgressDialog loading;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        ImageView whatsapp=(ImageView)findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("smsto:"+"+923111101102");
                Intent i =new Intent(Intent.ACTION_SENDTO,uri);
                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });
        final Intent intent=getIntent();
        P_id=intent.getStringExtra("product_id");
        sku=intent.getStringExtra("SKU");
        spinners=(LinearLayout)findViewById(R.id.spinners);
        name=(TextView)findViewById(R.id.p_name);
        imageView=(ImageView)findViewById(R.id.p_image);
        ed_qty=(EditText)findViewById(R.id.ed_qty);
        tv_price=(TextView)findViewById(R.id.tv_price);
        s_color=(Spinner)findViewById(R.id.spinner_color) ;
        s_size=(Spinner)findViewById(R.id.spinner_size);
        tv_aval=(TextView)findViewById(R.id.tv_qnty);
//        tv_qty=(TextView)findViewById(R.id.tv_qty);
        tv_disprice=(TextView)findViewById(R.id.tv_disprice);
        tv_website=(TextView)findViewById(R.id.tv_website);
        Buy=(Button)findViewById(R.id.buy);
        footer=(LinearLayout)findViewById(R.id.footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://itpvt.net/"));
                startActivity(myIntent);
            }
        });
        tv_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.forecast.com.pk/index.php/"));
                startActivity(myIntent);

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Image_Url==null)
                {
                    Toast.makeText(getApplicationContext(), "Network Connection Error No Image" , Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        Intent intent=new Intent(Product_Details.this,FullScreenImage.class);
                        intent.putExtra("URL",Image_Url);
                        startActivity(intent);
                    }

////                ImageViewPopUpHelper.enablePopUpOnClick(activity,holder.Thumbnail);
//                Intent intent = new Intent(Product_Details.this,FullScreenImage.class);
//               imageView.buildDrawingCache();
//                Bitmap bitmap =imageView.getDrawingCache();
//                Bundle extras = new Bundle();
//                extras.putParcelable("imagebitmap", bitmap);
//                intent.putExtras(extras);
//                startActivity(intent);
            }
        });
        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_CART, Context.MODE_PRIVATE);
                cart_no=sharedPreferences.getString(Config.SHARED_PREF_CART_NO,null);

                    if (cart_no==null)
                    {
                        if (p_type.equals("configurable"))
                        {
                            if (!color_name.equals("") && !size_name.equals(""))
                            {
                                Build_Sku=sku+"-"+color_name+"-"+size_name;
                                ChekgivenQuantity();
//                            ADDTOCART();
                            } else if (!color_name.equals("")&& size_name.equals(""))
                            {
                                Build_Sku=sku+"-"+color_name;
                                ChekgivenQuantity();
//                            ADDTOCART();
                            }
                            else if (color_name.equals("")&& !size_name.equals(""))
                            {
                                Build_Sku=sku+"-"+size_name;
                                ChekgivenQuantity();
//                            ADDTOCART();
                            }
                            else if (color_name.equals("")&& size_name.equals(""))
                            {
                                Build_Sku=sku;
                                ChekgivenQuantity();
//                            ADDTOCART();
                            }

                        }else
                            {
                                given=Float.valueOf(quantity1);
                                enter=Float.valueOf(ed_qty.getText().toString());
                               if (given>=enter)
                               {
                                     ADDTOCART();
                               }
                               else

                                   {
                                       Toast.makeText(getApplicationContext(), "Please Reduce The Quantity" , Toast.LENGTH_SHORT).show();
                                   }
                            }


                    }else
                    {
                        if (p_type.equals("configurable"))
                        {
                            if (!color_name.equals("") && !size_name.equals(""))
                            {
                                Build_Sku=sku+"-"+color_name+"-"+size_name;
                                ChekgivenQuantity_2();
//                            ADDTOCART();
                            } else if (!color_name.equals("")&& size_name.equals(""))
                            {
                                Build_Sku=sku+"-"+color_name;
                                ChekgivenQuantity_2();
//                            ADDTOCART();
                            }
                            else if (color_name.equals("")&& !size_name.equals(""))
                            {
                                Build_Sku=sku+"-"+size_name;
                                ChekgivenQuantity_2();
//                            ADDTOCART();
                            }
                            else if (color_name.equals("")&& size_name.equals(""))
                            {
                                Build_Sku=sku;
                                ChekgivenQuantity_2();
                            }
                        }
                        else
                        {
                            given=Float.valueOf(quantity1);
                            enter=Float.valueOf(ed_qty.getText().toString());
                            if (given>=enter)
                            {
                                ADDTOCARTWITHCARTNO();
                            }
                            else

                            {
                                Toast.makeText(getApplicationContext(), "Please Reduce The Quantity" , Toast.LENGTH_SHORT).show();
                            }
                        }

                    }


            }
        });
        s_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner_attribute_Pojo country = (Spinner_attribute_Pojo) parent.getSelectedItem();
                value_indexc=country.getValue_index().toString();
                size_name=country.getLabel().toString();
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
                color_name=country.getLabel().toString();
//                if (country.getLabel().toString().equals("BLACK"))
//                {
//                    color_name="BLK";
//                }
//                else if (country.getLabel().toString().equals("AQUA BLUE"))
//                {
//                    color_name="A-BLUE";
//                }
//                else if (country.getLabel().toString().equals("AIR FORCE BLUE"))
//                {
//                    color_name="AFBLU";
//                }
//                else if (country.getLabel().toString().equals("APPLE GREEN"))
//                {
//                    color_name="AGRN";
//                }
//                else if (country.getLabel().toString().equals("APRICOT"))
//                {
//                    color_name="APCT";
//                }
//                else if (country.getLabel().toString().equals("BLACK GREY"))
//                {
//                    color_name="B GRY";
//                }
//                else if (country.getLabel().toString().equals("BLOOD RED"))
//                {
//                    color_name="B RED";
//                }
//                else if (country.getLabel().toString().equals("BLACK SILVER"))
//                {
//                    color_name="B SLVR";
//                }
//                else if (country.getLabel().toString().equals("Black Blue"))
//                {
//                    color_name="B-BLUE";
//                }
//                else if (country.getLabel().toString().equals("BEIGE"))
//                {
//                    color_name="BAIG";
//                }
//                else if (country.getLabel().toString().equals("BLACKISH BLUE"))
//                {
//                    color_name="BBLU";
//                }
//                else if (country.getLabel().toString().equals("BLACKISH BROWN"))
//                {
//                    color_name="BLKBRN";
//                }
//                else if (country.getLabel().toString().equals("BLUE"))
//                {
//                    color_name="BLU";
//                }
//                else if (country.getLabel().toString().equals("BLUISH GREY"))
//                {
//                    color_name="BLU GRY";
//                }
//                else if (country.getLabel().toString().equals("BURGUNDY"))
//                {
//                    color_name="BRGNDI";
//                }
//                else if (country.getLabel().toString().equals("BRICK"))
//                {
//                    color_name="BRK";
//                }
//                else if (country.getLabel().toString().equals("BORLAND"))
//                {
//                    color_name="BRLND";
//                }
//                else if (country.getLabel().toString().equals("BROWN"))
//                {
//                    color_name="BRN";
//                }
//                else if (country.getLabel().toString().equals("BRONZE"))
//                {
//                    color_name="BRNZ";
//                }
//                else if (country.getLabel().toString().equals("SEA SHELL"))
//                {
//                    color_name="C SHL";
//                }
//                else if (country.getLabel().toString().equals("CHARCOLE GREY"))
//                {
//                    color_name="C-GREY";
//                }
//                else if (country.getLabel().toString().equals("CHOCOLATE BROWN"))
//                {
//                    color_name="CBRN";
//                }
//                else if (country.getLabel().toString().equals("CHARCOLE"))
//                {
//                    color_name="CHR";
//                }
//                else if (country.getLabel().toString().equals("CHERRY RED"))
//                {
//                    color_name="CHRD";
//                }
//                else if (country.getLabel().toString().equals("CHEETA PRINT"))
//                {
//                    color_name="CHTHA";
//                }
//                else if (country.getLabel().toString().equals("CAMEL"))
//                {
//                    color_name="CML";
//                }
//                else if (country.getLabel().toString().equals("COFFEE"))
//                {
//                    color_name="COFE";
//                }
//                else if (country.getLabel().toString().equals("COPPER"))
//                {
//                    color_name="COPR";
//                }
//                else if (country.getLabel().toString().equals("CREAM"))
//                {
//                    color_name="CRM";
//                }
//                else if (country.getLabel().toString().equals("CRIMSON RED"))
//                {
//                    color_name="CRMRED";
//                }
//                else if (country.getLabel().toString().equals("CARROT"))
//                {
//                    color_name="CRT";
//                }
//                else if (country.getLabel().toString().equals("DARK GREEN"))
//                {
//                    color_name="D GRN";
//                }
//                else if (country.getLabel().toString().equals("DEEP ROSE"))
//                {
//                    color_name="D ROSE";
//                }
//                else if (country.getLabel().toString().equals("DARK STEEL BLUE"))
//                {
//                    color_name="D S BLU";
//                }
//                else if (country.getLabel().toString().equals("DARK VOILET"))
//                {
//                    color_name="D VLT";
//                }
//                else if (country.getLabel().toString().equals("DARK BEIGE"))
//                {
//                    color_name="D-BAIG";
//                }
//                else if (country.getLabel().toString().equals("DARK BLUE"))
//                {
//                    color_name="D-BLU";
//                }
//                else if (country.getLabel().toString().equals("DARK PINK"))
//                {
//                    color_name="D-PINK";
//                }
//                else if (country.getLabel().toString().equals("DARK BLACK"))
//                {
//                    color_name="DBLK";
//                }
//                else if (country.getLabel().toString().equals("DARK BROWN"))
//                {
//                    color_name="DBRN";
//                }
//                else if (country.getLabel().toString().equals("DARK CHARCOLE"))
//                {
//                    color_name="DCHRCL";
//                }
//                else if (country.getLabel().toString().equals("DARK GREY"))
//                {
//                    color_name="DGRY";
//                }
//                else if (country.getLabel().toString().equals("DUSTY ROSE"))
//                {
//                    color_name="DUSTY ROSE";
//                }
//                else if (country.getLabel().toString().equals("ELECTRIC BLUE"))
//                {
//                    color_name="ELECT BLU";
//                }
//                else if (country.getLabel().toString().equals("FEROZI"))
//                {
//                    color_name="FEROZI";
//                }
//                else if (country.getLabel().toString().equals("FUSHIA"))
//                {
//                    color_name="FSHIA";
//                }
//                else if (country.getLabel().toString().equals("GREY BLUE"))
//                {
//                    color_name="G BLUE";
//                }
//                else if (country.getLabel().toString().equals("GREY BROWN"))
//                {
//                    color_name="G BRN";
//                }
//                else if (country.getLabel().toString().equals("GREY BROWN"))
//                {
//                    color_name="G BRN";
//                }
//                else if (country.getLabel().toString().equals("GREY COATED"))
//                {
//                    color_name="G CTD";
//                }
//                else if (country.getLabel().toString().equals("GOLDEN YELLOW"))
//                {
//                    color_name="G YEL";
//                }
//                else if (country.getLabel().toString().equals("GREEN COATED"))
//                {
//                    color_name="G-CTD";
//                }
//                else if (country.getLabel().toString().equals("GOLDEN"))
//                {
//                    color_name="GLDN";
//                }
//                else if (country.getLabel().toString().equals("GREEN"))
//                {
//                    color_name="GRN";
//                }
//                else if (country.getLabel().toString().equals("GREY"))
//                {
//                    color_name="GRY";
//                }
//                else if (country.getLabel().toString().equals("GUN METAL"))
//                {
//                    color_name="GUN";
//                }
//                else if (country.getLabel().toString().equals("HEATHER GREY"))
//                {
//                    color_name="HGRY";
//                }
//                else if (country.getLabel().toString().equals("INDIGO"))
//                {
//                    color_name="INDG";
//                }
//                else if (country.getLabel().toString().equals("JET BLACK"))
//                {
//                    color_name="J-BLK";
//                }
//                else if (country.getLabel().toString().equals("KHAKI"))
//                {
//                    color_name="KHK";
//                }
//                else if (country.getLabel().toString().equals("LIGHT GREY"))
//                {
//                    color_name="L GREY";
//                }
//                else if (country.getLabel().toString().equals("LIGHT PURPLE"))
//                {
//                    color_name="L PUP";
//                }
//                else if (country.getLabel().toString().equals("LIGHT BLUE"))
//                {
//                    color_name="L-BLU";
//                }
//                else if (country.getLabel().toString().equals("LIGHT PINK"))
//                {
//                    color_name="L-PINK";
//                }
//                else if (country.getLabel().toString().equals("LIGHT BLACK"))
//                {
//                    color_name="LBLK";
//                }
//                else if (country.getLabel().toString().equals("LIGHT BROWN"))
//                {
//                    color_name="LBRN";
//                }
//                else if (country.getLabel().toString().equals("LIGHT CHARCOLE"))
//                {
//                    color_name="LCHRCL";
//                }
//                else if (country.getLabel().toString().equals("LIGHT GREEN"))
//                {
//                    color_name="LGRN";
//                }
//                else if (country.getLabel().toString().equals("LIGHT GREY"))
//                {
//                    color_name="LGRY";
//                }
//                else if (country.getLabel().toString().equals("LIME"))
//                {
//                    color_name="LIME";
//                }
//                else if (country.getLabel().toString().equals("LIGHT INDIGO"))
//                {
//                    color_name="LINDG";
//                }
//                else if (country.getLabel().toString().equals("LEMON"))
//                {
//                    color_name="LMN";
//                }
//                else if (country.getLabel().toString().equals("LIGHT ORANGE"))
//                {
//                    color_name="LORG";
//                }
//                else if (country.getLabel().toString().equals("MEDIUM GREY"))
//                {
//                    color_name="M GREY";
//                }
//                else if (country.getLabel().toString().equals("MEDIUM BLUE"))
//                {
//                    color_name="M/BLU";
//                }
//                else if (country.getLabel().toString().equals("MAJANTA"))
//                {
//                    color_name="MAJNDA";
//                }
//                else if (country.getLabel().toString().equals("MID BLACK"))
//                {
//                    color_name="MBLK";
//                }
//                else if (country.getLabel().toString().equals("MIX COLOR"))
//                {
//                    color_name="MIX";
//                }
//                else if (country.getLabel().toString().equals("MALON"))
//                {
//                    color_name="MLN";
//                }
//                else if (country.getLabel().toString().equals("MULTI COLOR"))
//                {
//                    color_name="MLT";
//                }
//                else if (country.getLabel().toString().equals("MAROON"))
//                {
//                    color_name="MRN";
//                }
//                else if (country.getLabel().toString().equals("MUSTARD"))
//                {
//                    color_name="MSTRD";
//                }
//                else if (country.getLabel().toString().equals("NAVY BLUE"))
//                {
//                    color_name="NBLU";
//                }
//                else if (country.getLabel().toString().equals("NAVY BLUE"))
//                {
//                    color_name="NBLU";
//                }
//                else if (country.getLabel().toString().equals("NO COLOR"))
//                {
//                    color_name="NILL";
//                }
//                else if (country.getLabel().toString().equals("OLIVE GREEN"))
//                {
//                    color_name="OGRN";
//                }
//                else if (country.getLabel().toString().equals("OLIVE GREY"))
//                {
//                    color_name="OGRY";
//                }
//                else if (country.getLabel().toString().equals("ORANGE"))
//                {
//                    color_name="ORG";
//                }
//                else if (country.getLabel().toString().equals("OFF WHITE"))
//                {
//                    color_name="OWHT";
//                }
//                else if (country.getLabel().toString().equals("PEACH"))
//                {
//                    color_name="PEACH";
//                }
//                else if (country.getLabel().toString().equals("PISTASHU"))
//                {
//                    color_name="PISTASHU";
//                }
//                else if (country.getLabel().toString().equals("PINK"))
//                {
//                    color_name="PNK";
//                }
//                else if (country.getLabel().toString().equals("PARROT"))
//                {
//                    color_name="PRT";
//                }
//                else if (country.getLabel().toString().equals("Purple"))
//                {
//                    color_name="PUP";
//                }
//                else if (country.getLabel().toString().equals("PURPLE"))
//                {
//                    color_name="PUP";
//                }
//                else if (country.getLabel().toString().equals("ROYAL BLUE"))
//                {
//                    color_name="R BLU";
//                }
//                else if (country.getLabel().toString().equals("RASBERRY ROSE"))
//                {
//                    color_name="RBROSE";
//                }
//                else if (country.getLabel().toString().equals("RED"))
//                {
//                    color_name="RED";
//                }
//                else if (country.getLabel().toString().equals("RED PEACH"))
//                {
//                    color_name="RPEACH";
//                }
//                else if (country.getLabel().toString().equals("RUST"))
//                {
//                    color_name="RST";
//                }
//                else if (country.getLabel().toString().equals("SILVER BLACK"))
//                {
//                    color_name="S BLK";
//                }
//                else if (country.getLabel().toString().equals("SEA GREEN"))
//                {
//                    color_name="S GRN";
//                }
//                else if (country.getLabel().toString().equals("SILVER WHITE"))
//                {
//                    color_name="S WHT";
//                }
//                else if (country.getLabel().toString().equals("SHALLOW ASH"))
//                {
//                    color_name="SHLW";
//                }
//                else if (country.getLabel().toString().equals("SKY BLUE"))
//                {
//                    color_name="SKBLU";
//                }
//                else if (country.getLabel().toString().equals("SKIN"))
//                {
//                    color_name="SKN";
//                }
//                else if (country.getLabel().toString().equals("SILVER"))
//                {
//                    color_name="SLV";
//                }
//                else if (country.getLabel().toString().equals("SILVER BLACK"))
//                {
//                    color_name="SLVBLK";
//                }
//                else if (country.getLabel().toString().equals("SILVER STEEL"))
//                {
//                    color_name="SLVR";
//                }
//                else if (country.getLabel().toString().equals("SNAKE BLACK"))
//                {
//                    color_name="SNKBLK";
//                }
//                else if (country.getLabel().toString().equals("TWIT BLUE"))
//                {
//                    color_name="T / BLU";
//                }
//                else if (country.getLabel().toString().equals("TINTED BLUE"))
//                {
//                    color_name="T BLUE";
//                }
//                else if (country.getLabel().toString().equals("Tea Pink"))
//                {
//                    color_name="T-PNK";
//                }
//                else if (country.getLabel().toString().equals("TEAL BEIGE"))
//                {
//                    color_name="TBAIG";
//                }
//                else if (country.getLabel().toString().equals("TEAL"))
//                {
//                    color_name="TEAL";
//                }
//                else if (country.getLabel().toString().equals("TEAL GREEN"))
//                {
//                    color_name="TLGRN";
//                }
//                else if (country.getLabel().toString().equals("TURQAISH"))
//                {
//                    color_name="TURQ";
//                }
//                else if (country.getLabel().toString().equals("VINTAGE GREEN"))
//                {
//                    color_name="VGRN";
//                }
//                else if (country.getLabel().toString().equals("VOILET"))
//                {
//                    color_name="VLT";
//                }
//                else if (country.getLabel().toString().equals("WHITE"))
//                {
//                    color_name="WHT";
//                }
//                else if (country.getLabel().toString().equals("WHITE ROSE"))
//                {
//                    color_name="WHT ROSE";
//                }
//                else if (country.getLabel().toString().equals("YELLOW"))
//                {
//                    color_name="YEL";
//                }
//                else if (country.getLabel().toString().equals("ZINK"))
//                {
//                    color_name="ZINK";
//                }
//                else {
//                    color_name = country.getLabel().toString();
//                }
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
                    String p_img_url=object.getString("img").replace("localhost",Config.ip);
                    String p_des=object.getString("proName");
                 p_type=object.getString("type_id");
                    String p_quantity=object.getString("product_quantity");
                    String P_dis_price=object.getString("discount_price");
                    JSONArray quantity=object.getJSONArray("Qunatity");
                    JSONObject data=quantity.getJSONObject(0);
                    quantity1=data.getString("qty");
                    orig=object.getString("price").replace(".0000"," ");
                    disco=object.getString("discount_price").replace(".0000"," ");

                    Image_Url=object.getString("id");

                    Glide.with(Product_Details.this).load(p_img_url).into(imageView);
                    name.setText(p_des);
                    tv_aval.setText("In Stock");
                    tv_price.setText(orig);
                    tv_disprice.setText(disco);


                    if (p_quantity.equals("0"))
                    {
//                      tv_aval.setText("Out Of Stock");
                        tv_aval.setText("Out Of Stock");
                        tv_aval.setTextColor(getResources().getColor(R.color.red));
                        Buy.setEnabled(false);
                        ed_qty.setVisibility(View.GONE);
                        s_color.setVisibility(View.GONE);
                        s_size.setVisibility(View.GONE);
                    }
                    else if (p_type.equals("configurable") && !p_quantity.equals("0"))
                    {

                            Buy.setEnabled(true);
                            s_color.setVisibility(View.VISIBLE);
                             s_size.setVisibility(View.VISIBLE);
                        spinners.setVisibility(View.VISIBLE);
                            ed_qty.setVisibility(View.VISIBLE);
                            productifConfigure();
                           // ed_qty.setVisibility(View.GONE);
                    }
                    else if (p_type.equals("simple") && !p_quantity.equals("0"))
                    {
                        ed_qty.setEnabled(true);
                        s_color.setVisibility(View.GONE);
                        s_size.setVisibility(View.GONE);
                        spinners.setVisibility(View.GONE);
//                        tv_qty.setText(quantity1);
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
                Toast.makeText(getApplicationContext(), "Network Connection Error" , Toast.LENGTH_SHORT).show();
  //              Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("product_id", P_id);
                params.put("sku", sku);
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

    private void ChekgivenQuantity_2()
    {
        loading = ProgressDialog.show(this,"Checking Availability...","Please Wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_INVENTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                loading.dismiss();
                try {
                    JSONObject main=new JSONObject(response);
                    JSONArray quantyCOnfig=main.getJSONArray("Qunatity");
                    JSONObject data=quantyCOnfig.getJSONObject(0);
                    quantity1=data.getString("qty");
                    given=Float.valueOf(quantity1);
                    enter=Float.valueOf(ed_qty.getText().toString());
                    if (given>=enter)
                    {
                        ADDTOCARTWITHCARTNO();
                    }
                    else

                    {
                        Toast.makeText(getApplicationContext(), "Please Reduce The Quantity Amount Available ="+quantity1 , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "This Product is Out of stock" , Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "This Product is Out of stock" , Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sku", Build_Sku);
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

    //////////////////Configureable Quantity////////////////////
    private void ChekgivenQuantity()
    {
        loading = ProgressDialog.show(this,"Checking Availability...","Please Wait...",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_INVENTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                loading.dismiss();
                try {
                    JSONObject main=new JSONObject(response);
                    JSONArray quantyCOnfig=main.getJSONArray("Qunatity");
                    JSONObject data=quantyCOnfig.getJSONObject(0);
                    quantity1=data.getString("qty");
                    given=Float.valueOf(quantity1);
                    enter=Float.valueOf(ed_qty.getText().toString());
                    if (given>=enter)
                    {
                        ADDTOCART();
                    }
                    else

                    {
                        Toast.makeText(getApplicationContext(), "Please Reduce The Quantity Amount Available= "+quantity1 , Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "This Product is Out of stock" , Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "This Product is Out of stock" , Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sku", Build_Sku);
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
//////////////////////////////////////////////////////
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
                    Toast.makeText(Product_Details.this,response,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Error",error.printStackTrace());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "This Product is Out of stock" , Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getApplicationContext(), "This Product Is Out of Stock", Toast.LENGTH_SHORT).show();

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
                Intent intent =new Intent(Product_Details.this,MyCart.class);
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



                    }else if (atr_id.equals("135"))
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


                    }else if (atr_id2.equals("135"))
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
                Toast.makeText(getApplicationContext(), "Network Connection Error" , Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

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
