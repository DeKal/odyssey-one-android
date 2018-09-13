package com.odyssey.one.app.reference;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tan_pc.navigationdraweractivity.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static SettingsSQLite.SqliteHelper.TABLE_SETTINGS;
import static com.odyssey.one.app.reference.DeviceInfo.DisplayRow;
import static com.odyssey.one.app.activity.MainActivity.PROJECTDATABASE;
import static com.odyssey.one.app.activity.MainActivity.delayImage;
import static com.odyssey.one.app.activity.MainActivity.mTcpClient;
import static com.odyssey.one.app.activity.MainActivity.milliseconds;
import static com.odyssey.one.app.activity.MainActivity.serialCom;
import static com.odyssey.one.app.activity.MainActivity.thresholeNumber;
import static com.odyssey.one.app.activity.MainActivity.vanNumber;

/**
 * A simple {@link Fragment} subclass.
 */

public class DisplayTextFragment extends Fragment {
    LinearLayout TextLinearLayout;
    LinearLayout layoutMaskText;
    ScrollView layoutShowTextView;
    LinearLayout layoutLinearFormatText;
    TextView txtReviewDisplayText;
    TextView txtShowHide;
    EditText edtSendText;
    SeekBar seekBarLineSpacing;
    SeekBar seekBarTextSize;
    ImageView imageClear;
//    RadioButton radioLetterByLetter;
//    RadioButton radioWordByWord;

    Button btnSendText;
    private Bitmap image;
    private WaterImage imgTextImage;
    Bitmap bitmapToSend;
    int height_layout;
    int width_layout;
    TextView textviewtemp;

    RelativeLayout layout;
    CheckBox cbRepeatText ;
    boolean isAllowSending=true;
    boolean isStopSending =false;
    boolean isRepeat=false;
   private ConvertImage convertingProcess;
    private SendText sendText;
    //khi xoay man hinh thi khong bi giu nguyen layout
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        ViewGroup rootView = (ViewGroup) getView();
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View newview = inflater.inflate(R.layout.fragment_display_text, rootView, false);
//        //ViewGroup rootView = (ViewGroup) getView();
//        // Remove all the existing views from the root view.
//        // This is also a good place to recycle any resources you won't need anymore
//        rootView.removeAllViews();
//        rootView.addView(newview);
//        InitializeComponent(newview);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_display_text, container, false);
        InitializeComponent(view);
        return view;
    }

    private void InitializeComponent(View v) {
        //load into Valves
        ReflectAndListener(v);
    }

    public void ReflectAndListener(View view) {

        TextLinearLayout = (LinearLayout) view.findViewById(R.id.TextLinearLayout);
        TextLinearLayout.setOnClickListener(btnClickListener);
        layoutShowTextView =(ScrollView) view.findViewById(R.id.layoutShowTextView) ;
        txtReviewDisplayText = (TextView) view.findViewById(R.id.txtReviewDisplayText);
        imageClear = (ImageView) view.findViewById(R.id.imageClear);
        txtShowHide =(TextView) view.findViewById(R.id.txtShowHide);
        txtShowHide.setText("  Show  ▼  ");
        txtShowHide.setOnClickListener(btnClickListener);
        layoutMaskText= (LinearLayout) view.findViewById(R.id.layoutMaskText);
        layoutLinearFormatText=  (LinearLayout) view.findViewById(R.id.layoutLinearFormatText);;;

        edtSendText = (EditText) view.findViewById(R.id.edtSendText);
        edtSendText.setOnClickListener(btnClickListener);
        edtSendText.setOnFocusChangeListener(edtFocusChange);
        edtSendText.setOnKeyListener(edtOnKeyListener);
        seekBarLineSpacing =(SeekBar)view.findViewById(R.id.seekBarLineSpacing);
        seekBarTextSize=(SeekBar)view.findViewById(R.id.seekBarTextSize);
        seekBarLineSpacing.setProgress(50);
        seekBarLineSpacing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
//                    imageSizeView.getLayoutParams().height = (progress+1) * 10;
//                    imageSizeView.getLayoutParams().width = 100;
//                    mPaint.setStrokeWidth(((progress+1) * 10));
//                    imageSizeView.requestLayout();
                    txtReviewDisplayText.setLineSpacing(progress*4,1f);

                }catch (Exception e){
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBarTextSize.setProgress(50);
        seekBarTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
//                    imageSizeView.getLayoutParams().height = (progress+1) * 10;
//                    imageSizeView.getLayoutParams().width = 100;
//                    mPaint.setStrokeWidth(((progress+1) * 10));
//                    imageSizeView.requestLayout();
                    txtReviewDisplayText.setTextSize((float)1.3*(progress+10));

                }catch (Exception e){
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        edtSendText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.edtSendText) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
//        radioGroupText = (RadioGroup) view.findViewById(R.id.radioGroupText);
//
//        radioLetterByLetter = (RadioButton) view.findViewById(R.id.radioLetterByLetter);
//        radioLetterByLetter.setOnClickListener(btnClickListener);
//        radioLetterByLetter.setOnCheckedChangeListener(checkedChangedListenner);
//
//        radioWordByWord = (RadioButton) view.findViewById(R.id.radioWordByWord);
//        radioWordByWord.setOnClickListener(btnClickListener);
//        radioWordByWord.setOnCheckedChangeListener(checkedChangedListenner);

        btnSendText = (Button) view.findViewById(R.id.btnSendText);
        btnSendText.setOnClickListener(btnClickListener);
        cbRepeatText =(CheckBox)view.findViewById(R.id.cbRepeatText);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        radioGroupText.clearCheck();
