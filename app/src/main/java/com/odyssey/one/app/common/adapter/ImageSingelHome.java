package com.odyssey.one.app.common.adapter;

/**
 * Created by tan-pc on 10/17/16.
 */

public class ImageSingelHome {
    public String name;
    public long width;
    public long height;
    public byte[] image;
    public ImageSingelHome(String name, long width, long height, byte[] image) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.image = image;
    }
}
