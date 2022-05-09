package com.example.shnyagashnyajnaya.Notification;

import static com.example.shnyagashnyajnaya.MainActivity.ArrOfFavorite;
import static com.example.shnyagashnyajnaya.MainActivity.serialize;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;

public class NotificationCallbackService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String xid = intent.getStringExtra("xid");
        Log.d(getClass().getSimpleName(), "startCommand");
        if (ArrOfFavorite.keySet().contains(xid)) ArrOfFavorite.remove(xid);
        try {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                .putString("favs", serialize(ArrOfFavorite).toString())
                .apply();
        } catch (JSONException e) {
            e.printStackTrace();
            return flags;
        }
        return START_NOT_STICKY;
    }

}