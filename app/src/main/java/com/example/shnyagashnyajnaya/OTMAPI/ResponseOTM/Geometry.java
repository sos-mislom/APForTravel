package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Geometry implements Parcelable
{
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("coordinates")
    @Expose
    public List<Double> coordinates;

    public final static Creator<Geometry> CREATOR = new Creator<Geometry>() {
        @SuppressWarnings({
                "unchecked"
        })
        public Geometry createFromParcel(android.os.Parcel in) {
            return new Geometry(in);
        }
        public Geometry[] newArray(int size) {
            return (new Geometry[size]);
        }
    };

    protected Geometry(android.os.Parcel in) {
        this.type = ((String) in.readValue(String.class.getClassLoader()));
        this.coordinates =  ((ArrayList<Double>) in.readValue(ArrayList.class.getClassLoader()));
    }

    public Geometry withType(String type) {
        this.type = type;
        return this;
    }

    public Geometry withCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeList(coordinates);
    }



}
