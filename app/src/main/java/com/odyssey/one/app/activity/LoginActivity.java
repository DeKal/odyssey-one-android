package com.odyssey.one.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.example.tan_pc.navigationdraweractivity.R;

import com.odyssey.one.app.common.api.AccountVerification;
import com.odyssey.one.app.common.api.DataAPIService;
import com.odyssey.one.app.common.api.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tan-pc on 8/9/2018.
 */

public class LoginActivity extends LocalizationActivity {

    public static int typeOfUser =99;//0-admin ; 1-customer; 2-worker

    public DataAPIService mApiService = null;
    UserVerificationAdapter mUserVerification = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button emailSignInButton = (Button)findViewById(R.id.btn_login);
        Button enButton = (Button)findViewById(R.id.btnEn);
        Button vnButton = (Button)findViewById(R.id.btnVn);

        enButton.setOnClickListener(setLanguageClick("en"));
        vnButton.setOnClickListener(setLanguageClick("vn"));

        emailSignInButton.setOnClickListener(view -> attemptLogin());

        mApiService = RetrofitClientInstance.getRetrofitInstance().create(DataAPIService.class);
        mUserVerification = new UserVerificationAdapter();
    }

    private View.OnClickListener setLanguageClick(String language) {
        return view -> setLanguage(language);
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    /**
     * Attempts to sign in or register the AccountVerification specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        EditText emailView = (EditText) findViewById(R.id.input_name);
        EditText passwordView = (EditText) findViewById(R.id.input_password);

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String user = emailView.getText().toString().trim();
        String password = passwordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            mUserVerification.verified(user, password);
        }

    }
    void loginError(){
        EditText passwordView = (EditText) findViewById(R.id.input_password);
        passwordView.setError(getString(R.string.error_incorrect_password));
        passwordView.requestFocus();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 1;
    }


    class UserVerificationAdapter implements Callback<AccountVerification>{
        private ProgressDialog progressDialog;

        void generateDataList(AccountVerification accountVerification) throws NullPointerException {
            if (accountVerification.getUserVerified()) {
                typeOfUser = accountVerification.getUserRole();
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(mainIntent);
            }
            else{
                loginError();
            }
        }

        void verified(String user, String password) {
            progressDialog = ProgressDialog.show(LoginActivity.this,"Please wait",
                    "Verifying Users...", false, false);
            Call<AccountVerification> call = mApiService.verifiedAccount(user, password);
            call.enqueue(this);
        }

        @Override
        public void onResponse(Call<AccountVerification> call, Response<AccountVerification> response) {
            try{
                generateDataList(response.body());
            }
            catch (NullPointerException e){
                Log.e(LoginActivity.class.getCanonicalName(),"No response from user verification api");
            }
            finally {
                progressDialog.dismiss();
            }
        }

        @Override
        public void onFailure(Call<AccountVerification> call, Throwable t) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
        }
    }

}
