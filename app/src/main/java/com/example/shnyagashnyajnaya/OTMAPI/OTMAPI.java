package com.example.shnyagashnyajnaya.OTMAPI;

import static com.example.shnyagashnyajnaya.MainActivity.checkConnection;

import android.util.Log;

import com.example.shnyagashnyajnaya.Requests.Requests;
import com.example.shnyagashnyajnaya.Requests.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OTMAPI {
    private static Requests p;
    private String url;

    public OTMAPI() {
        this.url = "http://api.opentripmap.com/0.1";
        this.p = new Requests();
    }

    public JSONArray GET_PLACES(String lang, String lon_min, String lon_max, String lat_min, String lat_max) {
        if (checkConnection()){
            try {
                String url = this.url + "/" + lang + "/places/bbox?"
                        + "lon_min=" + lon_min + "lat_min=" + lat_min
                        + "lon_max=" + lon_max + "lat_max=" + lat_max;
                Log.e("url", url);
                Response r = p.get(url);
                return new JSONArray(r.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
}
