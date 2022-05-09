package com.example.shnyagashnyajnaya.Notification;

import static com.example.shnyagashnyajnaya.MainActivity.ArrOfFavorite;
import static com.example.shnyagashnyajnaya.MainActivity.deserialize;
import static com.example.shnyagashnyajnaya.MainActivity.ma;
import static com.example.shnyagashnyajnaya.MainActivity.myPosition;
import static com.example.shnyagashnyajnaya.MainActivity.serialize;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.DEFAULT_NOTIFICATION_CHANNEL;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.LANGUAGE;

import static java.lang.Math.cos;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.shnyagashnyajnaya.MainActivity;
import com.example.shnyagashnyajnaya.R;
import com.yandex.mapkit.geometry.Point;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationService extends Service  {
    private MyThread myThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(myThread == null) {
            myThread = new MyThread();
            myThread.start();
        }

        Notification notification =
                new Notification.Builder(this, DEFAULT_NOTIFICATION_CHANNEL)
                        .setContentTitle("GPS")
                        .setContentText("Search for places")
                        .build();

        startForeground(101, notification);

        return START_STICKY;
    }

    class MyThread extends Thread {
        private String getWordDeclension(Integer n){
            String[] wordType = new String[]{" метр", " метра", " метров"};
            int result = n % 100;
            if (result >=10 && result <= 20) {
                return wordType[2]; }
            result = n % 10;
            if (result == 0 || result > 4) {
                return wordType[2];
            }
            if (result > 1) {
                return wordType[1];
            } if (result == 1) {
                return wordType[0];
            }
            return null;
        }

        private double calculateDistance(Point curr_point, Point needTo_point){
            double lat, lon, la3, lo3;
            lon= curr_point.getLongitude();
            lat= curr_point.getLatitude();
            la3= needTo_point.getLatitude();
            lo3= needTo_point.getLongitude();
            return Math.sqrt(Math.pow((111321 * cos(Math.toRadians(lat)) * (lon)) - (111321 * cos(Math.toRadians(lat)) * (lo3)), 2)
                    + Math.pow(111111 * (lat) - 111111 * (la3), 2));
        }
        @Override
        public void run() {
            Map<String, ArrayList<String>> ArrOfFavorite;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ma);
            String favs = preferences.getString("favs", "");
            if (favs.length() > 0){
                try { ArrOfFavorite = deserialize(new JSONObject(favs));
                } catch (JSONException e) {
                    e.printStackTrace();
                    ArrOfFavorite = new HashMap<>();
                }
            } else { ArrOfFavorite = new HashMap<>(); }
            if (ArrOfFavorite.keySet().size() > 0){
                int size = ArrOfFavorite.size();
                for (String str: ArrOfFavorite.keySet()){
                    List<String> coordList = Arrays.asList(ArrOfFavorite.get(str).get(0).split(" "));
                    Point PlacePosition = new Point(Double.parseDouble(coordList.get(0)), Double.parseDouble(coordList.get(1)));
                    double dist = calculateDistance(new Point(myPosition.getLatitude(), myPosition.getLongitude()), PlacePosition);

                    if (dist <= 500f){
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            int Icon = Integer.parseInt(ArrOfFavorite.get(str).get(1));
                            String title = ArrOfFavorite.get(str).get(2);
                            String text;
                            String actionText;
                            Intent intent = new Intent("com.example.shnyagashnyajnaya.Notification.action.longpoll");
                            sendBroadcast(intent);
                            if (LANGUAGE.equals("ru")){
                                text = "Совсем рядом! " + (int)dist + getWordDeclension((int)dist);
                                actionText = "Да че я там не видел";
                            }else {
                                text = "Very near! " + (int)dist + " meters";
                                actionText = "Сhecked out";
                            }

                            Intent removeIntent = new Intent(NotificationService.this, NotificationCallbackService.class);
                            removeIntent.putExtra("xid", str);

                            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent removePendingIntent =
                                    PendingIntent.getBroadcast(getApplicationContext(),
                                            --size, removeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                            Notification notification =
                                    new NotificationCompat.Builder(NotificationService.this, DEFAULT_NOTIFICATION_CHANNEL)
                                            .setSmallIcon(Icon)
                                            .setAutoCancel(true)
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), Icon))
                                            .setContentTitle(title)
                                            .setContentText(text)
                                            .addAction(R.drawable.bin, actionText, removePendingIntent)
                                            .build();

                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(size, notification);
                        }
                    }
                 }
            }
            try {
                sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myThread = new MyThread();
            myThread.start();
        }
    }

}
