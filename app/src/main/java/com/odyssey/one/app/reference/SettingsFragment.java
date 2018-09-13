package com.odyssey.one.app.reference;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tan_pc.navigationdraweractivity.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static SettingsSQLite.SqliteHelper.TABLE_SETTINGS;
import static com.odyssey.one.app.activity.MainActivity.PROJECTDATABASE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    final String TABLE_NAME = "tblUser";
    final static int bundleApply = 1;

    SpinnerAdapter customSpinnerAdapter;
    ViewGroup container1;
    ArrayAdapter<CharSequence> adapter;
    //Backup
    LinearLayout touchOutsideEdittext;
    String[] valves;
    String[] languages;

//    Switch switch1B;
    Button btnApplyB;
    Button btnDefaultB;
    EditText edt2RowsB;
    EditText edt2ImagesB;
    EditText edtThresholdB;
    EditText edtIPB;
    EditText edtPortB;
    int spinValvesB;
    int spinLanguagesB;
    int spinThemsB;

    private Spinner spinValves;
//    private Switch switch1;
    private EditText edt2Rows;
    private EditText edt2Images;
    private EditText edtThreshold;
    private EditText edtIP;
    private EditText edtPort;
    private Spinner spinLanguages;
    private Spinner spinThems;
    private Button btnDefault;
    private Button btnApply;

    Spanned[] spannedStringsValves = new Spanned[12];
    Spanned[] spannedStringsLanguages = new Spanned[3];

    public SettingsFragment() {
        // Required empty public constructor

    }

    //Check Ip Correct
    public static boolean validIP(String ip) {
        if (ip == null || ip.isEmpty()) return false;
        ip = ip.trim();
        if ((ip.length() < 6) & (ip.length() > 15)) return false;

        try {
            Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        } catch (PatternSyntaxException ex) {
            return false;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

//        InitializeComponent(v);
        return v;
    }

    public void InitializeComponent(final View v) {
        //load into Valves
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ReflectAndListener(v);

                IPformat(edtIP);//define IP
                PortFormat(edtPort); //define Port
                //Load Data From Database to Settings Fragment
                //spinnerLoad(true);
                        ReflectAndListener(v);
                spinnerLoad(true);
                spinThems.setAdapter(new SpinnerAdapter(getContext()));
                touchOutsideEdittext.setOnClickListener(btnClickListener);
                btnApply.setOnClickListener(btnClickListener);
                btnDefault.setOnClickListener(btnClickListener);


                spinValves.setOnItemSelectedListener(itemSelectedListener);
                spinLanguages.setOnItemSelectedListener(itemSelectedListener);
                spinThems.setOnItemSelectedListener(itemSelectedListener);

                edt2Rows.setOnClickListener(btnClickListener);
                edt2Rows.setOnFocusChangeListener(edtFocusChange);
                edt2Rows.setOnKeyListener(edtOnKeyListener);
                edt2Rows.addTextChangedListener(edtTextChangeListener);

                edt2Images.setOnClickListener(btnClickListener);
                edt2Images.setOnFocusChangeListener(edtFocusChange);
                edt2Images.setOnKeyListener(edtOnKeyListener);
                edt2Images.addTextChangedListener(edtTextChangeListener);

                edtThreshold.setOnClickListener(btnClickListener);
                edtThreshold.setOnFocusChangeListener(edtFocusChange);
                edtThreshold.setOnKeyListener(edtOnKeyListener);
                edtThreshold.addTextChangedListener(edtTextChangeListener);

                edtIP.setOnClickListener(btnClickListener);
                edtIP.setOnFocusChangeListener(edtFocusChange);
                edtIP.setOnKeyListener(edtOnKeyListener);
                edtIP.addTextChangedListener(edtTextChangeListener);

                edtPort.setOnClickListener(btnClickListener);
                edtPort.setOnFocusChangeListener(edtFocusChange);
                edtPort.setOnKeyListener(edtOnKeyListener);
                edtPort.addTextChangedListener(edtTextChangeListener);
//                DisplaySettingsValue();
//                Switch1check(false);
            }
        }, 50);

    }

    public void SetButtondisableOrEnable(Button btn, boolean b) {
        try {
            if (b == true) {
                btn.setBackgroundColor(Color.parseColor("#269999"));
                btn.setEnabled(true);
            } else {
                btn.setBackgroundColor(Color.parseColor("#d3d3d3"));
                btn.setEnabled(false);
            }
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    public void ReflectAndListener(View view) {
        touchOutsideEdittext = (LinearLayout) view.findViewById(R.id.touchOutsideEdittext);
//
//        spinValves = (Spinner) view.findViewById(R.id.spinner_van);
////        switch1 = (Switch) view.findViewById(R.id.switch1);
//        edt2Rows = (EditText) view.findViewById(R.id.edt2Rows);
//        edt2Rows.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//        edt2Rows.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//        edt2Images = (EditText) view.findViewById(R.id.edt2Images);
//        edt2Images.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//        edt2Images.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//        edtThreshold = (EditText) view.findViewById(R.id.edtThreshold);
//        edtThreshold.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//        edtThreshold.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//        edtIP = (EditText) view.findViewById(R.id.edtIP);
//        edtIP.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//        //edtIP.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//        edtPort = (EditText) view.findViewById(R.id.edtPort);
//        edtPort.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//        edtPort.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//        spinLanguages = (Spinner) view.findViewById(R.id.spinLanguage);
//        spinThems = (Spinner) view.findViewById(R.id.spinTheme);
//
//        btnDefault = (Button) view.findViewById(R.id.btnSetDefault);
//        btnApply = (Button) view.findViewById(R.id.btnApply);

    }

    private CompoundButton.OnCheckedChangeListener checkedChangedListenner = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//            Switch1check(switch1.isChecked());
        }
    };
    private View.OnKeyListener edtOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
