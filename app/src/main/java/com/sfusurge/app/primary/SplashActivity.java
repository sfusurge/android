package com.sfusurge.app.primary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.sfusurge.app.primary.service.PreferencesManager;

public class SplashActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private static int DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fade transition
        // Must be called before setting ContentView
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setExitTransition(new Fade());

        setContentView(R.layout.activity_splash);

        // Hide both the navigation bar and the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        final Activity context = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Class nextClass = fieldsSet() ? ScanActivity.class : InputActivity.class;
                startActivity(
                        new Intent(getApplicationContext(), nextClass),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(context).toBundle()
                );
                finish();
            }
        }, DELAY);
    }

    // Field is set by button in InputActivity when advancing
    private boolean fieldsSet() {
        return PreferencesManager.getBoolean(this, InputActivity.fieldsSet);
    }
}
