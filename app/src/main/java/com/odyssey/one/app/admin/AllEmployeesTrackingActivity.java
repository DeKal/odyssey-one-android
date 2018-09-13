package com.odyssey.one.app.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tan_pc.navigationdraweractivity.R;


public class AllEmployeesTrackingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        getSupportActionBar().show();
//        getSupportActionBar().setSelectedNavigationItem(getResources().getDrawable(R.drawable.heart));

        setContentView(R.layout.activity_all_employees_tracking);
//        try {
//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
//
////            final ActionBar ab = this.getSupportActionBar();
////            ab.setHomeAsUpIndicator(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        } catch (Exception e){
//            Log.e("Tan log ", e.toString());
//
//            ToastShow(e.toString());//
//        }
    }
    public void ToastShow(String frag) {
        try{
            Toast.makeText(this, frag, Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }

    }

}
