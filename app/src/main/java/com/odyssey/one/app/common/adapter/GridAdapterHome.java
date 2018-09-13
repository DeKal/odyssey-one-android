package com.odyssey.one.app.common.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tan_pc.navigationdraweractivity.R;

import java.util.List;

/**
 * Created by tan-pc on 10/17/16.
 */

public class GridAdapterHome extends ArrayAdapter<ImageSingelHome> {

    public GridAdapterHome(Context context,int textViewResourceID){
        super(context, textViewResourceID);
    }
    public GridAdapterHome(Context context, int resource, List<ImageSingelHome> items){
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v =vi.inflate(R.layout.row,null);
        }
        ImageSingelHome p=getItem(position);
        if(p!=null){
            TextView tt1=(TextView)v.findViewById(R.id.txtImageNameHome_row);
            tt1.setText(p.name);
            ImageView imgv=(ImageView)v.findViewById(R.id.imageviewSingelHome_row);
            Bitmap bitmap = BitmapFactory.decodeByteArray(p.image, 0, p.image.length);
            imgv.setImageBitmap(bitmap);
        }
//            convertView = LayoutInflater.from(context).inflate(res, parent, false);
//            img = (ImageView) convertView.findViewById(R.id.img_grid);

//           img.setScaleType(ImageView.ScaleType.FIT_CENTER);
//          img.setAdjustViewBounds(false);
//            img.setPadding(2, 2, 2, 2);

//            int tmp = array.get(position);
//
//            img.setImageResource(tmp);
//
//        }

        return v;
    }
}
