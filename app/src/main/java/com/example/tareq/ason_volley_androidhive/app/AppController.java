package com.example.tareq.ason_volley_androidhive.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.tareq.ason_volley_androidhive.AppSingleton;
import com.example.tareq.ason_volley_androidhive.utils.LruBitmapCache;

/**
 * Created by Tareq on 4/5/2017.
 */

public class AppController extends Application {

    public static  final String TAG =AppController.class.getSimpleName();


    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public  static synchronized AppController getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
    public ImageLoader getmImageLoader(){
        getRequestQueue();
        if(mImageLoader == null){
            mImageLoader=new ImageLoader(this.mRequestQueue,new LruBitmapCache());

        }
        return this.mImageLoader;
    }
    public <T> void addToRequestQueue(Request<T> request, String tag){
        //set the default tag if tag is empty
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
        }
    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag){
        if(mRequestQueue !=null){
            mRequestQueue.cancelAll(tag);
        }
    }
}
