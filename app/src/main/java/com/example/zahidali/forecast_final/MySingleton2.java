package com.example.zahidali.forecast_final;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton2 {
    private static MySingleton2 mInstance;
    private static Context ctx;
    private RequestQueue requestQueue;

    private MySingleton2(Context ctx)

    {
        this.ctx=ctx;
        requestQueue=getRequestQueue();
    }

    private RequestQueue getRequestQueue()
    {
        if (requestQueue==null)
        {
           requestQueue= Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }
    public  static synchronized MySingleton2 getmInstance(Context context)
    {
        if (mInstance==null)
        {
            mInstance=new MySingleton2(context);
        }
        return mInstance;
    }
    public  <T>void addToRequestQue(Request<T> request)
    {
        getRequestQueue().add(request);
    }

}
