package com.sfusurge.app.primary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.sfusurge.app.primary.service.PreferencesManager;

public class SplashActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private static int DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide both the navigation bar and the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Class nextClass = fieldsSet() ? ScanActivity.class : InputActivity.class;
                startActivity(new Intent(getApplicationContext(), nextClass));
                finish();
            }
        }, DELAY);
    }

    // Field is set by button in InputActivity when advancing
    private boolean fieldsSet() {
        return PreferencesManager.getBoolean(this, InputActivity.fieldsSet);
    }
}
