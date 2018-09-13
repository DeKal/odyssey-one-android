package com.odyssey.one.app.reference;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Tan-UIT on 8/19/2017.
 */

public class WaterImage {
    private Bitmap img;
    private int width;
    private int height;

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public WaterImage(Bitmap img)
    {
        //this.height =height;
        this.img = img;
    }
    public WaterImage(Bitmap img, int valves)
    {
        this.width = valves;
        //this.height =height;
        this.img = img;
    }
    public WaterImage()
    {
    }
    public Bitmap GetBitmap(Bitmap src, int wantedWidth, int threshold) {

//        Bitmap _resized = ResizeImage(src, wantedWidth);
        Bitmap Grayscaled= ConvertToGrayscale(src);
        Bitmap Binaried = GrayscaletoBinary (Grayscaled, threshold );
        this.img =Binaried;
        return Binaried;
    }
      public Bitmap GetBitmapInvert(Bitmap src, int wantedWidth, int threshold) {

//        Bitmap _resized = ResizeImage(src, wantedWidth);
        Bitmap Grayscaled= ConvertToGrayscale(src);
        Bitmap Binaried = GrayscaletoBinaryInvert (Grayscaled, threshold );
        this.img =Binaried;
        return Binaried;
    }
    public static Bitmap ResizeImage(Bitmap bitmap, int wantedWidth) {

        // float heightScale = (float)wantedWidth/(float)bitmap.getWidth();
        int wantedHeight =  (bitmap.getHeight()*wantedWidth)/bitmap.getWidth();
        //       Bitmap output = Bitmap.createBitmap(wantedWidth, (int)wantedHeight, Bitmap.Config.ARGB_8888);
        Bitmap output = Bitmap.createScaledBitmap(bitmap, wantedWidth, wantedHeight, true);
        //  Canvas canvas = new Canvas(output);
        //  Matrix m = new Matrix();
        // m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
        //   canvas.drawBitmap(bitmap, m, new Paint());
        return output;
    }
    //Convert To GrayScale
    public Bitmap ConvertToGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
    //ConVert Grayscale To binary Nomal
    public Bitmap GrayscaletoBinary(Bitmap bmpOriginal, int threshold) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpBinary = Bitmap.createBitmap(bmpOriginal);
        for (int x = 0; x < width; ++x) {
            // progressBarConvertHome.setVisibility(getView().VISIBLE);
            for (int y = 0; y < height; ++y) {
                // get one pixel color
                int pixel = bmpOriginal.getPixel(x, y);
                int red = Color.red(pixel);
                int green=Color.green(pixel);
                int blue= Color.blue(pixel);
                int arg= Color.alpha(pixel);

//                int

                if(x==2 && y ==2) {
                    Log.e("Pixcel 2,2 ", "GrayscaletoBinary: red, green, blue, arg: "+ String.valueOf(red)+" "+ String.valueOf(green)+" "+ String.valueOf(blue) +" "+ String.valueOf(arg));
                }
                //get binary value
//                if(red!=0){
                if(arg >0){
                    if (red < threshold) {
                        bmpBinary.setPixel(x, y, 0xFF000000);
                    } else {
                        bmpBinary.setPixel(x, y, 0xFFFFFFFF);
                    }
                }else {
                    bmpBinary.setPixel(x, y, 0xFFFFFFFF);
                }

//                }
//                else {
//                    bmpBinary.setPixel(x, y, 0xFFFFFFFF);
//                }



            }
        }
        return bmpBinary;
    }
    //ConVert Grayscale To binary Invert
    public Bitmap GrayscaletoBinaryInvert(Bitmap bmpOriginal, int threshold) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpBinary = Bitmap.createBitmap(bmpOriginal);
        for (int x = 0; x < width; ++x) {
            // progressBarConvertHome.setVisibility(getView().VISIBLE);
            for (int y = 0; y < height; ++y) {
                // get one pixel color
                int pixel = bmpOriginal.getPixel(x, y);
                int red = Color.red(pixel);
                int green=Color.green(pixel);
                int blue= Color.blue(pixel);
                int arg= Color.alpha(pixel);
                //get binary value
                if(arg>0){
                      if (red < threshold) {
                        bmpBinary.setPixel(x, y, 0xFFFFFFFF);
                    } else{
                        bmpBinary.setPixel(x, y,0xFF000000 );
                    }

                }else {
                    bmpBinary.setPixel(x, y, 0xFF000000);
                }
            }
        }
        return bmpBinary;
    }
    public byte[] RowToByte(int index)
    {
        if (index < 0 || index >= img.getHeight())
            return null;
        int NumOfByte = (int)((double)img.getWidth()/8);
        byte[] ret = new byte[NumOfByte];
        for (int i = 0; i < img.getWidth(); i++)
        {
            int byteIndex = i >> 3; // devide by 8
            int bitShift = i & 7;   // i modulo 8

            if (img.getPixel(i, index) == Color.argb(0xFF, 0x00, 0x00, 0x00))
            {
                byte mask = (byte)(0x01 << bitShift);
                ret[byteIndex] = (byte)(ret[byteIndex] | mask);
            }
            else
            {
                byte mask = (byte)(~(0x01 << bitShift));
                ret[byteIndex] = (byte)(ret[byteIndex] & mask);
            }
        }
        for(int i=0; i<NumOfByte ;i++)
        {

        }
        return ret;
    }
    public static long getUnsignedInt(byte[]data)
    {
        long result =0;
        for ( int i =0; i < data.length; i++){
            result += data[i] <<8*(data.length -1-i);
            }
        return result;
    }

}
