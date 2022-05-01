
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf;

import java.util.HashMap;
import java.util.Map;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;


public class Point implements Parcelable
{

    private double lon;
    private double lat;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Point> CREATOR = new Creator<Point>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Point createFromParcel(android.os.Parcel in) {
            return new Point(in);
        }

        public Point[] newArray(int size) {
            return (new Point[size]);
        }

    }
    ;

    protected Point(android.os.Parcel in) {
        this.lon = ((double) in.readValue((double.class.getClassLoader())));
        this.lat = ((double) in.readValue((double.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public Point() {
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Point withLon(double lon) {
        this.lon = lon;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Point withLat(double lat) {
        this.lat = lat;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Point withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(lon);
        dest.writeValue(lat);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
