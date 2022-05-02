
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf;


import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WikipediaExtracts implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("html")
    @Expose
    private String html;
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
    }

    public WikipediaExtracts() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(text);
        dest.writeValue(html);
    }

    public int describeContents() {
        return  0;
    }

}
