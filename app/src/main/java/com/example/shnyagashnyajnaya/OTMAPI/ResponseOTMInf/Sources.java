
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;


public class Sources implements Parcelable
{

    private String geometry;
    private List<String> attributes = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
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
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public Sources() {
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public Sources withGeometry(String geometry) {
        this.geometry = geometry;
        return this;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public Sources withAttributes(List<String> attributes) {
        this.attributes = attributes;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Sources withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(geometry);
        dest.writeList(attributes);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
