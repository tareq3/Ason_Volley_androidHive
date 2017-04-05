package com.example.tareq.ason_volley_androidhive.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Tareq on 4/5/2017.
 */


public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{




    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    public LruBitmapCache(){
        this(getDefaultLruCacheSize());
    }

    private static int getDefaultLruCacheSize() {
        final int maxMemory =(int) (Runtime.getRuntime().maxMemory() /1024);
        final int cacheSize=maxMemory /8;
        return cacheSize;
    }


    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url,bitmap);
    }


}