//        radioGroupText.setOnCheckedChangeListener(radioCheckedChange);
//        radioGroupText.check(R.id.radioWordByWord);
        txtReviewDisplayText.setText("Here!\nShow\nText\nYour");
        height_layout=layoutShowTextView.getHeight();
        width_layout=layoutShowTextView.getWidth();

        layout = new RelativeLayout(getActivity());
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        textviewtemp=new TextView(getActivity());
        imageClear.setOnClickListener(btnClickListener);
        edtSendText.setOnClickListener(btnClickListener);
        edtSendText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean a = edtSendText.getText().toString().trim().isEmpty();
                Log.e("EDIT TEXT", a + "A");
//                if (radioWordByWord.isChecked()) {
//
//                }
                try {
                if(!edtSendText.getText().toString().trim().toString().isEmpty()) {
                    String s = "";
                    s = edtSendText.getText().toString().trim().toString();
                    strArray = s.split("\\s+");
                    String strArray_temp1 = "";
                    if (strArray.length > 93) {
                        strArray_temp = new String[93];
                        for (int i = 0; i < 93; i++) {
//                                strArray_temp=strArray_temp+strArray[i];
                            strArray_temp[i] = strArray[i];
                            strArray_temp1 = strArray_temp1 + strArray[i] + " ";
                        }
                        edtSendText.setText(strArray_temp1);
                        txtReviewDisplayText.setText(reverseString(strArray_temp, 0));
                    }
                    txtReviewDisplayText.setText(reverseString(strArray, 0));
                    layoutShowTextView.invalidate();
                }     else
                {
                    txtReviewDisplayText.setText("Here!\nShow\nText\nYour");
                }


                }catch (Exception e){

                }


