
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf;

import java.util.HashMap;
import java.util.Map;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;


public class ResponseOTMInf implements Parcelable
{

    private String xid;
    private String name;
    private Address address;
    private String rate;
    private String osm;
    private String wikidata;
    private String kinds;
    private Sources sources;
    private String otm;
    private String wikipedia;
    private String image;
    private Preview preview;
    private WikipediaExtracts wikipediaExtracts;
    private Point point;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
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
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public ResponseOTMInf() {
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public ResponseOTMInf withXid(String xid) {
        this.xid = xid;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResponseOTMInf withName(String name) {
        this.name = name;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ResponseOTMInf withAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public ResponseOTMInf withRate(String rate) {
        this.rate = rate;
        return this;
    }

    public String getOsm() {
        return osm;
    }

    public void setOsm(String osm) {
        this.osm = osm;
    }

    public ResponseOTMInf withOsm(String osm) {
        this.osm = osm;
        return this;
    }

    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }

    public ResponseOTMInf withWikidata(String wikidata) {
        this.wikidata = wikidata;
        return this;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public ResponseOTMInf withKinds(String kinds) {
        this.kinds = kinds;
        return this;
    }

    public Sources getSources() {
        return sources;
    }

    public void setSources(Sources sources) {
        this.sources = sources;
    }

    public ResponseOTMInf withSources(Sources sources) {
        this.sources = sources;
        return this;
    }

    public String getOtm() {
        return otm;
    }

    public void setOtm(String otm) {
        this.otm = otm;
    }

    public ResponseOTMInf withOtm(String otm) {
        this.otm = otm;
        return this;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public ResponseOTMInf withWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ResponseOTMInf withImage(String image) {
        this.image = image;
        return this;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public ResponseOTMInf withPreview(Preview preview) {
        this.preview = preview;
        return this;
    }

    public WikipediaExtracts getWikipediaExtracts() {
        return wikipediaExtracts;
    }

    public void setWikipediaExtracts(WikipediaExtracts wikipediaExtracts) {
        this.wikipediaExtracts = wikipediaExtracts;
    }

    public ResponseOTMInf withWikipediaExtracts(WikipediaExtracts wikipediaExtracts) {
        this.wikipediaExtracts = wikipediaExtracts;
        return this;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public ResponseOTMInf withPoint(Point point) {
        this.point = point;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public ResponseOTMInf withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
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
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
