
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf;

import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Sources implements Parcelable
{

    @SerializedName("geometry")
    @Expose
    private String geometry;
    @SerializedName("attributes")
    @Expose
    private List<String> attributes = null;
    public final static Creator<Sources> CREATOR = new Creator<Sources>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Sources createFromParcel(android.os.Parcel in) {
            return new Sources(in);
        }

        public Sources[] newArray(int size) {
            return (new Sources[size]);
        }

    }
    ;

    protected Sources(android.os.Parcel in) {
        this.geometry = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.attributes, (java.lang.String.class.getClassLoader()));
    }

    public Sources() {
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(geometry);
        dest.writeList(attributes);
    }

    public int describeContents() {
        return  0;
    }

}
