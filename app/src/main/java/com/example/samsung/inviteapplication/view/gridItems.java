package com.example.samsung.inviteapplication.view;


import android.widget.ImageView;

import com.cunoraz.gifview.library.GifView;

public class gridItems {

    private int gifView;
    private int imageView;
    private String caption;
    private byte[] imgByte;
/*
    public gridItems(int gifView, String caption) {
        this.gifView = gifView;
        this.caption = caption;
    }*/

    public gridItems(byte[] imgByte, String caption) {
        this.imgByte = imgByte;
        this.caption = caption;
    }

    public byte[] getImgByte() {
        return imgByte;
    }

    public void setImgByte(byte[] imgByte) {
        this.imgByte = imgByte;
    }

    public gridItems(int imageView, String caption) {
        this.imageView = imageView;
        this.caption = caption;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public int getGifView() {
        return gifView;
    }

    public void setGifView(int gifView) {
        this.gifView = gifView;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
