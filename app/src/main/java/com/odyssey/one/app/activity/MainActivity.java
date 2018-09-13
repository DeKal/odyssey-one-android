package com.odyssey.one.app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
//import com.example.tan_pc.navigationdraweractivity.AdminFragment.AdminMainFragment;
import com.odyssey.one.app.admin.AdminMainFragment;
import com.odyssey.one.app.customer.CustomerMainFragment;
import com.odyssey.one.app.reference.DeviceInfo;
import com.odyssey.one.app.employee.EmployeeMainFragment;
import com.example.tan_pc.navigationdraweractivity.R;
import com.odyssey.one.app.reference.SettingsFragment;
import ClientSocket.ClientSocket;
import SettingsSQLite.SqliteHelper;

import static SettingsSQLite.SqliteHelper.TABLE_SETTINGS;
import static com.odyssey.one.app.activity.LoginActivity.typeOfUser;

public class MainActivity extends LocalizationActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static SqliteHelper PROJECTDATABASE;
    public static DrawerLayout drawerLayout;
    public static ClientSocket mTcpClient = null;
    final static String DATABASENAME = "db_Waterfall";
    public RelativeLayout rClock;
    public RelativeLayout rSettings;
    public RelativeLayout rAdminMain;
    public RelativeLayout rCustomerMain;
    public RelativeLayout rEmployeeMain;
    public static int vanNumber;
    public static int thresholeNumber;
    public static int milliseconds = 30;
    public static int delayImage = 1000;
    static public DeviceInfo serialCom;
    int countBackButtonPress = 0;
    DrawerLayout backupmainApp;
    Button btnSignOut;
    int sttfragment = 0;//trang thai dang o fragment nao 0=home; 1=Displaytext;2=Clock; 3=Import....
    AdminMainFragment adminMainFragment = new AdminMainFragment();
    CustomerMainFragment customerMain =new CustomerMainFragment();
//    EmployeeTrackingFragment employeeTrackingFragment = new EmployeeTrackingFragment();
    EmployeeMainFragment employeeMainFragment = new EmployeeMainFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    //Hide Keyboard if click app recent button if keyboard in showing
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                InitializeComponent();
                switch (typeOfUser){
                    case 0:
                        FragmentShow(0);//Admin
                        break;
                    case 1:
                        FragmentShow(1);//Admin
                        break;
                    case 2:
                        FragmentShow(2);//Admin
                        break;
                }
            }
        }, 0);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            countBackButtonPress++;
//            Log.d(this.getClass().getName(), "back button pressed");
            if (countBackButtonPress == 1) {
//                ToastShow("Press Back Again To Run In BackGround");
            } else if (countBackButtonPress == 3) {
                countBackButtonPress++;
//                moveTaskToBack(true);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


//    //Hint the slide
    @Override
    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
//                    int count =  getSupportFragmentManager().getBackStackEntryCount();
//                    if (count == 1){
//                        finish();
//                    } else {
//                        String title = getSupportFragmentManager().getBackStackEntryAt(count - 2).getName();
//                        getSupportFragmentManager().popBackStack();
//                        getSupportActionBar().setTitle(title);
//                    }
                    finish();
                    //Khong Tat Ung Dung Khi nhan nut Back;
//                    MainActivity.super.onBackPressed();
                }
            }
        }, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

