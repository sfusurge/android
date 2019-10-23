package com.sfusurge.app.primary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sfusurge.app.primary.server.CheckinServer;
import com.sfusurge.app.primary.service.NfcService;
import com.sfusurge.app.primary.service.Preferences;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScanActivity extends AppCompatActivity {

    private NfcService nfcService;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);

        Preferences.setDefaults(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Link Settings button
        final ImageButton button = findViewById(R.id.settings_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ScanActivity.this, SettingsActivity.class));
            }
        });

        // Instantiate private members

        try {
            nfcService = new NfcService(this);
        }
        catch(NfcService.NfcException e) {
            Toast.makeText(this, e.getReason().getText(), Toast.LENGTH_LONG).show();
            if(e.getReason().equals(NfcService.NfcException.Reason.NOT_SUPPORTED)) {
                finish();
            }
        }

        requestQueue =  Volley.newRequestQueue(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        nfcService.pause(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(nfcService != null) {
            nfcService.resume(this);
        }

        String welcomeMsg = "Welcome, " + Preferences.getString(this, "first_name");
        TextView welcome = findViewById(R.id.scan_activity_welcome);
        welcome.setText(welcomeMsg);
    }

    // Receive an NFC reading
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String eventCode = readTagData(intent);
        if (eventCode != null && !eventCode.isEmpty()) {
            checkIntoEvent(eventCode);
        }
    }

    private String readTagData(Intent intent) {
        String data;
        try {
            data = nfcService.readTagFromIntent(intent);
        } catch (NfcService.NfcException e) {
            setActionStatus(e.getReason().getText());
            return "";
        }

        if (data == null) {
            setActionStatus("No information found on event tag.");
            return "";
        }

        return data;
    }

    private void checkIntoEvent(final String eventCode) {
        Request r = CheckinServer.checkIntoEvent(
                this, eventCode,
                (TextView)findViewById(R.id.action)
        );
        if (r == null) {
            setActionStatus("Failed to build request object.");
            return;
        }
        requestQueue.add(r);
    }

    private void setActionStatus(String text) {
        TextView t = findViewById(R.id.action);
        t.setText(text);
    }

}
