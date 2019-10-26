package com.sfusurge.app.primary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sfusurge.app.primary.service.Preferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

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

        Preferences.setDefaults(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FloatingActionButton button = findViewById(R.id.set_input_details);

        final Activity context = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputFieldsCompleted()) {
                    startActivity(new Intent(InputActivity.this, ScanActivity.class));
                } else {
                    setVisible(context, R.id.input_warning);
                }
            }
        });
    }

    private boolean inputFieldsCompleted() {
        for (int id : inputFieldResourceIds) {
            if (getValueOfInputField(this, id).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static String getValueOfInputField(Activity context, int resourceId) {
        EditText text = context.findViewById(resourceId);
        return text.getText().toString();
    }

    private static void setVisible(Activity context, int resourceId) {
        context.findViewById(resourceId).setVisibility(View.VISIBLE);
    }

    private static void setInvisible(Activity context, int resourceId) {
        context.findViewById(resourceId).setVisibility(View.INVISIBLE);
    }

    private static void toggleVisibility(Activity context, int resourceId) {
        View t = context.findViewById(resourceId);
        if (t.getVisibility() == View.VISIBLE) {
            t.setVisibility(View.INVISIBLE);
        }
        else {
            t.setVisibility(View.VISIBLE);
        }
    }

}
