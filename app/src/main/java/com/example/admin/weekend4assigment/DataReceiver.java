package com.example.admin.weekend4assigment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DataReceiver extends BroadcastReceiver {
public static final String TAG = "DataReceiverTAG";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        Boolean Ready = intent.getBooleanExtra("Ready",false);
        Intent intent1 = new Intent(context,ListingActivity.class);
        intent1.putExtra("Ready",Ready);
        context.startActivity(intent1);
    }
}