//        getMenuInflater().inflate(R.menu.main, menu);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getMenuInflater().inflate(R.menu.main, menu);

                btnSignOut = (Button) findViewById(R.id.btnSignOut);
                if (btnSignOut==null) {
                } else
                {
                    btnSignOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            ToastShow("Signing out");
                            try {
                                Intent mainIntent = new Intent(MainActivity.this,LoginActivity.class);
                                MainActivity.this.startActivity(mainIntent);
                                finish();
                            }catch (Exception e)
                            {
                                ToastShow(e.toString());
                            }

                        }
                    });
                }

            }
        }, 0);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.

                int id = item.getItemId();
                switch (id)
                {
                    case R.id.nav_clock:
                      new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                        FragmentShow(typeOfUser);
                                        }
                                        }, 0);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        }, 100);
                        break;
                    case R.id.nav_settings:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FragmentShow(5);
                            }
                        }, 10);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        }, 100);
                        break;

                         default: new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FragmentShow(5);
                            }
                        }, 10);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        }, 100);
                        break;
                }

        return true;
    }

    private void InitializeComponent() {
        try {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            navigationView.setNavigationItemSelectedListener(this);

            rClock = (RelativeLayout) findViewById(R.id.rClock);
            rSettings = (RelativeLayout) findViewById(R.id.rSettings);
            rAdminMain =(RelativeLayout)findViewById(R.id.rAdminMain) ;
            rCustomerMain =(RelativeLayout)findViewById(R.id.rCustomerMain) ;
            backupmainApp = (DrawerLayout) findViewById(R.id.drawer_layout);
            rEmployeeMain =(RelativeLayout)findViewById(R.id.rEmployeeMain) ;
            ThreelinesLeftShow();
            PROJECTDATABASE = new SqliteHelper(getApplicationContext(), DATABASENAME, null, 1);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());

        }
    }

    private void FragmentShow(int frag) {
        try{
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (frag) {
                case 0: //admin
                    sttfragment = 2;
                    hideAllFragment();
                    rAdminMain.setVisibility(View.VISIBLE);
                    setTitle("Work Tracking Management");

                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.rAdminMain, adminMainFragment)
                            .commit();

//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.rAdminMain, worktracking);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                    break;
                case 1: //Customer
                    sttfragment = 2;
                    hideAllFragment();
                    rCustomerMain.setVisibility(View.VISIBLE);
                    setTitle("Customer Name");

                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.rCustomerMain, customerMain)
                            .commit();

                    break;
                case 2:
                    sttfragment=5;
                    hideAllFragment();
                    rEmployeeMain.setVisibility(View.VISIBLE);
                    setTitle("Employee");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.rEmployeeMain,employeeMainFragment )
                            .commit();
                    break;
                default:
                    sttfragment=5;
                    hideAllFragment();
                    rSettings.setVisibility(View.VISIBLE);
                    setTitle("Settings ");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.rSettings, settingsFragment)
                            .commit();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.rAdminMain, worktracking);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                    break;

            }

        }catch (Exception e){
            ToastShow(e.getMessage().toString());
        }
    }

    //Switch Connect Click
    @Override
    public void onClick(View v) {
        PROJECTDATABASE = new SqliteHelper(getApplicationContext(), DATABASENAME, null, 1);
        Cursor cursorCT = MainActivity.PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
        while (cursorCT.moveToNext()) {
//            IP = cursorCT.getString(5);
//            PORT = Integer.parseInt(cursorCT.getString(6));
        }
    }
     //settings...
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void ThreelinesLeftShow() {
            try {

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer,
                        toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close) {
                    /**
                     * Called when a drawer has settled in a completely closed state.
                     */
                    public void onDrawerClosed(View view) {
                        super.onDrawerClosed(view);
                    }

                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        super.onDrawerSlide(drawerView, slideOffset);
                    }


                    /**
                     * Called when a drawer has settled in a completely open state.
                     */
                    // @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }
                };

                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
                toggle.setDrawerIndicatorEnabled(true);
                drawer.addDrawerListener(toggle);

                toggle.syncState();

            } catch (Exception e) {
                ToastShow(e.getMessage().toString());
            }
    }

    public void setTitle(String title) {
        try{
            getSupportActionBar().setTitle(title);
        }catch (Exception e){
            ToastShow(e.getMessage().toString());
        }

    }

    public void ToastShow(String frag) {
        try{
            Toast.makeText(this, frag, Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }

    }

    public void hideAllFragment() {
        try{
            rClock.setVisibility(View.GONE);
            rCustomerMain.setVisibility(View.GONE);
            rEmployeeMain.setVisibility(View.GONE);
            rSettings.setVisibility(View.GONE);
            rEmployeeMain.setVisibility(View.GONE);
            rAdminMain.setVisibility(View.GONE);
        }catch (Exception e)
        {
            ToastShow(e.getMessage().toString());
        }

    }

}

