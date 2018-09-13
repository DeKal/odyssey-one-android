package com.odyssey.one.app.reference;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ClientSocket.ClientSocket;

import static com.odyssey.one.app.activity.MainActivity.drawerLayout;
import static com.odyssey.one.app.activity.MainActivity.mTcpClient;
import static com.odyssey.one.app.activity.MainActivity.serialCom;


/**
 * Created by leehoa on 11/26/16.
 */

public class DeviceInfo {
    public static int REC_PACKET_MAX = 1024;
    public static int SEND_PACKET_MAX = 1024;
    public static short HEADER_SIZE = 4;
    public static int READ_TIMEOUT = 100;
    public static int CLOSE_TIMEOUT = 1000;

    public DeviceInfo() {
        ClientSocket mTcpClient =null;
    }
    // transmission data

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static byte[] TX_START = {(byte)0x40&0xFF,(byte)0x00&0xFF};
    public static byte[] TX_STOP = {(byte)0x41&0xFF, (byte)0x00&0xFF};
    public static byte[] TX_DATA = {(byte)0x42&0xFF, (byte)0x00&0xFF};
    public static byte[] RX_ACK = {(byte)0x01&0xFF, (byte)0x00&0xFF};

    //private ByteBuffer _sendBuf=
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }




    static final class MyResult1 {
        private final Bitmap bmOut;
       private final char[] string;
        private final byte[] bytes;

        public MyResult1(Bitmap bitmap, char[] string, byte[] bytes) {
            this.bmOut = bitmap;
            this.string = string;
            this.bytes = bytes;
        }

        public Bitmap getBitmap() {
            return bmOut;
        }

        public char[] getString() {
            return string;
        }

        public byte[] getBytes() {
            return bytes;
        }

    }
//    public static byte [] RowToByte(Bitmap src, int index){
//        int width = src.getWidth();
//        int height = src.getHeight();
//
//    }

    public static MyResult1 createBlackAndWhite2(Bitmap src, int threshold) {
        int width = src.getWidth();
        int height = src.getHeight();
        char[] string = new char[width*height];
        // create output bitmap
        Log.i("INFO",height +":" + width);
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        int NumOfByte = height *(width/8);
        byte[] ret = new byte[NumOfByte];


        for (int y = 0; y < height; y++) {//height
            for (int x = 0; x < width; x++) {//width
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

                int byteIndex = ((width*y)+x) >> 3 ; // devide by 8
                int bitShift = (x) & 7;   // i modulo 8

                // use 128 as threshold, above -> white, below -> black
                if (gray < threshold) {

                    gray = 0;
                    string[(y)*width +x] = '1';
                    byte mask = (byte)(0x01 << bitShift);
                    ret[byteIndex] = (byte)(ret[byteIndex] | mask);
                }
                else {
                    gray = 255;
                    string[(y)*width +x]  = '0';

                    byte mask = (byte)(~(0x01 << bitShift));// (0xFE << bitShift);
                    ret[byteIndex] = (byte)(ret[byteIndex] & mask);
                }
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb( A, gray, gray, gray));
            }
        }

        return new MyResult1(bmOut,string, ret);
    }

    public static boolean DisplayRow(byte[]data, int n) throws IOException {
        for (int i = 0; i < n; i++)
        {
            //   Log.e("Row ", "Lan: "+i );
            if (!serialCom.TxCmdData(data))
            {
                return false;
            }
        }
        return true;
    }

//
//
//    public static Bitmap createBlackAndWhite(Bitmap src, int threshold) {
//        int width = src.getWidth();
//        int height = src.getHeight();
//        // create output bitmap
//        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//        // color information
//        int A, R, G, B;
//        int pixel;
//
//        // scan through all pixels
//        for (int x = 0; x < width; ++x) {
//            for (int y = 0; y < height; ++y) {
//                // get pixel color
//                pixel = src.getPixel(x, y);
//                A = Color.alpha(pixel);
//                R = Color.red(pixel);
//                G = Color.green(pixel);
//                B = Color.blue(pixel);
//                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);
//
//                // use 128 as threshold, above -> white, below -> black
//                if (gray > threshold)
//                    gray = 0;
//                else
//                    gray = 255;
//                // set new pixel color to output bitmap
//                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
//            }
//        }
//        return bmOut;
//    }


    public static void openDialog(Context context, String message, String title) {
        TextView gettitle = new TextView(context);
        // You Can Customise your Title here
        gettitle.setText(title);
        gettitle.setBackgroundColor(Color.BLACK);
        gettitle.setPadding(10, 15, 15, 10);
        gettitle.setGravity(Gravity.CENTER);
        gettitle.setTextColor(Color.WHITE);
        gettitle.setTextSize(22);

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCustomTitle(gettitle);
        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

        // You Can Customise your Message here
        TextView messageView = (TextView) alertDialog
                .findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }

    public static void saveImageToExternal(String imgName, Bitmap bm, Context context) throws IOException {
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath + "/folder name/");
        dir.mkdirs();
        File imageFile = new File(dir, imgName + ".png");

        FileOutputStream out = new FileOutputStream(imageFile);
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
            out.flush();
            out.close();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
        } catch (Exception e) {
            throw new IOException();
        }
    }

    public static Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    public static void openSlideMenu() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public static String insert(String bag, String marble, int index) {
        String bagBegin = bag.substring(0,index);
        String bagEnd = bag.substring(index);
        return bagBegin + marble + bagEnd;
    }

