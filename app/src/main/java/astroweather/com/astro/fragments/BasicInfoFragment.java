package astroweather.com.astro.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import astroweather.com.astro.R;
import astroweather.com.astro.activities.SpecificForecastActivity;
import astroweather.com.astro.models.*;


public class BasicInfoFragment extends Fragment {

    TextView cityInfo;
    TextView latInfo;
    TextView longInfo;
    TextView temperatureInfo;
    TextView pressureInfo;
    TextView descriptionInfo;

    public BasicInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);

        cityInfo = view.findViewById(R.id.cityInfo);
        latInfo = view.findViewById(R.id.latInfo);
        longInfo = view.findViewById(R.id.longInfo);
        temperatureInfo = view.findViewById(R.id.temperatureInfo);
        pressureInfo = view.findViewById(R.id.pressureInfo);
        descriptionInfo = view.findViewById(R.id.descriptionInfo);
        update();
        return view;
    }

    public void update() {

        try {
            SpecificForecastActivity specificForecastActivity = (SpecificForecastActivity) getActivity();
            ForecastDataModel forecastDataModel = specificForecastActivity.forecastDataModel;
            String dataFormat = specificForecastActivity.dataFormat;
            cityInfo.setText(forecastDataModel.location.city + ", " + forecastDataModel.location.region + ", " + forecastDataModel.location.country);
            latInfo.setText(forecastDataModel.location.latitude + "°");
            longInfo.setText(forecastDataModel.location.longitude + "°");
            temperatureInfo.setText(forecastDataModel.currentObservation.condition.temperature + (dataFormat.isEmpty() ? " F" : " C"));
            pressureInfo.setText(forecastDataModel.currentObservation.atmosphere.pressure + (dataFormat.isEmpty() ? " inchHg" : " mbar"));
            descriptionInfo.setText(forecastDataModel.currentObservation.condition.text);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
