package com.example.shnyagashnyajnaya.Notification;

import static com.example.shnyagashnyajnaya.MainActivity.ArrOfFavorite;
import static com.example.shnyagashnyajnaya.MainActivity.GetInfoAbout;
import static com.example.shnyagashnyajnaya.MainActivity.mapView;
import static com.example.shnyagashnyajnaya.MainActivity.serialize;
import static com.yandex.mapkit.Animation.Type.SMOOTH;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

public class NotificationCallbackService extends Service {
    public static final String ACTION_FIND = "action.find";
    public static final String ACTION_REMOVE = "action.remove";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int id;
        String xid;
        String action = intent.getAction();
        if (action != null){
            switch (action){
                case ACTION_REMOVE:
                    xid = intent.getStringExtra("xid");
                    id = intent.getIntExtra("id", 0);
                    if (ArrOfFavorite.keySet().contains(xid)) ArrOfFavorite.remove(xid);
                    try {
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                .putString("favs", serialize(ArrOfFavorite).toString())
                                .apply();
                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.cancel(id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return flags;
                    }
                    break;
                case ACTION_FIND:
                    List<String> coordList = Arrays.asList(intent.getStringExtra("target").split(" "));
                    Point PlacePosition = new Point(Double.parseDouble(coordList.get(0)), Double.parseDouble(coordList.get(1)));
                    mapView.getMap().move(
                            new CameraPosition(PlacePosition, 20.5f, 0.0f, 0.0f),
                            new Animation(SMOOTH, 2),
                            null);
                    GetInfoAbout(intent.getStringExtra("xid"), intent.getStringExtra("dist"));
                    break;
            }

        }
        return START_NOT_STICKY;
    }

}