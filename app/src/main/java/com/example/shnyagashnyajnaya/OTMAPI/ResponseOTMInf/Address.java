
package com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf;


import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Address implements Parcelable
{

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("road")
    @Expose
    private String road;
    @SerializedName("house")
    @Expose
    private String house;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("county")
    @Expose
    private String county;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("house_number")
    @Expose
    private String houseNumber;
    @SerializedName("city_district")
    @Expose
    private String cityDistrict;
    @SerializedName("neighbourhood")
    @Expose
    private String neighbourhood;
    public final static Creator<Address> CREATOR = new Creator<Address>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Address createFromParcel(android.os.Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return (new Address[size]);
        }

    }
    ;

    protected Address(android.os.Parcel in) {
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.road = ((String) in.readValue((String.class.getClassLoader())));
        this.house = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.county = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.postcode = ((String) in.readValue((String.class.getClassLoader())));
        this.countryCode = ((String) in.readValue((String.class.getClassLoader())));
        this.houseNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.cityDistrict = ((String) in.readValue((String.class.getClassLoader())));
        this.neighbourhood = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Address() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCityDistrict() {
        return cityDistrict;
    }

    public void setCityDistrict(String cityDistrict) {
        this.cityDistrict = cityDistrict;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(city);
        dest.writeValue(road);
        dest.writeValue(house);
        dest.writeValue(state);
        dest.writeValue(county);
        dest.writeValue(country);
        dest.writeValue(postcode);
        dest.writeValue(countryCode);
        dest.writeValue(houseNumber);
        dest.writeValue(cityDistrict);
        dest.writeValue(neighbourhood);
    }

    public int describeContents() {
        return  0;
    }

}
