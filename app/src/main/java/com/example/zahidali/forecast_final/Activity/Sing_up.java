package com.example.zahidali.forecast_final.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zahidali.forecast_final.Config;
import com.example.zahidali.forecast_final.R;

import java.util.HashMap;
import java.util.Map;

public class Sing_up extends AppCompatActivity {
    EditText firstname,lastname,email,password;
    Button Signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        firstname = (EditText) findViewById(R.id.fname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.pswrd);
        Signup=(Button)findViewById(R.id.create);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_email = email.getText().toString().trim();
                /**
                 //         * Validation
                 //
                 //         */
                boolean invalid = false;
                if (firstname.getText().length()==0) {
                    firstname.requestFocus();
                    firstname.setError(Html.fromHtml("<font color='red'>Please Enter Your Name</font>"));
                }
                else if (lastname.getText().length()==0) {
                    lastname.requestFocus();
                    lastname.setError(Html.fromHtml("<font color='red'>Please Enter Username</font>"));
                }
                else if (email.getText().length()==0) {
                    email.requestFocus();
                    email.setError(Html.fromHtml("<font color='red'>Please Enter Your Email</font>"));
                }
                else if (user_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
                    email.requestFocus();
                    email.setError(Html.fromHtml("<font color='red'>Invalid Email Address</font>"));
                }
                else if (password.getText().length()==0) {
                    password.requestFocus();
                    password.setError(Html.fromHtml("<font color='red'>Please Enter A Password</font>"));
                }
                else if (v == Signup) {
                    registerUser();
                }
            }
        });
    }
    private void registerUser() {
        final String ufirstname = firstname.getText().toString().trim();
        final String ulastname = lastname.getText().toString().trim();
        final String uemail = email.getText().toString().trim();
        final String upassword = password.getText().toString().trim();



        final ProgressDialog pd = new ProgressDialog(Sing_up.this);
        pd.setMessage("loading");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_USER_SIGNUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();
                if (response.equals("SuccessfullyRegister")) {

                    Toast.makeText(Sing_up.this.getApplicationContext(), "Signup Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Sing_up.this.getApplicationContext(), Login.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Sing_up.this.getApplicationContext(),response, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //  Log.e("Error",error.printStackTrace());
                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        )

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.SU_KEY_EMAIl, uemail);
                params.put(Config.SU_KEY_FISTANME, ufirstname);
                params.put(Config.SU_KEY_LASTNAME, ulastname);
                params.put(Config.SU_KEY_PASSWORD, upassword);
                return params;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);








//        {
//            //Now will create a StringRequest almost the same as we did before
//
//            class UpdateEmployee extends AsyncTask<Void, Void, String> {
//                ProgressDialog loading;
//
//                @Override
//                protected void onPreExecute() {
//                    super.onPreExecute();
//                    loading = ProgressDialog.show(Sing_up.this, "User Creating...", "Please Wait...", false, false);
//                }
//                @Override
//                protected void onPostExecute(String s) {
//                    super.onPostExecute(s);
//
//                    if (s.equals("SuccessfullyRegister")) {
//                        Intent intent=new Intent(Sing_up.this,Login.class);
//                        startActivity(intent);
//                        loading.dismiss();
//                        Toast.makeText(Sing_up.this.getApplicationContext(), "Signup Successfully", Toast.LENGTH_LONG).show();
//
//                    } else {
//                        loading.dismiss();
//                        Toast.makeText(Sing_up.this.getApplicationContext(),s, Toast.LENGTH_LONG).show();
//                    }
//                }
//                @Override
//                protected String doInBackground(Void... params) {
//                    HashMap<String, String> hashMap = new HashMap<>();
//                    hashMap.put(Config.SU_KEY_EMAIl, uemail);
//                    hashMap.put(Config.SU_KEY_FISTANME, ufirstname);
//                    hashMap.put(Config.SU_KEY_LASTNAME, ulastname);
//                    hashMap.put(Config.SU_KEY_PASSWORD, upassword);
//                    RequestHandler rh = new RequestHandler();
//                    String s = rh.sendPostRequest(Config.URL_USER_SIGNUP, hashMap);
//                    return s;
//                }
//            }
//            UpdateEmployee ue = new UpdateEmployee();
//            ue.execute();
//        }
    }
}
