
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf;

import java.util.HashMap;
import java.util.Map;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;


public class WikipediaExtracts implements Parcelable
{

    private String title="";
    private String text="";
    private String html="";
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<WikipediaExtracts> CREATOR = new Creator<WikipediaExtracts>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WikipediaExtracts createFromParcel(android.os.Parcel in) {
            return new WikipediaExtracts(in);
        }

        public WikipediaExtracts[] newArray(int size) {
            return (new WikipediaExtracts[size]);
        }

    }
    ;

    protected WikipediaExtracts(android.os.Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.html = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public WikipediaExtracts() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WikipediaExtracts withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public WikipediaExtracts withText(String text) {
        this.text = text;
        return this;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public WikipediaExtracts withHtml(String html) {
        this.html = html;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public WikipediaExtracts withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(text);
        dest.writeValue(html);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
