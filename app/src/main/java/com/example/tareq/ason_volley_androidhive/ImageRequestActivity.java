package com.example.tareq.ason_volley_androidhive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.tareq.ason_volley_androidhive.app.AppController;
import com.example.tareq.ason_volley_androidhive.utils.Const;

import java.io.UnsupportedEncodingException;

public class ImageRequestActivity extends AppCompatActivity {

    private static final String TAG =ImageRequestActivity.class.getSimpleName();

    private Button btnImageReq;
    private NetworkImageView imgNetWorkView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_request);

        btnImageReq= (Button) findViewById(R.id.btnImageReq);
        imgNetWorkView= (NetworkImageView) findViewById(R.id.imgNetwork);
        imageView=(ImageView) findViewById(R.id.imgView);

        btnImageReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeImageRequest();
            }
        });
    }

    private void makeImageRequest() {
        ImageLoader imageLoader= AppController.getInstance().getmImageLoader();
        //if you are using NetworkImageView
        imgNetWorkView.setImageUrl(Const.URL_IMAGE,imageLoader);

        //if you are using normal imageview
        /*imageLoader.get(Const.URL_IMAGE, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if(response.getBitmap() != null){
                    //load image  into imageview
                    imageView.setImageBitmap(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());

            }
        });*/

        //Loading image with placeholder and error image
        imageLoader.get(Const.URL_IMAGE, ImageLoader.getImageListener(
                imageView, R.drawable.ico_loading, R.drawable.ico_error));
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry=cache.get(Const.URL_IMAGE);
        if(entry !=null){

            try {
                String data=new String(entry.data, "UTF-8");
                //handle data, like converting it to xml, json,bitmap etc...
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }else{
            //cached response doesn't exists. make a network call here
        }


    }


}
