
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf;


import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;

import com.google.android.gms.common.api.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseOTMInf implements Parcelable {

    @SerializedName("xid")
    @Expose
    private String xid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("osm")
    @Expose
    private String osm;
    @SerializedName("wikidata")
    @Expose
    private String wikidata;
    @SerializedName("kinds")
    @Expose
    private String kinds;
    @SerializedName("sources")
    @Expose
    private Sources sources;
    @SerializedName("otm")
    @Expose
    private String otm;
    @SerializedName("wikipedia")
    @Expose
    private String wikipedia;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("preview")
    @Expose
    private Preview preview;
    @SerializedName("wikipedia_extracts")
    @Expose
    private WikipediaExtracts wikipediaExtracts;
    @SerializedName("point")
    @Expose
    private Point point;
    public final static Creator<ResponseOTMInf> CREATOR = new Creator<ResponseOTMInf>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseOTMInf createFromParcel(android.os.Parcel in) {
            return new ResponseOTMInf(in);
        }

        public ResponseOTMInf[] newArray(int size) {
            return (new ResponseOTMInf[size]);
        }

    }
    ;

    protected ResponseOTMInf(android.os.Parcel in) {
        this.xid = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((Address) in.readValue((Address.class.getClassLoader())));
        this.rate = ((String) in.readValue((String.class.getClassLoader())));
        this.osm = ((String) in.readValue((String.class.getClassLoader())));
        this.wikidata = ((String) in.readValue((String.class.getClassLoader())));
        this.kinds = ((String) in.readValue((String.class.getClassLoader())));
        this.sources = ((Sources) in.readValue((Sources.class.getClassLoader())));
        this.otm = ((String) in.readValue((String.class.getClassLoader())));
        this.wikipedia = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.preview = ((Preview) in.readValue((Preview.class.getClassLoader())));
        this.wikipediaExtracts = ((WikipediaExtracts) in.readValue((WikipediaExtracts.class.getClassLoader())));
        this.point = ((Point) in.readValue((Point.class.getClassLoader())));
    }

    public ResponseOTMInf() {
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getOsm() {
        return osm;
    }

    public void setOsm(String osm) {
        this.osm = osm;
    }

    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public Sources getSources() {
        return sources;
    }

    public void setSources(Sources sources) {
        this.sources = sources;
    }

    public String getOtm() {
        return otm;
    }

    public void setOtm(String otm) {
        this.otm = otm;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public WikipediaExtracts getWikipediaExtracts() {
        return wikipediaExtracts;
    }

    public void setWikipediaExtracts(WikipediaExtracts wikipediaExtracts) {
        this.wikipediaExtracts = wikipediaExtracts;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(xid);
        dest.writeValue(name);
        dest.writeValue(address);
        dest.writeValue(rate);
        dest.writeValue(osm);
        dest.writeValue(wikidata);
        dest.writeValue(kinds);
        dest.writeValue(sources);
        dest.writeValue(otm);
        dest.writeValue(wikipedia);
        dest.writeValue(image);
        dest.writeValue(preview);
        dest.writeValue(wikipediaExtracts);
        dest.writeValue(point);
    }

    public int describeContents() {
        return  0;
    }

}
