package com.example.shnyagashnyajnaya;

import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.API_OTM;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.API_YANDEX_MAP;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.KINDS_OF_PLACES;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.LANGUAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.shnyagashnyajnaya.OTMAPI.OTMAPI;
import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM.Feature;
import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM.ResponseOTM;
import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf.ResponseOTMInf;
import com.example.shnyagashnyajnaya.OTMAPI.ServiceToGetInfoAboutPlaces;
import com.example.shnyagashnyajnaya.OTMAPI.ServiceToGetPlaces;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener, CameraListener{
    public static MapView mapView;
    public static Geocoder geocoder;
    public static Point myPosition;
    private UserLocationLayer userLocationLayer;
    public static BottomSheetBehavior bottomSheetBehavior;
    private final Handler HandlerPlacesUpdater = new Handler();
    private final Handler HandlerCheckAllAccess = new Handler();

    public static TableLayout content_layout;
    public static TableLayout name_layout;

    private boolean followUserLocation;

    private Bitmap forPhoto;
    private Bitmap buildings;
    private Bitmap historical;
    private Bitmap unknown;
    private Bitmap industrial;
    private Bitmap nature;
    private Bitmap list;
    private Bitmap loupe;
    private Bitmap target;
    private Bitmap wing;

    private Typeface tf;
    private int counter;
    public static TextView tx_town;

    public static ArrayList<String> regions;
    List<MapObjectTapListener> mapObjectTapListeners = new ArrayList<>();

    public static AsyncTask<String, Void, ArrayList<String>> thread;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(API_YANDEX_MAP);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        geocoder = new Geocoder(this, Locale.getDefault());
        tx_town = new TextView(MainActivity.this);
        mapView = (MapView) findViewById(R.id.mapview);

        @SuppressLint("UseCompatLoadingForDrawables") Drawable d = getResources().getDrawable(R.drawable.forphoto);
        forPhoto = ((BitmapDrawable) d).getBitmap();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d1 = getResources().getDrawable(R.drawable.buildings);
        buildings = ((BitmapDrawable) d1).getBitmap();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d2 = getResources().getDrawable(R.drawable.historical);
        historical = ((BitmapDrawable) d2).getBitmap();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d3 = getResources().getDrawable(R.drawable.unknown);
        unknown = ((BitmapDrawable) d3).getBitmap();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d4 = getResources().getDrawable(R.drawable.industrial);
        industrial = ((BitmapDrawable) d4).getBitmap();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d5 = getResources().getDrawable(R.drawable.nature);
        nature = ((BitmapDrawable) d5).getBitmap();

        @SuppressLint("UseCompatLoadingForDrawables") Drawable d6 = getResources().getDrawable(R.drawable.list);
        list = ((BitmapDrawable) d6).getBitmap();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d7 = getResources().getDrawable(R.drawable.loupe);
        loupe = ((BitmapDrawable) d7).getBitmap();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d8 = getResources().getDrawable(R.drawable.target);
        target = ((BitmapDrawable) d8).getBitmap();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d9 = getResources().getDrawable(R.drawable.wing);
        wing = ((BitmapDrawable) d9).getBitmap();

        tf = Typeface.createFromAsset(getAssets(), "karlocharm.otf");

        name_layout = (TableLayout) findViewById(R.id.nameofsheet);
        content_layout = (TableLayout) findViewById(R.id.content);

        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(true);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String coords = preferences.getString("last_coords", "");
        if (coords.length() > 0){
            List<String> coordList = Arrays.asList(coords.split(" "));
            myPosition = new Point(Double.parseDouble(coordList.get(0)), Double.parseDouble(coordList.get(1)));
        }

        ImageButton settings = new ImageButton(MainActivity.this);
        settings.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(wing, 20, 20, true)));
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearTableView(name_layout);
                clearTableView(content_layout);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });

        ImageButton search = new ImageButton(MainActivity.this);
        search.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(loupe, 20, 20, true)));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearTableView(name_layout);
                clearTableView(content_layout);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });

        ImageButton favorites = new ImageButton(MainActivity.this);
        favorites.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(list, 20, 20, true)));
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearTableView(name_layout);
                clearTableView(content_layout);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });

        ImageButton findUser = new ImageButton(MainActivity.this);

        findUser.setImageDrawable(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(target, 50, 50, true)));
        findUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followUserLocation = true;
            }
        });

        super.onCreate(savedInstanceState);

        mapView.setZoomFocusPoint(new ScreenPoint(5.f, 4.f));
        mapView.getMap().addCameraListener((CameraListener) this);
        HandlerCheckAllAccess.removeCallbacks(CheckAllAccess);
        HandlerPlacesUpdater.removeCallbacks(PlacesUpdater);
        HandlerCheckAllAccess.postDelayed(CheckAllAccess, 0);
    }

    private final Runnable CheckAllAccess = new Runnable() {
        @Override
        public void run() {
            if (checkLocationAccess() && checkConnection()){
                FindUser();
            } else {
                HandlerCheckAllAccess.postDelayed(this, 10000);
            }
        }
    };
    private final Runnable PlacesUpdater = new Runnable() {
        @Override
        public void run() {
            if (myPosition.getLatitude() != 0.0){
                if (counter == 0){
                    counter++;
                    mapView.getMap().move(
                            new CameraPosition(myPosition, 11.5f, 3.0f, 1.0f),
                            new Animation(Animation.Type.LINEAR, 5),
                            null);
                    thread = new asyncTaskGetGeoLocation(MainActivity.this, myPosition, tf).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                SetPlacesInMap(myPosition);
                HandlerPlacesUpdater.postDelayed(this, 60000);
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                        .putString("last_coords", myPosition.getLatitude() + " " + myPosition.getLongitude())
                        .apply();
            } else {HandlerPlacesUpdater.postDelayed(this, 0);}
        }
    };

    public void SetPlacesInMap(Point position){
        Log.e("lon, lat", position.getLongitude() +" "+position.getLatitude());
        ServiceToGetPlaces service = OTMAPI.CreateService(ServiceToGetPlaces.class);
        Call<ResponseOTM> call = service.getPlaces(
                LANGUAGE,
                10000,
                position.getLongitude(),
                position.getLatitude(),
                KINDS_OF_PLACES,
                API_OTM
        );
        call.enqueue(new Callback<ResponseOTM>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onResponse(@NonNull Call<ResponseOTM> call, @NonNull Response<ResponseOTM> response) {
                Log.e("url", response.toString());
                if (response.body() != null) {
                    for (int i = 0; i < response.body().features.size(); i++) {
                        Feature card = response.body().features.get(i);
                        double lon = card.geometry.coordinates.get(1);
                        double lat = card.geometry.coordinates.get(0);
                        Bitmap bit;
                        String kinds = card.properties.kinds;
                        if (kinds.contains("historic")){
                            bit = historical;
                        }else if (kinds.contains("cultural")){
                            bit = historical;
                        }else if (kinds.contains("industrial_facilities")){
                            bit = industrial;
                        }else if (card.properties.name.length() == 0){
                            bit = unknown;
                        } else if (kinds.contains("natural")){
                            bit = nature;
                        } else if (kinds.contains("architecture")){
                            bit = buildings;
                        }else if (kinds.contains("other")){
                            bit = forPhoto;
                        }else{
                            bit = unknown;
                        }

                        MapObjectTapListener mapObjectTapListener = (mapObject, point) -> {
                            GetInfoAbout(card.properties.xid, card.properties.dist.toString());
                            return true;
                        };

                        mapObjectTapListeners.add(mapObjectTapListener);

                        mapView.getMap().getMapObjects()
                                .addPlacemark(new Point(lon, lat),
                                        ImageProvider.fromBitmap(Bitmap.createScaledBitmap(bit,
                                                50,
                                                50,
                                                true))
                                ).addTapListener(mapObjectTapListener);

                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseOTM> call, @NonNull Throwable t) {
                Log.e("!SomethingWentWrong(P)!", t.toString());
                TextView View_of_Fail = new TextView(MainActivity.this);
                View_of_Fail.setText(t.toString());
                mapView.addView(View_of_Fail);
            }
        });
    }
    public static void clearTableView(ViewGroup tblview){
        for (View i : getAllChildren(tblview)) {
            ((ViewGroup) tblview).removeView(i);
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
    public void GetInfoAbout(String xid, String distance){
        Log.e("xid", xid);
        ServiceToGetInfoAboutPlaces service = OTMAPI.CreateService(ServiceToGetInfoAboutPlaces.class);
        Call<ResponseOTMInf> call = service.getInfo(
                xid,
                LANGUAGE,
                API_OTM
        );
        call.enqueue(new Callback<ResponseOTMInf>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onResponse(@NonNull Call<ResponseOTMInf> call, @NonNull Response<ResponseOTMInf> response) {
                Log.e("url", response.toString());
                if (response.body() != null) {
                    PlaceInfoDialogFragment newFragment = new PlaceInfoDialogFragment(response, distance);
                    newFragment.show(getSupportFragmentManager().beginTransaction(), "info");
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseOTMInf> call, @NonNull Throwable t) {
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

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }


    public void  setAnchor(){
        userLocationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5)),
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83))
        );
    }

    public void  removeAnchor(){
        userLocationLayer.resetAnchor();
    }

    public void FindUser(){
        MapKit mapKit = MapKitFactory.getInstance();
        if (userLocationLayer == null){userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());}
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
    }

    public boolean checkLocationAccess (){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            return checkLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return checkLocation();
        }
    }

    public boolean checkLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.e("checkLocation", ""+ locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

          final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final boolean[] result = new boolean[1];
            builder.setCancelable(false);
            builder.setMessage("Соблаговалите вас обнаружить");
            builder.setPositiveButton("Окей", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int id) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    result[0] = true;
                }
            });
            final AlertDialog alert = builder.create();
            alert.show();
            return result[0];
        } else {
            return true;
        }
    }


    public boolean checkConnection(){
        Log.e("checkConnection", hasConnection()+"");
        if (hasConnection()){
            return true;
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final boolean[] result = new boolean[1];
            builder.setCancelable(false)
                    .setMessage("Влючи инет по-братски, а")
                    .setPositiveButton("Без б", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                            result[0] = true;
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
            return result[0];
        }
    }

    public boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) return true;

        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) return true;

        wifiInfo = cm.getActiveNetworkInfo();
        return wifiInfo != null && wifiInfo.isConnected();
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        setAnchor();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d = getResources().getDrawable(R.drawable.userpic);
        Bitmap bitmap1 = ((BitmapDrawable) d).getBitmap();
        userLocationView.getPin().setIcon(ImageProvider.fromBitmap(Bitmap.createScaledBitmap(bitmap1, 70, 70, true)));
        userLocationView.getArrow().setIcon(ImageProvider.fromBitmap(Bitmap.createScaledBitmap(bitmap1, 70, 70, true)));
        followUserLocation = false;

        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
        HandlerPlacesUpdater.postDelayed(PlacesUpdater, 0);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onCameraPositionChanged(Map m, CameraPosition cP, CameraUpdateReason cUR, boolean finished) {
        if (regions != null && regions.size() > 0){
            float zoom = cP.getZoom();
            if (zoom > 12f){
                tx_town.setText(regions.get(0));
            }else if (zoom < 12f && zoom > 8f){
                tx_town.setText(regions.get(1));
            } else if (zoom < 8f && zoom  > 5f){
                tx_town.setText(regions.get(2));
            }else if (zoom < 5f){
                tx_town.setText(regions.get(3));
            }
        }

        if (finished) {
            if (followUserLocation) {
                setAnchor();
            }
        } else {
            if (!followUserLocation) {
                removeAnchor();
            }
        }
    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {
        double lat = userLocationView.getPin().getGeometry().getLatitude();
        double lon = userLocationView.getPin().getGeometry().getLongitude();

        myPosition = new Point(lat, lon);
    }
}