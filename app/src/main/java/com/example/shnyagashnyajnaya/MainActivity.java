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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;


import com.example.shnyagashnyajnaya.OTMAPI.OTMAPI;
import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM.Feature;
import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM.ResponseOTM;
import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf.ResponseOTMInf;
import com.example.shnyagashnyajnaya.OTMAPI.ServiceToGetInfoAboutPlaces;
import com.example.shnyagashnyajnaya.OTMAPI.ServiceToGetPlaces;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener{
    public static MapView mapView;
    public static Geocoder geocoder;
    public static OTMAPI otmapi;
    public static MainActivity ma;
    public static Point myPosition;
    private static TextView t_x_message;
    private UserLocationLayer userLocationLayer;
    private Handler mHandler = new Handler();

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

        mapView.setZoomFocusPoint(new ScreenPoint(5.f, 4.f));
        cheсkLocationAccess();

        mHandler.removeCallbacks(PlacesUpdater);
    }

    private Runnable PlacesUpdater = new Runnable() {
        @Override
        public void run() {
            if (myPosition.getLatitude() != 0.0){
                SetPlacesInMap(myPosition);
                mHandler.postDelayed(this, 60000);
            } else {mHandler.postDelayed(this, 1000);}
        }
    };

    public void SetPlacesInMap(Point position){
        Log.e("lon, lat", position.getLongitude() +" "+position.getLatitude());
        ServiceToGetPlaces service = otmapi.CreateService(ServiceToGetPlaces.class);
        Call<ResponseOTM> call = service.getPlaces(
                1500,
                position.getLongitude(),
                position.getLatitude(),
                KINDS_OF_PLACES,
                API_OTM
        );
        call.enqueue(new Callback<ResponseOTM>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onResponse(Call<ResponseOTM> call, Response<ResponseOTM> response) {
                Log.e("url", response.toString());
                if (response.body() != null) {
                    for (int i = 0; i < response.body().features.size(); i++) {
                        Feature card = response.body().features.get(i);
                        double lon = card.geometry.coordinates.get(1);
                        double lat = card.geometry.coordinates.get(0);
                        Point target = new Point(lon, lat);
                        mapView.getMap().getMapObjects()
                                .addPlacemark(target, ImageProvider.fromBitmap(MainActivity.drawSimpleBitmap("")))
                                .addTapListener(CreateMapObjectTapListener(card, card.properties.dist.toString()));
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseOTM> call, Throwable t) {
                Log.e("!SomethingWentWrong(P)!", t.toString());
                TextView View_of_Fail = new TextView(MainActivity.this);
                View_of_Fail.setText(t.toString());
                mapView.addView(View_of_Fail);
            }
        });
    }

    public void GetInfoAbout(String xid, String distance){
        Log.e("xid", xid);
        ServiceToGetInfoAboutPlaces service = otmapi.CreateService(ServiceToGetInfoAboutPlaces.class);
        Call<ResponseOTMInf> call = service.getInfo(
                xid,
                API_OTM
        );
        call.enqueue(new Callback<ResponseOTMInf>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onResponse(Call<ResponseOTMInf> call, Response<ResponseOTMInf> response) {
                Log.e("url", response.body().getAddress().toString());
                if (response.body() != null) {
                    PlaceInfoDialogFragment newFragment = new PlaceInfoDialogFragment(response, distance);
                    newFragment.show(getSupportFragmentManager().beginTransaction(), "info");
                }
            }
            @Override
            public void onFailure(Call<ResponseOTMInf> call, Throwable t) {
                Log.e("!SomethingWentWrong(I)!", t.toString());
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

    private MapObjectTapListener CreateMapObjectTapListener(Feature card, String distance) {
        MapObjectTapListener MOTL = (mapObject, point) -> {
            GetInfoAbout(card.properties.xid,distance);
            return true;
        };
        return MOTL;
    }

    public void cheсkLocationAccess (){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            MapKit mapKit = MapKitFactory.getInstance();
            userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
            userLocationLayer.setVisible(true);
            userLocationLayer.setHeadingEnabled(false);
            userLocationLayer.setObjectListener(this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
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

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        PointF mWgW = new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5));
        PointF mWgW83 = new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83));
        userLocationLayer.setAnchor(mWgW, mWgW83);
        userLocationLayer.setAutoZoomEnabled(true);

        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
        mHandler.postDelayed(PlacesUpdater, 0);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {
        double lat = userLocationView.getPin().getGeometry().getLatitude();
        double lon = userLocationView.getPin().getGeometry().getLongitude();
        myPosition = new Point(lat, lon);
    }
}