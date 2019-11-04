package com.sfusurge.app.primary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sfusurge.app.primary.service.PreferencesManager;
import com.sfusurge.app.primary.service.TextAndViewManager;

import java.util.ArrayList;
import java.util.List;

public class InputActivity extends AppCompatActivity {

    private static final int[] inputFieldResourceIds = {
            R.id.first_name_input,
            R.id.last_name_input,
            R.id.email_input
    };

    public static final String fieldsSet = "fieldsSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allow transitions
        // Must be called before setting ContentView
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);

        setContentView(R.layout.activity_input);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Link button to save input
        FloatingActionButton button = findViewById(R.id.set_input_details);
        final Activity context = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputFieldsCompleted()) {
                    TextAndViewManager.setVisible(context, R.id.input_warning);
                } else {
                    PreferencesManager.setStrings(context, getInputFields());
                    PreferencesManager.setBoolean(context, fieldsSet, true);
                    navigateToNextActivity();
                }
            }
        });
    }

    private void navigateToNextActivity() {
        startActivity(new Intent(InputActivity.this, ScanActivity.class));
        finish();
    }

    private boolean inputFieldsCompleted() {
        for (int id : inputFieldResourceIds) {
            if (TextAndViewManager.getValueOfInputField(this, id).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private List<Pair<String, String>> getInputFields() {
        List<Pair<String, String>> list = new ArrayList<>();

        // Set preferences in root_preferences.xml
        list.add(new Pair<>(
                "first_name",
                TextAndViewManager.getValueOfInputField(this, R.id.first_name_input)
        ));
        list.add(new Pair<>(
                "last_name",
                TextAndViewManager.getValueOfInputField(this, R.id.last_name_input)
        ));
        list.add(new Pair<>(
                "email",
                TextAndViewManager.getValueOfInputField(this, R.id.email_input)
        ));

        return list;
    }

}
