package com.example.shnyagashnyajnaya;


import static com.example.shnyagashnyajnaya.MainActivity.ll;
import static com.example.shnyagashnyajnaya.MainActivity.mapView;
import static com.example.shnyagashnyajnaya.MainActivity.tx_town;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.LANGUAGE;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.yandex.mapkit.geometry.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class asyncTaskGetGeoLocation extends AsyncTask<String, Void, ArrayList<String>> {
    private MainActivity mainActivity;
    private Point position;
    private Typeface tf;

    public asyncTaskGetGeoLocation(MainActivity ma, com.yandex.mapkit.geometry.Point myPosition, Typeface tipeface) {
        mainActivity = ma;
        position = myPosition;
        tf = tipeface;
    }

    @Override
    protected ArrayList<String> doInBackground(String... parameter) {
        Geocoder geocoder;
        if (LANGUAGE.equals("ru")){
            geocoder = new Geocoder(mainActivity, Locale.getDefault());
        }else{
            geocoder = new Geocoder(mainActivity, Locale.UK);
        }
        ArrayList<String> arr = new ArrayList<>();
        try {
            List<Address> location = geocoder.getFromLocation(position.getLatitude(), position.getLongitude(), 1);
            arr.add(location.get(0).getThoroughfare()); //route
            arr.add(location.get(0).getLocality());//town
            arr.add( location.get(0).getAdminArea());//admin
            arr.add(location.get(0).getCountryName());//country
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onPostExecute(ArrayList<String> result) {
        super.onPostExecute(result);
        MainActivity.regions = result;
        if (ll.findViewById(1) instanceof TextView){
            tx_town.setText(result.get(0));
        } else{
            tx_town.setTextSize(30);
            tx_town.setTypeface(tf);
            tx_town.setId(1);
            tx_town.setTextColor(ContextCompat.getColor(mainActivity, R.color.black));
            tx_town.setPadding(20, 70, 0, 0);
            tx_town.setText(result.get(0));

            tx_town.setGravity(Gravity.TOP);
            ll.addView(tx_town);
        }
    }
}