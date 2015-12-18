package com.example.pruebasandroid.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Jordi on 15/12/2015.
 */
public class CustomService extends IntentService {


    public static final String ACTION="com.example.pruebasandroid.services.CustomService";
    public static final String SERVICE_RESPONSE="com.example.pruebasandroid.Response";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public CustomService()
    {
        super(ACTION);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        sendResult();
    }

    private void sendResult()
    {
        Intent intent = new Intent();
        intent.setAction(SERVICE_RESPONSE);
        intent.putExtra("result", Activity.RESULT_OK);
        sendBroadcast(intent);
    }
}
