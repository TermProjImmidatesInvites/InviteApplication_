package com.example.samsung.inviteapplication.view;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.cunoraz.gifview.library.GifView;
import com.felipecsl.gifimageview.library.GifImageView;
import com.example.samsung.inviteapplication.R;
import android.content.Intent;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class splash extends AppCompatActivity {

//    GifView gifView = (GifView) findViewById(R.id.gif_splash);
    private  GifImageView gifImageView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        gifImageView = (GifImageView) findViewById(R.id.gif_splash);
        try {
            InputStream inputStream = getAssets().open("logo1.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException e) {
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3400);

        //logo 2 3500
    }
}

