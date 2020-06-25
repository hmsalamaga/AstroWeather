package astroweather.com.astro.models;

import com.google.gson.annotations.SerializedName;

public class ForecastDayModel {
    @SerializedName("day")
    public
    String day;
    @SerializedName("date")
    public
    Long date;
    @SerializedName("low")
    public
    String low;
    @SerializedName("high")
    public
    String high;
    @SerializedName("text")
    public
    String text;
    @SerializedName("code")
    public
    Integer code;

}
