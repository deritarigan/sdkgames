package com.akggame.android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.akggame.akg_sdk.AKG_SDK;
import com.akggame.akg_sdk.MenuSDKCallback;
import com.akggame.akg_sdk.ui.component.FloatingButton;
import org.jetbrains.annotations.NotNull;

public class GameActivity extends AppCompatActivity implements MenuSDKCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FloatingButton floatingButton = findViewById(R.id.floatingButton);
        AKG_SDK akgsdk= new AKG_SDK(this);
        akgsdk.setFloatingButton(floatingButton, this, this);

        //call relaunch dialog
        akgsdk.setRelauchDialog(this);
    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onSuccessBind(@NotNull String token) {

    }
}
