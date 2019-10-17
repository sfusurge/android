package com.sfusurge.app.primary;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sfusurge.app.primary.service.NfcService;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ScanActivity extends AppCompatActivity {

    private NfcService nfcService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        try {
////            nfcService = new NfcService(this);
//        }
//        catch(NfcService.NfcException e) {
//            Toast.makeText(this, e.getReason().getText(), Toast.LENGTH_LONG).show();
//            if(e.getReason().equals(NfcService.NfcException.Reason.NOT_SUPPORTED)) {
//                finish();
//            }
//            return;
//        }
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
    }

    @Override
    protected void onNewIntent(Intent intent) {
        readTagDataTo(intent);
    }

    private void readTagDataTo(Intent intent) {
        String data;
        try {
            data = nfcService.readTagFromIntent(intent);
        }
        catch(NfcService.NfcException e) {
            Toast.makeText(this, e.getReason().getText(), Toast.LENGTH_LONG).show();
            return;
        }

        if(data == null) {
            return;
        }


        Toast.makeText(this, "Read tag data", Toast.LENGTH_SHORT).show();
    }
}
