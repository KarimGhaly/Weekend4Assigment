package com.example.admin.weekend4assigment;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.constraint.solver.Cache;
import android.telecom.Call;
import android.util.Log;

import com.example.admin.weekend4assigment.model.BooksClass;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.content.ContentValues.TAG;


public class GetDataService extends IntentService {

    public static final String TAG = "ServiceTAG";
    private static final String MY_PREF_FILE = "com.example.admin.weekend4assigment.mypref";
    private SharedPreferences sharedPreferences;

    public GetDataService() {
        super("GetDataService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPreferences = getSharedPreferences(MY_PREF_FILE, Context.MODE_PRIVATE);
        Long LastCallTime = sharedPreferences.getLong("TIME",-1);
        Log.d(TAG, "onHandleIntent: "+String.valueOf(LastCallTime));
        Long ExpireDate = LastCallTime + 1800;
        Log.d(TAG, "onHandleIntent: "+String.valueOf(ExpireDate));
        Date D = new Date();
        Long CurrentDate = D.getTime()/1000;
        String Response;
        if(LastCallTime == -1 ||  ExpireDate <= CurrentDate)
        {
            Log.d(TAG, "onHandleIntent: Call Created");
            Response = RestCall();
        }
        else {
            Response = sharedPreferences.getString("Response", null);
            Log.d(TAG, "onHandleIntent: Cache Information");
        }
        if(Response != null) {

            Intent intent1 = new Intent();
            intent1.setAction("DataReceived");
            intent1.putExtra("Ready",true);
            sendBroadcast(intent1);
        }
    }

    private String RestCall() {
        Request request = new Request.Builder().url("http://de-coding-test.s3.amazonaws.com/books.json").build();
        OkHttpClient client = new OkHttpClient();
        try {
            String Response = client.newCall(request).execute().body().string();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Response",Response);
            editor.putLong("TIME",new Date().getTime()/1000);
            editor.commit();
            return Response;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();

    }

}
