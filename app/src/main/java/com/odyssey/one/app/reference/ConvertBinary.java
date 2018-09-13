package com.odyssey.one.app.reference;

import android.database.Cursor;
import android.graphics.Bitmap;

import static SettingsSQLite.SqliteHelper.TABLE_SETTINGS;
import static com.odyssey.one.app.activity.MainActivity.PROJECTDATABASE;

/**
 * Created by TAN-PC on 10/9/2016.
 */

public class ConvertBinary {
    //Rezise Image From ColorImageView
    public Bitmap ResizeColorImage(Bitmap yourBitmap){
        Bitmap resized=null;
        try {
            int iH = yourBitmap.getHeight();//H original
            int iW =  yourBitmap.getWidth();//W original
            // int ih = imageViewColorImageHome.getMeasuredHeight();//height of imageView
            Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
            while (cursorCT.moveToNext()) {
                int id = cursorCT.getInt(1);
                int newWidth = NumberOfValves(id);
                int newHeight = (int) (((float) iH / iW) * newWidth);//newWidth=valves
                resized = Bitmap.createScaledBitmap(yourBitmap, newWidth, newHeight, true);
                //txtSendToHardware.setText(String.valueOf(iH)+" "+String.valueOf(iW)+ " "+ String.valueOf((float)iH/iW)  +" "+newWidth);
                //ToastShow(String.valueOf(iH) + " " + String.valueOf(iW) + " " + String.valueOf((float) iH / iW) + " " + newWidth);
            }//
//                ToastShow(String.valueOf(iH) +" "+ String.valueOf((float)(iH/iW)*NumberValves));
        } catch (Exception e) {

        }
        return   resized ;
    }
     public int NumberOfValves(int id) {
        int s = 0;
        switch (id) {
            case 0:
                s = 192;
                break;
            case 1:
                s = 160;
                break;
            case 2:
                s = 128;
                break;
            case 3:
                s = 96;
                break;
            case 4:
                s = 64;
                break;
            case 5:
                s = 32;
                break;
            case 6:
                s = 16;
                break;
            case 7:
                s = 8;
                break;

        }
        return s;
    }

}