//    public  void hideKeyBoard() {
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
//    }

    public boolean TxCmdData(byte[] data) throws IOException {

        ByteBuffer buf = ByteBuffer.allocate(data.length);
        buf.put(data);
        return TxCommandSend(TX_DATA, (short)buf.capacity(), buf);
    }
    public static boolean TxCmdStart() throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.put(TX_START);
     //   Log.e("TxCmdStart", "Chuan bi Send " + Arrays.toString(buf.array()));
     //   return TxCommandSend(TX_START,(short) buf.capacity(),buf);
         mTcpClient.sendBytes(buf.array());
        return  true;
    }
    public boolean TxCmdStop() throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.putShort((short) 1);
        return TxCommandSend(TX_STOP,(short) buf.capacity(),buf);
    }
    static ByteBuffer _sendBuf = ByteBuffer.allocate(1024);


    public static boolean TxCommandSend (byte[] cmd, short size, ByteBuffer buf) throws IOException {
        int TxDataSize;
        short  TxChecksum;
        byte[] TxCmdCode;
        int tmp = 6+buf.array().length;
        _sendBuf= ByteBuffer.allocate(tmp);
     //   Log.e("TxCommandSend", "Da Vao TxCommandSend  " + "Allocate _sendbuff=  "+ tmp + " Array: " + Arrays.toString(buf.array())+ " Leng: " +buf.array().length );
     //    _sendBuf = ByteBuffer.allocate(size+cmd+buf.capacity());
     //   _sendBuf = ByteBuffer.allocate(SEND_PACKET_MAX);;
        //_sendBuf.put(TxSeq);
        TxCmdCode =cmd;
        TxDataSize=size;
        _sendBuf.position(0);
        _sendBuf.put(TxCmdCode);
        _sendBuf.putShort( size);
        _sendBuf.put(buf.array(),0,TxDataSize);

        TxChecksum = 0;
        buf.position(0);
        if (size > 0)
        {

            for (int i = 0; i < TxDataSize; i++)
            {
                TxChecksum += buf.get(i);
            }
        }

        _sendBuf.putShort(TxChecksum);

    //    Log.e("TxCommandSend", "Da Vao TxCommandSend  " + "Allocate _sendbuff sau khi wrap=  "+ _sendBuf.capacity() + "Array: " + Arrays.toString(_sendBuf.array())+ " Leng: " +_sendBuf.array().length );

 //       Log.e("TxCommandSend2", "Da Vao TxCommandSend  " + "size of cmd:  " + bbcmd.capacity() + " Size: " +size + "Array: " + Arrays.toString(buf.array()) );

     //   Log.e("TxCommandSend", "Chuan bi vao sendBytes Array of Buf: " + Arrays.toString(_sendBuf.array()) );

         mTcpClient.sendBytes(_sendBuf.array());
        return true;
    }
//    public static void SendData(byte[] bytes, int time) {
//
//        byte[] chksm = { (byte)0x00, (byte)0x00 };
//        byte[] begin = { (byte)0x42, (byte)0x00 };
//        byte[] size = { (byte)0x00, (byte)0x08 };
//
//        switch(vanNumber){
//            case 192:
//                size = new byte[]{(byte) 0x00, (byte) 0x18};
//                break;
//            case 128:
//                size = new byte[]{(byte) 0x00, (byte) 0x10};
//                break;
//            case 96:
//                size = new byte[]{(byte) 0x00, (byte) 0x0C};
//                break;
//            case 64:
//                size = new byte[]{(byte) 0x00, (byte) 0x08}; //8bytes
//                break;
//            case 32:
//                size = new byte[]{(byte) 0x00, (byte) 0x04}; //4bytes
//                break;
//        }
//
//        try {
//        //    mTcpClient.sendBytes(start);
////            Thread.sleep(milliseconds);
//
//            for (int x = 0; x < time; x++) {
//                mTcpClient.sendBytes(begin);
//                // Log.e("SentBytes", "Begin: " + Arrays.toString(begin) + "Lengh: " + begin.length );
////            Thread.sleep(milliseconds);
//                mTcpClient.sendBytes(size);
//                // Log.e("SentBytes", "Size: " + Arrays.toString(size) + "Lengh: " + size.length );
////            Thread.sleep(milliseconds);
//                mTcpClient.sendBytes(bytes);
//                // Log.e("SentBytes", "Data: " + Arrays.toString(bytes) + "Lengh: " + bytes.length );
//
////            Thread.sleep(milliseconds);
//                mTcpClient.sendBytes(chksm);
//                //   Log.e("SentBytes", "Checksum: " + Arrays.toString(chksm) + "Lengh: " + chksm.length );
//            }
////        } catch (InterruptedException e) {
////            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static void Sendstop() {
        byte[] stop = { (byte)0x41, (byte)0x00 ,(byte)0x00 ,(byte)0x02,(byte)0x00 ,(byte)0x01,(byte)0x00 ,(byte)0x01};
        try {
            mTcpClient.sendBytes(stop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Sendoff() {

        byte[] begin = { (byte)0x42, (byte)0x00 };
        byte[] off = {   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                         (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                         (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                         (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00
                        };
        byte[] size = { (byte)0x00, (byte)0x20 };
        byte[] chksm = { (byte)0x00, (byte)0x00 };
        try {
            mTcpClient.sendBytes(begin);
            mTcpClient.sendBytes(size);
            mTcpClient.sendBytes(off);
            mTcpClient.sendBytes(chksm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

