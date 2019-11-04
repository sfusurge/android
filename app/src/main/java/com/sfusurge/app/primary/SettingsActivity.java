package com.sfusurge.app.primary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.sfusurge.app.primary.service.PreferencesManager;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = SettingsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // Initialize settings
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        // Show action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            // Initialize reset button
            Preference button = getPreferenceManager().findPreference(
                    getString(R.string.pref_reset_button_key)
            );
            if (button == null) {
                Log.e(TAG, "Failed to retrieve reset button to set functionality.");
                return;
            }

            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new ConfirmResetDialogFragment().show(
                            getActivity().getSupportFragmentManager(),
                            ConfirmResetDialogFragment.class.getName()
                    );
                    return true;
                }
            });
        }
    }

    public static class ConfirmResetDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.confirm_reset_dialog))
                    .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PreferencesManager.clear(getActivity());
                            //noinspection ConstantConditions - Suppress NullPointerException warning
                            getActivity().finishAffinity();
                            startActivity(new Intent(getActivity(), SplashActivity.class));
                        }
                    })
                    .setNegativeButton(
                            getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // No action
                        }
                    });
            return builder.create();
        }
    }

}