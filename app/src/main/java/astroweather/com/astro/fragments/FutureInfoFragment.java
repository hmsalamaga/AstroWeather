package astroweather.com.astro.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import astroweather.com.astro.R;
import astroweather.com.astro.activities.SpecificForecastActivity;
import astroweather.com.astro.adapters.CardAdapter;
import astroweather.com.astro.models.ForecastDataModel;
import astroweather.com.astro.models.ForecastDayModel;


public class FutureInfoFragment extends Fragment {

    View view;

    public ForecastDataModel forecastDataModel;
    public ArrayList<ForecastDayModel> forecasts = new ArrayList<>();
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;

    public FutureInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_future_info, container, false);

        update();
        return view;
    }

    public void update() {
        try {
            SpecificForecastActivity specificForecastActivity = (SpecificForecastActivity) requireActivity();
            forecastDataModel = specificForecastActivity.forecastDataModel;
            forecasts = new ArrayList<>(Arrays.asList(forecastDataModel.forecasts));

            recyclerView = view.findViewById(R.id.futureInfoCards);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new CardAdapter(forecasts, this, specificForecastActivity.dataFormat);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
