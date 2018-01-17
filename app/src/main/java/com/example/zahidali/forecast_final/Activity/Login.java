package com.example.zahidali.forecast_final.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView register;
    EditText email, password;
    //    String u_email,u_password;
    Button login;
    private ProgressDialog loading;
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        register = (TextView) findViewById(R.id.register);
        email = (EditText) findViewById(R.id.usrusr);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.sin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validations
                final String user_email = email.getText().toString().trim();

                //validations
                if (email.getText().length() == 0) {
                    email.requestFocus();
                    email.setError(Html.fromHtml("<font color='red'>Please Enter A Email</font>"));
                } else if (user_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
                    email.requestFocus();
                    email.setError(Html.fromHtml("<font color='red'>Invalid Username</font>"));
                } else if (password.getText().length() == 0) {
                    password.requestFocus();
                    password.setError(Html.fromHtml("<font color='red'>Please Enter A Password</font>"));
                } else if (v == login) {
                    //Calling the Login Fuction;
                    loginUser();
                }
            }
        });


//        Log.i("Zahid","String");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Sing_up.class);
                startActivity(intent);
            }
        });
    }


    private void loginUser() {
        //Getting values from edit texts
        final String user_email = email.getText().toString().trim();
        final String user_pass = password.getText().toString().trim();

        final ProgressDialog pd = new ProgressDialog(Login.this);
        pd.setMessage("loading");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_USER_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();
                try {
                    JSONObject invalid=new JSONObject(response);
                    if (invalid.getString("status").equals("invalid"))
                    {
                        Toast.makeText(Login.this,"Invalid Useraname and Passwaord",Toast.LENGTH_LONG).show();
                    }
                    else if (invalid.getString("status").equals("valid")){

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject quoteId = object.getJSONObject("quoteID");
                            String firstname = quoteId.getString("customer_firstname");
                            String lastname = quoteId.getString("customer_lastname");
                            String email = quoteId.getString("customer_email");
                            String userID=object.getString("userId");
                            String Entity_ID=quoteId.getString("entity_id");
                            //    String regions =   object.get("regions").toString();

                            //Creating a shared preference
                            SharedPreferences sharedPreferences = Login.this.getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, user_email);
                            editor.putString(Config.SHARED_PREF_FirstName, firstname);
                            editor.putString(Config.SHARED_PREF_LastName, lastname);
                            editor.putString(Config.SHARED_PREF_UserID, userID);
                            editor.putString(Config.SHARED_PREF_EntityID,Entity_ID);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Intent intent = new Intent(Login.this.getApplicationContext(), Home_Catogeries.class);
                            startActivity(intent);
                            //  JSONArray regionArray = object.getJSONArray("regions");

//                    for (int i = 0; i < arrr.length(); i++) {
//
//                        JSONObject obj = new JSONObject(arrr.getString(i));
//
//                        String cat_id = obj.getString("categories_id");
//                        String cat_name = obj.getString("categories_name");
//                        //   regionMap.put(cat_id, lmn);
//                        cat_map.put(cat_id, cat_name);
//                        //regionsMapList.add(regionMap);
//                        spinnerDataCatList.add(cat_name);
//                        //   spinnerDataCountry.add(lmn);
//                    }
//
//                    ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(AddSaleActivity.this, R.layout.bg_spinner_item, spinnerDataCatList);
//
//                    spCategories.setAdapter(regionAdapter);


                            // regionsMapList.add()


                            //   String status = object.get("success").toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //  Log.e("Error",error.printStackTrace());
                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", user_email);
                params.put("password", user_pass);
//
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true

        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(this, Home_Catogeries.class);
            startActivity(intent);
        }
    }
}
