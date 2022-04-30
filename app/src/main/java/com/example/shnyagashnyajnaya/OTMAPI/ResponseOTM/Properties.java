
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Properties implements Parcelable
{

    @SerializedName("xid")
    @Expose
    public String xid;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("dist")
    @Expose
    public Double dist;
    @SerializedName("rate")
    @Expose
    public Integer rate;
    @SerializedName("wikidata")
    @Expose
    public String wikidata;
    @SerializedName("kinds")
    @Expose
    public String kinds;
    @SerializedName("osm")
    @Expose
    public String osm;
    public final static Creator<Properties> CREATOR = new Creator<Properties>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Properties createFromParcel(android.os.Parcel in) {
            return new Properties(in);
        }

        public Properties[] newArray(int size) {
            return (new Properties[size]);
        }

    }
    ;

    protected Properties(android.os.Parcel in) {
        this.xid = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.dist = ((Double) in.readValue((Double.class.getClassLoader())));
        this.rate = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.wikidata = ((String) in.readValue((String.class.getClassLoader())));
        this.kinds = ((String) in.readValue((String.class.getClassLoader())));
        this.osm = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Properties() {
    }

    public Properties withXid(String xid) {
        this.xid = xid;
        return this;
    }

    public Properties withName(String name) {
        this.name = name;
        return this;
    }

    public Properties withDist(Double dist) {
        this.dist = dist;
        return this;
    }

    public Properties withRate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public Properties withWikidata(String wikidata) {
        this.wikidata = wikidata;
        return this;
    }

    public Properties withKinds(String kinds) {
        this.kinds = kinds;
        return this;
    }

    public Properties withOsm(String osm) {
        this.osm = osm;
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(xid);
        dest.writeValue(name);
        dest.writeValue(dist);
        dest.writeValue(rate);
        dest.writeValue(wikidata);
        dest.writeValue(kinds);
        dest.writeValue(osm);
    }

    public int describeContents() {
        return  0;
    }

}
