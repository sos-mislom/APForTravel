package com.example.shnyagashnyajnaya;

import static android.graphics.Color.BLUE;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.API_OTM;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.API_YANDEX_MAP;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.KINDS_OF_PLACES;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.shnyagashnyajnaya.OTMAPI.OTMAPI;
import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM.ResponseOTM;
import com.example.shnyagashnyajnaya.OTMAPI.ServiceToGetPlaces;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener {
    public static MapView mapView;
    public static Geocoder geocoder;
    public static OTMAPI otmapi;
    public static MainActivity ma;
    private static TextView t_x_message;
    private UserLocationLayer userLocationLayer;
    private Point myLocation;
    private CoordinatorLayout rootCoordinatorLayout;
    private LocationManager locationManager;
    private static final double DESIRED_ACCURACY = 0;
    private static final long MINIMAL_TIME = 1000;
    private static final double MINIMAL_DISTANCE = 1;
    private static final boolean USE_IN_BACKGROUND = false;
    public static final int COMFORTABLE_ZOOM_LEVEL = 18;
    private LocationListener myLocationListener;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(API_YANDEX_MAP);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        geocoder = new Geocoder(this, Locale.getDefault());
        mapView = (MapView) findViewById(R.id.mapview);
        ma = MainActivity.this;
        otmapi = new OTMAPI();
        t_x_message = new TextView(ma);
        t_x_message.setVisibility(View.INVISIBLE);
        super.onCreate(savedInstanceState);


        locationManager = MapKitFactory.getInstance().createLocationManager();
        myLocationListener = new LocationListener() {
            @Override
            public void onLocationUpdated(Location location) {
                if (myLocation == null) {
                    moveCamera(location.getPosition(), COMFORTABLE_ZOOM_LEVEL);
                }
                myLocation = location.getPosition();
                SetPlacesInMap(myLocation.getLongitude(), myLocation.getLatitude());
            }

            @Override
            public void onLocationStatusUpdated(LocationStatus locationStatus) {
                if (locationStatus == LocationStatus.NOT_AVAILABLE) {
                    System.out.println("sdncvoadsjv");
                }
            }
        };
        mapView.setZoomFocusPoint(new ScreenPoint(5.f, 4.f));
        chekLocationAccess();


    }

    private void moveCamera(Point point, float zoom) {
        mapView.getMap().move(
                new CameraPosition(point, zoom, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
    }
    public void SetPlacesInMap(double lon, double lat){
        ServiceToGetPlaces service = otmapi.CreateService(ServiceToGetPlaces.class);
        Call<ResponseOTM> call = service.getPlaces(
                1800,
                lon,
                lat,
                KINDS_OF_PLACES,
                API_OTM
        );
        call.enqueue(new Callback<ResponseOTM>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onResponse(Call<ResponseOTM> call, Response<ResponseOTM> response) {

                if (response.body() != null) {
                    for (int i = 0; i < response.body().features.size(); i++) {
                        double lon = response.body().features.get(i).geometry.coordinates.get(1);
                        double lat = response.body().features.get(i).geometry.coordinates.get(0);
                        String name = response.body().features.get(i).properties.name;

                        Point target = new Point(lon, lat);
                        mapView.getMap().getMapObjects()
                                .addPlacemark(target, ImageProvider.fromBitmap(MainActivity.drawSimpleBitmap(name)))

                                .addTapListener(new MapObjectTapListener() {
                                    @Override
                                    public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
                                        TextView tx = new TextView(ma);
                                        tx.setText(name);
                                        tx.setTextSize(15);
                                        mapView.addView(tx);
                                        return false;
                                    }
                                });


                        mapView.getMap().move(
                                new CameraPosition(target, 4.5f, 3.0f, 1.0f),
                                new Animation(Animation.Type.LINEAR, 3),
                                null);

                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseOTM> call, Throwable t) {
                Log.e("!SomethingWentWrong!", t.toString());
                TextView View_of_Fail = new TextView(MainActivity.this);
                View_of_Fail.setText(t.toString());
                mapView.addView(View_of_Fail);
            }
        });
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

        userLocationView.getAccuracyCircle().setStrokeColor(BLUE);
        //userLocationLayer.setAutoZoomEnabled(true);
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

    public static void clearTableView(ViewGroup tblview){
        for (View i : getAllChildren(tblview)) {
            if (i instanceof TextView)
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Bitmap drawSimpleBitmap(String text) {
        int picSize = 60;
        Bitmap bitmap = Bitmap.createBitmap(picSize, picSize, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setTextSize(60);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawColor(BLUE);
        //canvas.drawText(text, picSize / 2,
         //       picSize / 2 - ((paint.descent() + paint.ascent()) / 2), paint);
        return bitmap;
    }
}