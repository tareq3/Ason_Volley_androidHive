package com.example.tareq.ason_volley_androidhive;

import android.content.Context;
import android.graphics.Bitmap; //for image as we know bitmap represents images
import android.support.v4.util.LruCache; //this is L1 cache uses for caching data

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

        mRequestQueue = getRequestQueue(); //getting request queue

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap>    cache = new LruCache<>(20); //making a 20 bit cache

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

    public static synchronized AppSingleton getInstance(Context context){ //creating a static method for in this class as this is singleTon pattern that we are using.
        if(m_AppSingleTonInstance ==null){
            m_AppSingleTonInstance= new AppSingleton(context);

        }
        return m_AppSingleTonInstance;
    }

    //this method handle request queue and make sure there is only one request queue present
    private RequestQueue getRequestQueue() {
if(mRequestQueue == null){
    mRequestQueue= Volley.newRequestQueue(m_context.getApplicationContext()); //creating new request queue

}
        return mRequestQueue;
    }

    //using generic Type T as we need to add multiple type of data on same queue [string,image,int]

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(tag); //setting tag to every request as we need to know the request

        getRequestQueue().add(req); //adding new request in request queue
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
