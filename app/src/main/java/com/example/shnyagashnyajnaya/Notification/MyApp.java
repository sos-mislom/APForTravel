package com.example.shnyagashnyajnaya.Notification;

import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.DEFAULT_NOTIFICATION_CHANNEL;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class MyApp extends Application {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationChannel channel = new NotificationChannel(DEFAULT_NOTIFICATION_CHANNEL,
                "Notifications",
                NotificationManager.IMPORTANCE_LOW);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}
