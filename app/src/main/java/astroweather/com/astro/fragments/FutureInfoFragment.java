package astroweather.com.astro.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import astroweather.com.astro.R;
import astroweather.com.astro.activities.SpecificForecastActivity;
import astroweather.com.astro.adapters.CardAdapter;
import astroweather.com.astro.models.ForecastDataModel;
import astroweather.com.astro.models.ForecastDayModel;


public class FutureInfoFragment extends Fragment {

    View view;

    TextView day1Info;
    TextView day1LowInfo;
    TextView day1HighInfo;
    ImageView day1ImageView;

    TextView day2Info;
    TextView day2LowInfo;
    TextView day2HighInfo;
    ImageView day2ImageView;

    TextView day3Info;
    TextView day3LowInfo;
    TextView day3HighInfo;
    ImageView day3ImageView;

    TextView day4Info;
    TextView day4LowInfo;
    TextView day4HighInfo;
    ImageView day4ImageView;

    TextView day5Info;
    TextView day5LowInfo;
    TextView day5HighInfo;
    ImageView day5ImageView;

    TextView day6Info;
    TextView day6LowInfo;
    TextView day6HighInfo;
    ImageView day6ImageView;

    TextView day7Info;
    TextView day7LowInfo;
    TextView day7HighInfo;
    ImageView day7ImageView;

    public ForecastDataModel forecastDataModel;
    public ArrayList<ForecastDayModel> forecasts = new ArrayList<>();
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private SpecificForecastActivity activity;

    public FutureInfoFragment(SpecificForecastActivity activity) {
        this.activity = activity;
        // Required empty public constructor
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
            forecastDataModel = activity.forecastDataModel;
            forecasts = new ArrayList<>(Arrays.asList(forecastDataModel.forecasts));

            recyclerView = view.findViewById(R.id.futureInfoCards);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new CardAdapter(forecasts, this);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