//                    } else {
//                        {
//                            strArray_temp = new String[150];
//                            for(int i=0; i<150;i++)
//                            {
////                                strArray_temp=strArray_temp+strArray[i];
//                                strArray_temp[i]=strArray[i];
//                                strArray_temp1=strArray_temp1+strArray[i] +" ";
//                            }
////                            int lengt = strArray.length - (strArray.length - 150);
//                            edtSendText.setText(strArray_temp1);
//                            txtReviewDisplayText.setText(reverseString(strArray_temp, 0));
//                        }
//                        ToastShow("Max word 150");
//                    }
//                }
            }
        });
    }
    public String reverseString(String[] words, int lenght)
    {
        String t="";
        if (lenght==0) {
            for (int i = words.length - 1; i >= 0; i--) {

                if (i != 0) {
                    t = t + words[i] + "\n";
                } else {
                    t = t + words[i];
                }

            }
        }else
        {
               for (int i = lenght - 1; i >= 0; i--) {

                if (i != 0) {
                    t = t + words[i] + "\n";
                } else {
                    t = t + words[i];
                }

            }
        }
        return t;
    }

    private RadioGroup.OnCheckedChangeListener radioCheckedChange = new RadioGroup.OnCheckedChangeListener() {
        @SuppressLint("ResourceType")
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            if (null != rb && checkedId > -1) {

                switch (checkedId) {
//                    case R.id.radioWordByWord:
//                        break;
//                    case R.id.radioLetterByLetter:
//                        break;
                }
            }
        }
    };

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.radioLetterByLetter:
//                    RadioLetterByLetterCheck(true);
//                    break;
//                case R.id.radioWordByWord:
//                    RadioWordByWordCheck(true);
//                    break;
                case R.id.TextLinearLayout:
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
                    break;
                case R.id.edtSendText:
                    showFormat(true);
                    edtSendText.setFocusableInTouchMode(true);
                    edtSendText.setFocusable(true);
                    edtSendText.requestFocus();
                    break;
                case R.id.btnSendText:
                    ButtonSendTextClicked();
                    break;
                case R.id.txtShowHide:
                    TextviewSHowHideClicked();
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
                    break;
                case R.id.imageClear:
                    edtSendText.setText("");
                    break;
            }
        }
    };
    private CompoundButton.OnCheckedChangeListener checkedChangedListenner = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            // SwitchTextCheck(switchActiveTextDisplay.isChecked());
        }
    };
    private View.OnFocusChangeListener edtFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //Switch1check(switch1.isChecked());
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
            }
        }
    };

    public DisplayTextFragment() {
        // Required empty public constructor
    }

    private View.OnKeyListener edtOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            //String s=edtSendText.getText().toString().trim();
            if (((event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL))) {
                // btnSendText.setEnabled(false);
                // btnSendText.setBackgroundColor(Color.parseColor("#d3d3d3"));
                //return true;

            }
            return false;
        }
    };


 public void TextviewSHowHideClicked()
 {
     if(txtShowHide.getText().toString()=="  Show  ▼  ")
     {


         showFormat(false);


//         layoutMaskText.invalidate();
//         layoutLinearFormatText.invalidate();
//         layoutLinearFormatText.requestLayout();
//         layoutMaskText.requestLayout();
     }else if(txtShowHide.getText().toString()=="  Hide  ▲  ")
     {
        showFormat(true);

//         layoutMaskText.invalidate();
//         layoutLinearFormatText.invalidate();
//         layoutLinearFormatText.requestLayout();
//         layoutMaskText.requestLayout();
     }else
     {
         showFormat(true);
     }

 }
 public void showFormat(boolean b)
 {
     if(b)
     {
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
//                 ToastShow(txtShowHide.getText().toString());
                 txtShowHide.setText("  Show  ▼  ");
                 layoutLinearFormatText.setVisibility(View.GONE);
//
                 layoutLinearFormatText.setVisibility(View.GONE);
                 layoutMaskText.setVisibility(View.VISIBLE);
                 TextLinearLayout.requestLayout();
                 TextLinearLayout.invalidate();

             }
         }, 1);
     }
     else
     {

         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
//                ToastShow(txtShowHide.getText().toString());
                 txtShowHide.setText("  Hide  ▲  ");
                 layoutMaskText.setVisibility(View.GONE);
                 layoutLinearFormatText.setVisibility(View.VISIBLE);
                 TextLinearLayout.requestLayout();
                 TextLinearLayout.invalidate();

             }
         }, 1);
     }
 }
    public void ToastShow(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
//
//    public static int countWords(String s) {
//        String trim = s.trim();
//        if (trim.isEmpty())
//            return 0;
//        return trim.split("\\s+").length;
//    }
    final Handler handler=new Handler();
    int count =0;
    String[] strArray;
    String[] strArray_temp;
//    final List<String> listString = new ArrayList<>();
    protected static final long TIME_DELAY = 3000;
    private void ButtonSendTextClicked() {

        if(!txtReviewDisplayText.getText().toString().trim().toString().isEmpty()) {
            if(CheckConnect()){
                if(cbRepeatText.isChecked()){
                    isRepeat =true;
                }else {
                    isRepeat =false;
                }

//                getSettingData();
//                Bitmap bitmapt ;
//                height_layout = layoutShowTextView.getChildAt(0).getHeight();
//                width_layout = layoutShowTextView.getChildAt(0).getWidth();
//                bitmapt =viewToBitmap1(layoutShowTextView,layoutShowTextView.getChildAt(0).getHeight(),layoutShowTextView.getChildAt(0).getWidth());
                //image = DeviceInfo.viewToBitmap(frameLayout);
//            Log.e("CLICKED", "Height , WIDTH: " +bitmap.getHeight() + " " + bitmap.getWidth());
//            Log.e("CLICKED", "Height , Valnumber: " +vanNumber+ " "+thresholeNumber + ""+ " ");
//                Bitmap reBitmap = WaterImage.ResizeImage(bitmapt,vanNumber);
//                Log.e("ReBit Map", "Height , WIDTH: " +reBitmap.getHeight() + " " + reBitmap.getWidth());
//                imgTextImage = new WaterImage(reBitmap);
//                Bitmap bwBitmap = reBitmap;
//
//                bwBitmap = imgTextImage.GetBitmap(reBitmap,vanNumber,thresholeNumber);
//                Log.e("bwBit Map", "Height , WIDTH: " +bwBitmap.getHeight() + " " + bwBitmap.getWidth()+" "+vanNumber);
//                return  bwBitmap;
//            }
//            @Override
//            protected void onPostExecute(Bitmap s) {
//                bitmapToSend = s;
//                try {
//                    saveImageToExternal(DeviceInfo.getCurrentTime(), bwBitmap, getActivity());
//
//                } catch (OutOfMemoryError e) {
//                    e.printStackTrace();
//                    ToastShow("Out of memory!");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                convertingProcess =new ConvertImage();
                convertingProcess.execute();
            }else
            {
                Toast.makeText(getActivity(), "Check your connection!", Toast.LENGTH_SHORT).show();
            }
            //showSendingDialog();

            //bitmap.recycle();
        }else
        {
            ToastShow("Empty!");
        }

//        if(!edtSendText.getText().toString().trim().toString().isEmpty()) {
//            count =0;
//            String s = "";
//            if (radioWordByWord.isChecked()) {
//                s = edtSendText.getText().toString().trim().toString();
//                strArray = s.split("\\s+");
//                handler.post(updateTextRunnable);
//            } else if(radioLetterByLetter.isChecked())
//                {
//                s = edtSendText.getText().toString().trim().toString();
//                String s1  = s.replaceAll("\\s+","").toString();
//                    strArray_temp=s1.split("");
//                    strArray=new String[strArray_temp.length-1];
//                    for(int i =0 ; i<strArray_temp.length;i++)
//                    {
//                        if(i>0) {
//                            strArray[i-1] = strArray_temp[i];
//                        }
//                    }
//                    handler.post(updateTextRunnable);
//                }
//        }
//        else
//        {
//            ToastShow("Empty!");
//        }

    }

    public static Bitmap viewToBitmap1(View view , int height,int width) {
//        view.setDrawingCacheEnabled(true);
//        view.refreshDrawableState();
//        Bitmap bitmap = view.getDrawingCache();
//        view.setDrawingCacheEnabled(false);
//        return bitmap;

        view.setDrawingCacheEnabled(true);
        view.refreshDrawableState();
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(width ,height,  Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        view.destroyDrawingCache();
        return bitmap;

//        Bitmap returnedBitmap = Bitmap.createBitmap(width,height , Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(returnedBitmap);
//        Drawable bgDrawable = view.getBackground();
//        if (bgDrawable != null)
//            bgDrawable.draw(canvas);
//        else
//            canvas.drawColor(Color.WHITE);
//        view.draw(canvas);
//        return returnedBitmap;
//        public static Bitmap loadBitmapFromView(View v, int width, int height) {
//            Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
//            Canvas c = new Canvas(b);
//            view.layout(0, 0, view.getLayoutParams().width, view.getLayoutParams().height);
//            view.draw(c);
//            return b;
    }

    private ProgressDialog convertText2ImageProgressDialog;//Converting
    private ProgressDialog sendTextProgressDialog;//Sending Dialog
    private void dismissConvertingDialog() {
        if (convertText2ImageProgressDialog != null && convertText2ImageProgressDialog.isShowing()) {
            convertText2ImageProgressDialog.dismiss();
        }
    }
    public void showConvertingDialog() {

        if (convertText2ImageProgressDialog == null) {
            convertText2ImageProgressDialog = new ProgressDialog(getActivity());
            convertText2ImageProgressDialog.setTitle("Convert Task" +
                    "" +
                    "" +
                    "");
            convertText2ImageProgressDialog.setMessage("Processing...");
            //progress_auto.setCancelable(false);
            convertText2ImageProgressDialog.setCanceledOnTouchOutside(false);
            convertText2ImageProgressDialog.setCancelable(false);
            convertText2ImageProgressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            convertText2ImageProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            convertText2ImageProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if(convertingProcess !=null &&convertingProcess.getStatus()!=AsyncTask.Status.FINISHED) {
                        convertingProcess.cancel(true);
                        isStopSending=true;
                        isAllowSending=false;
                    }
//                    isAutoShow=false;
                }
            });
        }
        try {
            convertText2ImageProgressDialog.show();
        }catch (Exception e)
        {

        }
    }
    private void dismissSendingDialog() {
        if ( sendTextProgressDialog!= null && sendTextProgressDialog.isShowing()) {
            sendTextProgressDialog.dismiss();
        }
    }
    public void showSendingDialog() {

        if (sendTextProgressDialog == null) {
            sendTextProgressDialog = new ProgressDialog(getActivity());
            sendTextProgressDialog.setTitle("Sending Task" +
                    "" +
                    "" +
                    "");
            sendTextProgressDialog.setMessage("Showing...");
            //progress_auto.setCancelable(false);
            sendTextProgressDialog.setCanceledOnTouchOutside(false);
            sendTextProgressDialog.setCancelable(false);
            sendTextProgressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            sendTextProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            sendTextProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Stop", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isStopSending=true;
                    dialog.dismiss();
//                    isAutoShow=false;
                }
            });
        }
        sendTextProgressDialog.show();
    }


    private class ConvertImage extends AsyncTask<Bitmap, Integer, Bitmap> {
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            if(isAutoShow)
//            {
            try
            {
//                dismissConvertingDialog();

                showConvertingDialog();
                isAllowSending=true;
                isStopSending=false;
                layoutShowTextView.invalidate();
                layoutShowTextView.requestLayout();
                getSettingData();
                //SendImage();
                height_layout = layoutShowTextView.getChildAt(0).getHeight();
                width_layout = layoutShowTextView.getChildAt(0).getWidth();
                bitmap= viewToBitmap1(layoutShowTextView,height_layout,width_layout);

            }catch (Exception e)
            {

            }

//            }else {
//            showSendingDialog();
//            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            if(values[0]<strArray.length) {
//                txtReviewDisplayText.setText(" " + strArray[values[0]] + " ");
//            }
//            else
//            {
//                isStop =true;
//                count=0;
//            }
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
//            Bitmap bitmap;

            //image = DeviceInfo.viewToBitmap(frameLayout);
//            Log.e("CLICKED", "Height , WIDTH: " +bitmap.getHeight() + " " + bitmap.getWidth());
//            Log.e("CLICKED", "Height , Valnumber: " +vanNumber+ " "+thresholeNumber + ""+ " ");
            Bitmap reBitmap = WaterImage.ResizeImage(bitmap,vanNumber);
            Log.e("ReBit Map", "Height , WIDTH: " +reBitmap.getHeight() + " " + reBitmap.getWidth());
            imgTextImage = new WaterImage(reBitmap);
            Bitmap bwBitmap = reBitmap;

            bwBitmap = imgTextImage.GetBitmap(reBitmap,vanNumber,thresholeNumber);
            Log.e("bwBit Map", "Height , WIDTH: " +bwBitmap.getHeight() + " " + bwBitmap.getWidth()+" "+vanNumber);
            return  bwBitmap;
        }
        @Override
        protected void onPostExecute(Bitmap s) {
            bitmapToSend = s;
                try {
                    saveImageToExternal(DeviceInfo.getCurrentTime(), s, getActivity());

                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    ToastShow("Out of memory!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            dismissSendingDialog_auto();
            dismissConvertingDialog();
//            Toast.makeText(getActivity(), "Convert Successful!", Toast.LENGTH_SHORT).show();
//            if(isAllowSending) {
//            try {
//                Thread.sleep(000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
                new SendText().execute(bitmapToSend);
//            }
        }
    }



    private class SendText extends AsyncTask<Bitmap, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            if(isAutoShow)
//            {
//                showSendingDialog_auto();
//            }else {
              showSendingDialog();
            imgTextImage=new WaterImage(bitmapToSend);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            if(values[0]<strArray.length) {
//                txtReviewDisplayText.setText(" " + strArray[values[0]] + " ");
//            }
//            else
//            {
//                isStopSending =true;
//                count=0;
//            }
            sendTextProgressDialog.setProgress(values[0]);
//            Log.e("Process Updated: ", "Percent :  " + values[0]);
        }

        @Override
        protected Boolean doInBackground(Bitmap... voids) {
            boolean isSuccess=false;
            int times = 1;
            try{

//                 Log.e("CLICKED", "Height , WIDTH: " +reBitmap.getHeight() + " " + reBitmap.getWidth());

                int byteRow;
                // Log.e("Watering ", "Sending Image: "+ i );
                byteRow=imgTextImage.getImg().getWidth()/8;
                int sum=0;
                int percent=0;
//                Log.e("Befor Send BitMap", "Height , WIDTH: " +img.getHeight() + " " + img.getWidth()+" "+vanNumber);

                if (isRepeat) {
                    Bitmap bmp = imgTextImage.getImg();
                    while (!isStopSending) {
                        serialCom = new DeviceInfo();
                        serialCom.TxCmdStart();
                        for (int i = 0; i < times; i++) {
                            sum = times * bmp.getHeight();
                            for (int k = bmp.getHeight() - 1; k >= 0; k--) {
                                byte[] data = imgTextImage.RowToByte(k);
                                // Log.e("DisplayImage ", "Data of row: "+ Arrays.toString(data));
//                                if (!isStopSending) {
                                if (DisplayRow(data, milliseconds)) {
                                    isSuccess = true;
                                }
                                percent = ((bmp.getHeight() - k) * 100) / sum;
//                                Log.e("DisplayImage ", "Percent: " + percent);
                                publishProgress(percent);
//                                } else {
//
//                                    isSuccess = false;
//                                }
                            }
                            serialCom.TxCmdStop();
                        }
                        CurtainSleep(byteRow, delayImage*200);
//                    try {
//                        Thread.sleep(delayImage*700);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    }

                }else {
                    Bitmap bmp = imgTextImage.getImg();
                    serialCom = new DeviceInfo();
                    serialCom.TxCmdStart();
                    for (int i = 0; i < times; i++) {
                        sum = times * bmp.getHeight();
                        for (int k = bmp.getHeight() - 1; k >= 0; k--) {
                            byte[] data = imgTextImage.RowToByte(k);
                            // Log.e("DisplayImage ", "Data of row: "+ Arrays.toString(data));
                            if (!isStopSending) {
                                if (DisplayRow(data, milliseconds)) {
                                    isSuccess = true;
                                }
                                percent = ((bmp.getHeight() - k) * 100) / sum;
//                                Log.e("DisplayImage ", "Percent: " + percent);
                                publishProgress(percent);
                            } else {

                                isSuccess = false;
                            }
                        }
                        serialCom.TxCmdStop();
                    }
//                CurtainSleep(byteRow, delayImage*10);
                }
                CurtainSleep(byteRow,3);
                try {
                    Thread.sleep(delayImage *10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }catch (Exception e)
            {
                Log.e("Error" , "Send"+ e.toString());

            }
//                        count++;
//                        publishProgress(count);

//                    }


//                    checkInto=true;
//                }
            return isSuccess;
        }
        @Override
        protected void onPostExecute(Boolean s) {
            dismissSendingDialog();
//            dismissSendingDialog_auto();
            if (s) {
                isStopSending=false;
                bitmapToSend.recycle();
                Toast.makeText(getActivity(), "Sent Successful!", Toast.LENGTH_SHORT).show();


            } else {

                isStopSending=false;
                bitmapToSend.recycle();
                Toast.makeText(getActivity(), "Fail!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private  void saveImageToExternal(String imgName, Bitmap bm, Context context) throws IOException {


        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath + "/folder_name/");
        dir.mkdirs();
        File imageFile = new File(dir, imgName + ".png");

        FileOutputStream out = new FileOutputStream(imageFile);
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
            out.flush();
            out.close();

            // immediately available to the user.
            MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
//                    addImage(path);
                }
            });
        } catch (Exception e) {
            throw new IOException();
        }
    }

    private boolean CurtainSleep( int bytes,int sec) throws IOException {
        byte [] temp= new byte [bytes];
        for (int i=0; i< bytes; i++) {
            if (true) {
                temp[i] = (byte) 0x00;
            }else
            {
                temp[i] = (byte) 0xFF;
            }
        }

        for (int i = 0; i < sec; i++)
        {

            if (!serialCom.TxCmdData(temp))
            {
                //MainForm._myForm.UpdateStatus("Fail put device to sleep!");
                return false;
            }
            //Thread.Sleep(5);
        }
        return true;
    }
    public boolean CheckConnect() {
        boolean a;
        try {
            Log.i("Send Clicked: ", "Send Cliked Listener");
            Log.i("Send Clicked: ", "Datasend.Size>0 ");
            if (mTcpClient != null && mTcpClient.isConnect() == true) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private void getSettingData() {
        Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
        while (cursorCT.moveToNext()) {
            vanNumber = Integer.parseInt(cursorCT.getString(1));
            thresholeNumber = Integer.parseInt(cursorCT.getString(4));
            milliseconds = Integer.parseInt(cursorCT.getString(2));
            delayImage = Integer.parseInt(cursorCT.getString(3));
        }
    }
//                Log.i("Send Clicked: ", "mTcpClient !=null ");
//                if() {
//
//                    ValvesNumText= Integer.parseInt(spinClockValves.getSelectedItem().toString().trim());
//                    if(edtRdelayClock.getText().toString().trim().isEmpty()==false)
//                    {
//                        bw2RowClock= Integer.parseInt(edtRdelayClock.getText().toString().trim());
//                        if (bw2RowClock>0){
//                            if(true)
//                            {
////                                if(isAutoShow)
////                                {
////                                    showSendingDialog_auto();
////                                }else {
////                                    showSendingDialog();
////                                }
//                                new ClockFragment.SendText().execute();
//
//                            }else
//                            {
//                                ToastShow("Idelay not be empty!");
//                            }
//                        }
//                        else
//                        {
//                            ToastShow("Rdelay must be >0");
//                        }
//                    }else
//                    {
//                        ToastShow("Rdelay not be empty!");
//
//                    }
//
//                }else
//                {
//                    Toast.makeText(getActivity(), "Check your connection!", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(getActivity(), "Please connect to Server and try again!!", Toast.LENGTH_SHORT).show();
//            }



    // showNextWord(words)
//         for(int i=0; i<listString.size();i++) {
//             Handler handler = new Handler();
//             final int finalI = i;
//             handler.postDelayed(new Runnable() {
//                 public void run() {
//                     TextView t = (TextView) getView().findViewById(R.id.txtReviewDisplayText);
//                     t.setText(listString.get(finalI));
//                     Log.e("EDIT TEXT2", finalI + listString.get(finalI));
//
//                 }
//             }, 2000);
//
//         }
//    }
}
