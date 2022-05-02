
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf;


import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Point implements Parcelable
{

    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("lat")
    @Expose
    private double lat;
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
    }

    public Point() {
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(lon);
        dest.writeValue(lat);
    }

    public int describeContents() {
        return  0;
    }

}
