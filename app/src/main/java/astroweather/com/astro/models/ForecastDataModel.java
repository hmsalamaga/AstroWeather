package astroweather.com.astro.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForecastDataModel {
    @SerializedName("location")
    public
    LocationModel location;
    @SerializedName("current_observation")
    public
    CurrentObservationModel currentObservation;
    @SerializedName("forecasts")
    public
    ForecastDayModel[] forecasts;
}



