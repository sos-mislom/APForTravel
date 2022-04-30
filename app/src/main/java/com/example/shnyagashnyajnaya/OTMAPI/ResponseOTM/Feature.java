
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Feature implements Parcelable
{

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("properties")
    @Expose
    public Properties properties;
    public final static Creator<Feature> CREATOR = new Creator<Feature>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Feature createFromParcel(android.os.Parcel in) {
            return new Feature(in);
        }

        public Feature[] newArray(int size) {
            return (new Feature[size]);
        }

    }
    ;

    protected Feature(android.os.Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.geometry = ((Geometry) in.readValue((Geometry.class.getClassLoader())));
        this.properties = ((Properties) in.readValue((Properties.class.getClassLoader())));
    }

    public Feature() {
    }

    public Feature withType(String type) {
        this.type = type;
        return this;
    }

    public Feature withId(String id) {
        this.id = id;
        return this;
    }

    public Feature withGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }

    public Feature withProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(id);
        dest.writeValue(geometry);
        dest.writeValue(properties);
    }

    public int describeContents() {
        return  0;
    }

}