//                        Switch1check(switch1.isChecked());
                        return true;
                }
            }
            return false;
        }
    };
    private View.OnFocusChangeListener edtFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

//            switch (v.getId()) {
//                case R.id.edt2Rows:
            try {
                if (!hasFocus) {
                    //Switch1check(switch1.isChecked());
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
                }
            } catch (Exception e) {
                ToastShow(e.getMessage().toString());
            }

        }
    };

    private TextWatcher edtTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            if (Unchange() == true) {
//               // SetButtondisableOrEnable(btnApply, false);
//            } else {
//               // SetButtondisableOrEnable(btnApply, true);
//            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //check anchange values from Database
//    public boolean Unchange() {
//        boolean unchangeEdt = true;
//        Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
//        while (cursorCT.moveToNext()) {
//            //                Log.e( "UFFFF", String.valueOf((edt2Rows.getText().toString().equals( cursorCT.getString(2)))&
////                        (edt2Images.getText().toString().equals(cursorCT.getString(3)))&
////                        (edtThreshold.getText().toString().equals(cursorCT.getString(4)))&
////                        (edtIP.getText().toString().equals(cursorCT.getString(5)))&
////                        (edtPort.getText().toString().equals(cursorCT.getString(6)))&
////                        (spinValves.getSelectedItemPosition()== cursorCT.getInt(1))&
////                        (spinLanguages.getSelectedItemPosition()==cursorCT.getInt(7))&
////                        (spinThems.getSelectedItemPosition()==cursorCT.getInt(8))
////                ));
//            unchangeEdt = (edt2Rows.getText().toString().equals(cursorCT.getString(2))) &
//                    (edt2Images.getText().toString().equals(cursorCT.getString(3))) &
//                    (edtThreshold.getText().toString().equals(cursorCT.getString(4))) &
//                    (edtIP.getText().toString().equals(cursorCT.getString(5))) &
//                    (edtPort.getText().toString().equals(cursorCT.getString(6))) &
//                    (spinValves.getSelectedItemPosition() == cursorCT.getInt(1)) &
//                    (spinLanguages.getSelectedItemPosition() == cursorCT.getInt(7)) &
//                    (spinThems.getSelectedItemPosition() == cursorCT.getInt(8));
//        }
////        Log.e( "Result",String.valueOf(unchangeEdt));
//        return unchangeEdt;
//    }


    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            if (Unchange() == true) {
//                //SetButtondisableOrEnable(btnApply, false);
//            } else {
//               // SetButtondisableOrEnable(btnApply, true);
//            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //SetButtondisableOrEnable(btnApply,true);
            return;
        }
    };
    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                switch (view.getId()) {
//                    case R.id.switch1:
//                        Switch1check(switch1.isChecked());
//                        break;
//                    case R.id.btnApply:
//                        ButtonApplyClicked();
//                        break;
//                    case R.id.btnSetDefault:
//                        ButtonDefaultClicked();
//                        break;
//                    case R.id.edt2Rows:
//                        //edt2Rows.setFocusableInTouchMode(false);
//                        edt2Rows.setFocusable(true);
//                        edt2Rows.requestFocus();
//                        edt2Rows.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//                        break;
//                    case R.id.edt2Images:
//                        //edt2Images.setFocusableInTouchMode(true);
//                        edt2Images.setFocusable(true);
//                        edt2Images.requestFocus();
//                        edt2Images.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//                        //showKeyBoard(edt2Images);
//                        break;
//                    case R.id.edtThreshold:
//                        //edtThreshold.setFocusableInTouchMode(true);
//                        edtThreshold.setFocusable(true);
//                        edtThreshold.requestFocus();
//                        edtThreshold.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//                        //showKeyBoard(edtThreshold);
//
//                        break;
//                    case R.id.edtIP:
//                        //edtIP.setError(null);
//                        //edtIP.setFocusableInTouchMode(true);
//                        edtIP.setFocusable(true);
//                        edtIP.requestFocus();
//                        edtIP.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//                        //showKeyBoard(edtIP);
//
//                        break;
//                    case R.id.edtPort:
//                        //edtPort.setFocusableInTouchMode(true);
//                        edtPort.setFocusable(true);
//                        edtPort.requestFocus();
//                        edtPort.setImeOptions((EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI));
//                        //showKeyBoard(edtPort);
//                        break;
//                    case R.id.touchOutsideEdittext://dfg
//                        edtIP.setError(null);
//                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//                        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getApplicationWindowToken(), 0);
//                        break;
                }
            } catch (Exception e) {
                ToastShow(e.getMessage().toString());
            }

        }
    };

    public void ButtonApplyClicked() {
        //checkEditTextNotnull();
        if (checkEditTextNotnull() == true) {
            try {
                new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                PROJECTDATABASE.updateSettings(
                        Integer.parseInt(spinValves.getSelectedItem().toString().trim()),
                        Integer.parseInt(edt2Rows.getText().toString()),
                        Integer.parseInt(edt2Images.getText().toString()),
                        Integer.parseInt(edtThreshold.getText().toString()),
                        edtIP.getText().toString(),
                        Integer.parseInt(edtPort.getText().toString()),
                        spinLanguages.getSelectedItemPosition(),
                        spinThems.getSelectedItemPosition());
            }
                    }, 100);
