package com.akggame.android;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.akggame.akg_sdk.AKG_SDK;
import com.akggame.akg_sdk.MenuSDKCallback;
import com.akggame.akg_sdk.dao.pojo.PurchaseItem;
import com.akggame.akg_sdk.ui.component.FloatingButton;
import com.akggame.akg_sdk.util.CacheUtil;
import com.akggame.akg_sdk.util.DeviceUtil;
import org.jetbrains.annotations.NotNull;

public class GameActivity extends AppCompatActivity implements MenuSDKCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FloatingButton floatingButton = findViewById(R.id.floatingButton);

        Button btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AKG_SDK.INSTANCE.onSDKPayment(GameActivity.this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode == AKG_SDK.SDK_PAYMENT_CODE){
                PurchaseItem payment = data.getParcelableExtra(AKG_SDK.SDK_PAYMENT_DATA);
                //handle your puchase here
            }
        }
    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onSuccessBind(@NotNull String token) {

    }
}
