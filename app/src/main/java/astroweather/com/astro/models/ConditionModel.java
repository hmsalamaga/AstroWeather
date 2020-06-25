package astroweather.com.astro.models;

import com.google.gson.annotations.SerializedName;

public class ConditionModel {
    @SerializedName("text")
    public
    String text;
    @SerializedName("code")
    public
    String code;
    @SerializedName("temperature")
    public
    String temperature;

}
