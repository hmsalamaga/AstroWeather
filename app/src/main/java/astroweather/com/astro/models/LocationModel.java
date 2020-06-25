package astroweather.com.astro.models;

import com.google.gson.annotations.SerializedName;

public class LocationModel {
    @SerializedName("woeid")
    public
    String woeid;
    @SerializedName("city")
    public
    String city;
    @SerializedName("region")
    public
    String region;
    @SerializedName("country")
    public
    String country;
    @SerializedName("lat")
    public
    String latitude;
    @SerializedName("long")
    public
    String longitude;
    @SerializedName("timezone_id")
    public
    String timezone_id;
}
