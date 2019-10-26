package com.sfusurge.app.primary.server;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sfusurge.app.primary.service.PreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckinServer {

    private static final String TAG = CheckinServer.class.getName();

    private static final String SERVER_URL = "http://sfusurge-checkin.herokuapp.com";

    // Returns a built Request object which should be fed into a Volley Queue.
    public static Request checkIntoEvent(Activity context, final String eventCode, final TextView resultView) {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("firstName", PreferencesManager.getString(context, "first_name"));
            jsonBody.put("lastName", PreferencesManager.getString(context, "last_name"));
            jsonBody.put("email", PreferencesManager.getString(context, "email"));
        } catch (JSONException e) {
            Log.e(TAG, "" + e.getMessage());
            e.printStackTrace();
            return null;
        }
        Log.v(TAG, jsonBody.toString());

        return new JsonObjectRequest(
                Request.Method.POST,
                SERVER_URL + "/event/checkin/" + eventCode,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String s = "Signed into event " + eventCode + "!";
                        resultView.setText(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "" + error.getMessage());
                        error.printStackTrace();

                        String s = "Failed to check into event " + eventCode +
                                ": " + error.getMessage();
                        resultView.setText(s);
                    }
                }
        );
    }

}
