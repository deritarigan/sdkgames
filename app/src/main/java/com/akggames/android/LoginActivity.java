package com.akggame.android;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.akggame.akg_sdk.AKG_SDK;
import com.akggame.akg_sdk.LoginSDKCallback;
import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AKG_SDK akgsdk= new AKG_SDK(this);


        if (akgsdk.checkIsLogin(this)){
            startActivity(new Intent(LoginActivity.this,Main2Activity.class));
            finish();
        }


        akgsdk.onLogin("Your Game", new LoginSDKCallback() {
            @Override
            public void onResponseSuccess(@NotNull String token) {

            }

            @Override
            public void onResponseFailed(@NotNull String message) {

            }
        });
    }
}
