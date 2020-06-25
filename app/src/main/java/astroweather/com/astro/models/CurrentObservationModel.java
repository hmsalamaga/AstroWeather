package astroweather.com.astro.models;

import com.google.gson.annotations.SerializedName;

public class CurrentObservationModel {
    @SerializedName("wind")
    public
    WindModel wind;
    @SerializedName("atmosphere")
    public
    AtmosphereModel atmosphere;
    @SerializedName("astronomy")
    public
    AstronomyModel astronomy;
    @SerializedName("condition")
    public
    ConditionModel condition;
    @SerializedName("pubDate")
    String pubDate;

}