//                Switch1check(false);
//                switch1.setChecked(false);
                PROJECTDATABASE.close();
                ToastShow("Your Settings Have Been Saved!");

                Bundle isValvesChange = new Bundle();
                isValvesChange.putString("ValvesChange", spinValves.getSelectedItem().toString());

                Intent intent = getActivity().getIntent();
                intent.putExtras(isValvesChange);
//                Fragment fragment = new Fragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("resultApply", bundleApply);
//                fragment.setArguments(bundle);

//                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            } catch (Exception e) {
                ToastShow("Failed When Saving To Database Error:\r\n" + e.getMessage().toString());
            }
        } else {
        }
    }


    public boolean checkEditTextNotnull() {
        boolean edttrue = true;
        String s = "This field cannot be blank!";
        if (edt2Rows.getText().toString().trim().length() <= 0) {
            SetError(edt2Rows, s);
            edttrue = false;
        } else if (edt2Images.getText().toString().trim().length() <= 0) {
            SetError(edt2Images, s);
            edttrue = false;
        } else if (edtThreshold.getText().toString().trim().length() <= 0) {
            SetError(edtThreshold, s);
            edttrue = false;
        } else if (edtIP.getText().toString().trim().length() <= 0) {
            SetError(edtIP, s);
            edttrue = false;
        } else if (edtPort.getText().toString().trim().length() <= 0) {
            SetError(edtPort, s);
            edttrue = false;
        } else if (validIP(edtIP.getText().toString()) == false) {
            SetError(edtIP, "Wrong IP format");
            edttrue = false;
        } else edttrue = true;
        return edttrue;
    }

    public void SetError(EditText edt, String err) {
        try {
//            switch1.setChecked(true);
            Drawable dr = getResources().getDrawable(R.drawable.error);
            edt.setError(null);
            edt.setError(err, dr);
            edt.setFocusableInTouchMode(true);
            edt.requestFocus();
            int position = edt.getText().length();
            Editable editObj = edt.getText();
            Selection.setSelection(editObj, position);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    public void ToastShow(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    public void DisplaySettingsValue() {
        try {
            Cursor cursorCT = PROJECTDATABASE.GetData("SELECT * FROM " + TABLE_SETTINGS);
            while (cursorCT.moveToNext()) {
                int value =Integer.parseInt(cursorCT.getString(1));
                switch(value) {
                    case 320:
                        spinValves.setSelection(0);
                        break;
                    case 288:
                        spinValves.setSelection(1);
                        break;
                    case 256:
                        spinValves.setSelection(2);
                        break;
                    case 224:
                        spinValves.setSelection(3);
                        break;
                    case 192:
                        spinValves.setSelection(4);
                        break;
                    case 160:
                        spinValves.setSelection(5);
                        break;
                    case 128:
                        spinValves.setSelection(6);
                        break;
                    case 96:
                        spinValves.setSelection(7);
                        break;
                    case 64:
                        spinValves.setSelection(8);
                        break;
                    case 32:
                        spinValves.setSelection(9);
                        break;
                    case 16:
                        spinValves.setSelection(10);
                        break;
                    case 8:
                        spinValves.setSelection(11);
                        break;
                }
                edt2Rows.setText(cursorCT.getString(2));
                edt2Images.setText(cursorCT.getString(3));
                edtThreshold.setText(cursorCT.getString(4));
                edtIP.setText(cursorCT.getString(5));
                edtPort.setText(cursorCT.getString(6));
                spinLanguages.setSelection(cursorCT.getInt(7));
                spinThems.setSelection(cursorCT.getInt(8));
            }
        } catch (Exception e) {
            ToastShow("Error When Display Settings Values\r\n" + e.getMessage().toString());
        }
    }

    public void ButtonDefaultClicked() {
        try {
            Switch1check(true);
            edtIP.setError(null);
            edt2Rows.setText("1");
            edt2Images.setText("1");
            edtThreshold.setText("128");
            edtIP.setText("192.168.4.1");
            edtPort.setText("1234");
            spinLanguages.setSelection(0);
            spinThems.setSelection(0);
            spinValves.setSelection(0);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    //switch=checked =>enable edit else disable
    public void Switch1check(boolean b) {
        // spinnerLoad(b);
        try {
            spinValves.setEnabled(b);
            spinLanguages.setEnabled(b);
            spinThems.setEnabled(b);
            edt2Rows.setEnabled(b);
            edt2Images.setEnabled(b);
            edtThreshold.setEnabled(b);
            edtIP.setEnabled(b);
            edtPort.setEnabled(b);
            edt2Rows.setFocusableInTouchMode(b);
            edt2Rows.setFocusable(b);
            edt2Images.setFocusableInTouchMode(b);
            edt2Images.setFocusable(b);
            edtThreshold.setFocusableInTouchMode(b);
            edtThreshold.setFocusable(b);
            edtIP.setFocusableInTouchMode(b);
            edtIP.setFocusable(b);
            edtPort.setFocusableInTouchMode(b);
            edtPort.setFocusable(b);
            SetButtondisableOrEnable(btnDefault, b);
            //SetButtondisableOrEnable(btnApply, false);
            if (b == true) {

                edtIP.setTextColor(Color.parseColor("#269999"));
                edtPort.setTextColor(Color.parseColor("#269999"));
                edt2Rows.setTextColor(Color.parseColor("#269999"));
                edt2Images.setTextColor(Color.parseColor("#269999"));
                edtThreshold.setTextColor(Color.parseColor("#269999"));
//                valves = getActivity().getResources().getStringArray(R.array.valvesEnable);
//                spinnerLoad(true);
                //          edtIP.setBackground(originalDrawable2Rows);
            }
//            else {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        DisplaySettingsValue();
//                    }
//                }, 0);
////                DisplaySettingsValue();
//                edtIP.setTextColor(Color.parseColor("#d3d3d3"));
//                edtPort.setTextColor(Color.parseColor("#d3d3d3"));
//                edt2Rows.setTextColor(Color.parseColor("#d3d3d3"));
//                edt2Images.setTextColor(Color.parseColor("#d3d3d3"));
//                edtThreshold.setTextColor(Color.parseColor("#d3d3d3"));
////                valves = getActivity().getResources().getStringArray(R.array.valvesEnable);
////                spinnerLoad(false);
//
//            }
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    void spinnerLoad(boolean boo) {
        try {
            if (boo == true) {
                //Load Valves enanble Color
                valves = getActivity().getResources().getStringArray(R.array.valvesEnable);
                //Load Languages enable Color
            } else {
                //Load Valves Disable Color
                valves = getActivity().getResources().getStringArray(R.array.valvesDisable);
                //Load Languages Disable Color
            }
            languages = getActivity().getResources().getStringArray(R.array.languagesEnable);
            //Valves Adapter
            for (int i = 0; i < valves.length; i++) {
                spannedStringsValves[i] = Html.fromHtml(valves[i]);
            }
            spinValves.setAdapter(new ArrayAdapter<CharSequence>(getActivity(),
                    R.layout.spinner_item_valves, spannedStringsValves));
            //languages Adapter

            for (int i = 0; i < languages.length; i++) {
                spannedStringsLanguages[i] = Html.fromHtml(languages[i]);
            }
            spinLanguages.setAdapter(new ArrayAdapter<CharSequence>(getActivity(),
                    R.layout.support_simple_spinner_dropdown_item, spannedStringsLanguages));
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    private void BackupComponen() {
        try {
            Button btnDefaultB;
            edtIPB = edtIP;
            edtPortB = edtPort;
            edt2RowsB = edt2Rows;
            edt2ImagesB = edt2Images;
            edtThresholdB = edtThreshold;
            spinValvesB = spinValves.getSelectedItemPosition();
            spinLanguagesB = spinLanguages.getSelectedItemPosition();
            spinThemsB = spinThems.getSelectedItemPosition();
//            switch1B = switch1;
            btnApplyB = btnApply;
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }

    }

    private void RecoverValuesComponent() {
        try {
            //Save status of all component Setting Fragment Before Screen Configchanged
//            switch1.setChecked(switch1B.isChecked());
            edtIP.setText(edtIPB.getText());
            edtPort.setText(edtPortB.getText());
            edt2Rows.setText(edt2RowsB.getText());
            edt2Images.setText(edt2ImagesB.getText());
            edtThreshold.setText(edtThresholdB.getText());
            spinValves.setSelection(spinValvesB);
            spinLanguages.setSelection(spinLanguagesB);
            spinThems.setSelection(spinThemsB);


        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
    }

    //Format Ip in EditText
    private void IPformat(EditText edt) {
        try {
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end,
                                           android.text.Spanned dest, int dstart, int dend) {
                    if (end > start) {
                        String destTxt = dest.toString();
                        String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                        if (!resultingTxt.matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                            return "";
                        } else {
                            String[] splits = resultingTxt.split("\\.");
                            for (int i = 0; i < splits.length; i++) {
                                if (Integer.valueOf(splits[i]) > 255) {
                                    return "";
                                }
                            }
                        }
                    }
                    return null;
                }

            };
            edt.setFilters(filters);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
    }

    //Define Port
    private void PortFormat(EditText edt) {
        try {
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end,
                                           android.text.Spanned dest, int dstart, int dend) {
                    if (end > start) {
                        String destTxt = dest.toString();
                        String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                        if (!resultingTxt.matches("^\\d{1,5}")) {
                            return "";
                        } else {
                            String[] splits = resultingTxt.split("\\.");
                            for (int i = 0; i < splits.length; i++) {
                                if (Integer.valueOf(splits[i]) > 65535) {
                                    return "";
                                }
                            }
                        }
                    }
                    return null;
                }

            };
            edt.setFilters(filters);
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
    }

    //khi xoay man hinh thi khong bi giu nguyen layout
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
//            BackupComponen();
//            ViewGroup rootView = (ViewGroup) getView();
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View newview = inflater.inflate(R.layout.fragment_settings, rootView, false);
//            rootView.removeAllViews();
//            rootView.addView(newview);
//            //Restore Values
//            InitializeComponent(newview);
//            RecoverValuesComponent();
        } catch (Exception e) {
            ToastShow(e.getMessage().toString());
        }
    }

    //Color adapter
    class SpinnerAdapter extends BaseAdapter {
        ArrayList<Integer> colors;
        Context context;

        public SpinnerAdapter(Context context) {
            try {
                this.context = context;
                colors = new ArrayList<Integer>();
                int retrieve[] = context.getResources().getIntArray(R.array.androidcolors);
                for (int re : retrieve) {
                    colors.add(re);
                }
            } catch (Exception e) {
                ToastShow(e.getMessage().toString());
            }
        }

        @Override
        public int getCount() {
            return colors.size();
        }

        @Override
        public Object getItem(int arg0) {
            return colors.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int pos, View view, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
            TextView txv = (TextView) view.findViewById(android.R.id.text1);
            txv.setBackgroundColor(colors.get(pos));
            txv.setTextSize(20f);
            txv.setText("       ");
            //txv.setText("       "+pos);
            return view;
        }
    }
}
