package com.example.andrewpat24.eventbrowser.interfaceAPI;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ajinkya on 05/12/16.
 */

public class WebMessenger {

    private static WebMessenger sInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;
    private ImageLoader mImageLoader;

    private WebMessenger(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final android.util.LruCache<String, Bitmap>
                            cache = new android.util.LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized WebMessenger getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new WebMessenger(context);
        }
        return sInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelAllRequests(int tag){
        getRequestQueue().cancelAll(tag);
    }

}
