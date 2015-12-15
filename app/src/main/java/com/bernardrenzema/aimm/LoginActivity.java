package com.bernardrenzema.aimm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bernardrenzema.aimm.entities.AppUser;
import com.bernardrenzema.aimm.entities.Callback;
import com.bernardrenzema.aimm.entities.RestClient;
import com.bernardrenzema.aimm.tools.SnackbarHelper;

public class LoginActivity extends FragmentActivity {

    private static final String     TAG = "LoginActivity";

    private EditText                mPassword;
    private Button                  mConnect;
    private RelativeLayout          mLoading;

    private boolean				    canExit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPassword = (EditText) findViewById(R.id.password);
        mConnect = (Button) findViewById(R.id.connect);
        mLoading = (RelativeLayout) findViewById(R.id.loading);

        mConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passsword = mPassword.getText().toString();

                if (passsword.length() > 0) {
                    mLoading.setVisibility(View.VISIBLE);
                    AppUser.connect(LoginActivity.this, "asusMemoPad7", passsword, new Callback() {
                        @Override
                        public void onSuccess() {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);

                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            LoginActivity.this.startActivity(i);
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onFailure(int code, String error) {
                            mLoading.setVisibility(View.GONE);
                            new SnackbarHelper().make(findViewById(android.R.id.content), error)
                                    .show();
                        }
                    });
                }
            }
        });

        RestClient.newRestClient(this);
        CookieManager cookieManager = CookieManager.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                public void onReceiveValue(Boolean value) {
                }
            });
        } else {
            cookieManager.removeAllCookie();
        }
    }

    @Override
    public void onBackPressed() {
        if (canExit) {
            super.onBackPressed();
            finish();
        }
    }
}
