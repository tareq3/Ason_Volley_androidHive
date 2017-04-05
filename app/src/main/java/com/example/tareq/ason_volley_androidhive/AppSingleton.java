package com.example.tareq.ason_volley_androidhive;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Tareq on 4/3/2017.
 */

public class AppSingleton {

    private static  AppSingleton m_AppSingleTonInstance;  //crating an object okay?

    private RequestQueue mRequestQueue; //need request queue for handling every request
    private ImageLoader mImageLoader;  // Object of  ImageLoader
    private static Context m_context; //create a global context so that we can use it to our local methods

    private AppSingleton(Context context) { //create  a constructor of AppSingleton
        m_context = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap>
                    cache = new LruCache<>(20);

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

    public static synchronized AppSingleton getInstance(Context context){
        if(m_AppSingleTonInstance ==null){
            m_AppSingleTonInstance= new AppSingleton(context);

        }
        return m_AppSingleTonInstance;
    }

    private RequestQueue getRequestQueue() {
if(mRequestQueue ==null){
    mRequestQueue= Volley.newRequestQueue(m_context.getApplicationContext());

}
        return mRequestQueue;
    }

    //using generic Type T as we need to add any type of data on same queue [string,image,int]
    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(tag);
        getRequestQueue().add(req);
    }
    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
    public void cancelPendingRequest(Object tag){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }


}
