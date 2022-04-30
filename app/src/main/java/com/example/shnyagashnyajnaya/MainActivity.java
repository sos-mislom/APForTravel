package com.example.shnyagashnyajnaya;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.shnyagashnyajnaya.AsyncTasks.asyncGetAllObjects;
import com.example.shnyagashnyajnaya.OTMAPI.OTMAPI;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener {
    public static MapView mapView;
    public static Geocoder geocoder;
    public static OTMAPI otmapi;
    public static MainActivity ma;
    private static TextView t_x_message;
    private Point SCREEN_POSITION = new Point();
    private UserLocationLayer userLocationLayer;
    public static AsyncTask<String, Void, Map<String, ArrayList>> thread;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String MAPKIT_API_KEY = "26047576-121f-4108-a0be-a1c7e90cfde7";
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        geocoder = new Geocoder(this, Locale.getDefault());
        mapView = (MapView) findViewById(R.id.mapview);
        ma = MainActivity.this;
        otmapi = new OTMAPI();
        t_x_message = new TextView(ma);
        t_x_message.setVisibility(View.INVISIBLE);
        super.onCreate(savedInstanceState);
        getObjectsOfTown("ru", "56.8519", "60.6122", "56.8519", "60.6122");
        mapView.setZoomFocusPoint(new ScreenPoint(5.f, 4.f));
        chekLocationAccess();

    }


    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    public void chekLocationAccess (){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            MapKit mapKit = MapKitFactory.getInstance();
            userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
            userLocationLayer.setVisible(true);
            userLocationLayer.setHeadingEnabled(true);
            userLocationLayer.setObjectListener((UserLocationObjectListener) this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
    }
    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5)),
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83)));
        userLocationView.getAccuracyCircle().setStrokeColor(Color.BLUE);
        userLocationLayer.setAutoZoomEnabled(true);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    public static void getObjectsOfTown(String ru, String s, String s1, String s2, String s3){
        if (checkConnection()) thread = new asyncGetAllObjects().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public static void clearTableView(ViewGroup tblview){
        for (View i : getAllChildren(tblview)) {
            ((ViewGroup) tblview).removeView(i);
        }
    }

    public static boolean checkConnection(){
        if (hasConnection()){
            return true;
        } else {
            Toast toast = Toast.makeText(ma,
                    "ИНТЕРНЕТ КОНЭКШН ЭРРОР", Toast.LENGTH_SHORT);
            toast.show();
            t_x_message.setText(" Ткните для перезагрузки");
            t_x_message.setTextSize(30);
            t_x_message.setGravity(View.TEXT_ALIGNMENT_CENTER);
            mapView.addView(t_x_message);
            return false;
        }
    }
    public static ArrayList<View> getAllChildren(View v) {
        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }
        ArrayList<View> result = new ArrayList<View>();
        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));
            result.addAll(viewArrayList);
        }
        return result;
    }


    public static boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) ma.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) return true;

        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) return true;

        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) return true;
        return false;
    }

}