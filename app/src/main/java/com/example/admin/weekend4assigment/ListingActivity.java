package com.example.admin.weekend4assigment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.weekend4assigment.model.BooksClass;
import com.google.gson.Gson;

import java.util.Arrays;

public class ListingActivity extends AppCompatActivity {
    private static final String MY_PREF_FILE = "com.example.admin.weekend4assigment.mypref";
    public static final String TAG = "ListingActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        fetchData();

    }
    private void fetchData()
    {
        Intent intent = getIntent();
        Boolean Ready = intent.getBooleanExtra("Ready",false);
        if(Ready)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF_FILE, Context.MODE_PRIVATE);
                    String Response = sharedPreferences.getString("Response", null);
                    if(Response !=null)

                    {
                        Gson gson = new Gson();
                        final BooksClass[] booksClasses = gson.fromJson(Response,BooksClass[].class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView rvList = (RecyclerView) findViewById(R.id.RVLIST);
                                RVAdapter rvAdapter = new RVAdapter(ListingActivity.this, Arrays.asList(booksClasses));
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListingActivity.this);
                                RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
                                rvList.setAdapter(rvAdapter);
                                rvList.setLayoutManager(layoutManager);
                                rvList.setItemAnimator(itemAnimator);
                            }
                        });

                    }
                }
            }).start();
        }

    }
}
