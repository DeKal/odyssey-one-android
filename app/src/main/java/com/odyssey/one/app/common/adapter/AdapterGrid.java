package com.odyssey.one.app.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.tan_pc.navigationdraweractivity.R;

import java.util.ArrayList;

/**
 * Created by tan-pc on 08/09/2016.
 */
public class AdapterGrid extends ArrayAdapter<Integer> {

    Context context;
    int res;
    ImageView img;
    ArrayList<Integer> array;

    public AdapterGrid(Context context, int resource, ArrayList<Integer> arr) {
        super(context, resource);
        this.context = context;
        res = resource;
        array = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(res, parent, false);
            img = (ImageView) convertView.findViewById(R.id.img_grid);

//           img.setScaleType(ImageView.ScaleType.FIT_CENTER);
//          img.setAdjustViewBounds(false);
//            img.setPadding(2, 2, 2, 2);

            int tmp = array.get(position);

                        img.setImageResource(tmp);

        }

        return convertView;
    }

    @Override
    public int getCount() {
        return array.size();
    }
}
