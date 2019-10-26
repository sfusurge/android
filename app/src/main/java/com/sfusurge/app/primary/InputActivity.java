package com.sfusurge.app.primary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sfusurge.app.primary.service.PreferencesManager;
import com.sfusurge.app.primary.service.TextAndViewManager;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;

public class InputActivity extends AppCompatActivity {

    private static final int[] inputFieldResourceIds = {
            R.id.first_name_input,
            R.id.last_name_input,
            R.id.sfu_email_input
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        PreferencesManager.setDefaults(this, R.xml.root_preferences);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FloatingActionButton button = findViewById(R.id.set_input_details);

        final Activity context = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputFieldsCompleted()) {
                    startActivity(new Intent(InputActivity.this, ScanActivity.class));
                } else {
                    TextAndViewManager.setVisible(context, R.id.input_warning);
                }
            }
        });
    }

    private boolean inputFieldsCompleted() {
        for (int id : inputFieldResourceIds) {
            if (TextAndViewManager.getValueOfInputField(this, id).isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
