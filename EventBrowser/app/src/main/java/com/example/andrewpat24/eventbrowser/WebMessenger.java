package com.example.andrewpat24.eventbrowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ajinkya on 05/12/16.
 */

public class WebMessenger {

    private static WebMessenger mInstance;
    private RequestQueue mRequestQueue;
    private static Context sContext;

    private WebMessenger(Context context) {
        sContext = context;
        mRequestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(sContext.getApplicationContext());
        }
        return mRequestQueue;
    }
    public static synchronized WebMessenger getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WebMessenger(context);
        }
        return mInstance;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
