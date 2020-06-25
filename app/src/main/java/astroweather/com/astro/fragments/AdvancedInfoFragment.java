package astroweather.com.astro.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import astroweather.com.astro.R;
import astroweather.com.astro.activities.SpecificForecastActivity;
import astroweather.com.astro.models.ForecastDataModel;


public class AdvancedInfoFragment extends Fragment {

    TextView windStrengthInfo;
    TextView windDirectionInfo;
    TextView humidityInfo;
    TextView visibilityInfo;

    public AdvancedInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_advanced_info, container, false);

        windStrengthInfo = view.findViewById(R.id.windStrengthInfo);
        windDirectionInfo = view.findViewById(R.id.windDirectionInfo);
        humidityInfo = view.findViewById(R.id.humidityInfo);
        visibilityInfo = view.findViewById(R.id.visibilityInfo);
        update();
        return view;
    }

    public void update()
    {
        try {
            ForecastDataModel forecastDataModel = ((SpecificForecastActivity) getActivity()).forecastDataModel;
            String dataFormat =  ((SpecificForecastActivity) getActivity()).dataFormat;
            windStrengthInfo.setText(forecastDataModel.currentObservation.wind.speed+(dataFormat.isEmpty()?" m/h":" km/h"));
            windDirectionInfo.setText(forecastDataModel.currentObservation.wind.direction+"°");
            humidityInfo.setText(forecastDataModel.currentObservation.atmosphere.humidity+"%");
            visibilityInfo.setText(forecastDataModel.currentObservation.atmosphere.visibility+(dataFormat.isEmpty()?" m":" km"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
