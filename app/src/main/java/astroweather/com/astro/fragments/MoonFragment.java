package astroweather.com.astro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import astroweather.com.astro.utils.AppPreferenceManager;
import astroweather.com.astro.utils.Calculator;
import astroweather.com.astro.R;


public class MoonFragment extends Fragment {

    private TextView longitude;
    private TextView latitude;
    private TextView moonrise;
    private TextView moonset;
    private TextView newMoon;
    private TextView fullMoon;
    private TextView faze;
    private TextView lunarDayData;
    private Context mContext = getActivity();

    private final Handler handler = new Handler();

    private int refreshFrequency;
    private double savedLongitude;
    private double savedLatitude;

    private final Runnable updateMoonDataTask = new Runnable() {
        @Override
        public void run() {
            setMoonData(savedLatitude, savedLongitude);
            Toast.makeText(mContext, getResources().getString(R.string.refreshed), Toast.LENGTH_SHORT).show();
            handler.postDelayed(this, refreshFrequency * 1000);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.moon_fragment, container, false);

        longitude = view.findViewById(R.id.longitude);
        latitude = view.findViewById(R.id.latitude);
        moonrise = view.findViewById(R.id.Moonrise);
        moonset = view.findViewById(R.id.Moonset);
        newMoon = view.findViewById(R.id.NewMoon);
        fullMoon = view.findViewById(R.id.FullMoon);
        faze = view.findViewById(R.id.Faze);
        lunarDayData = view.findViewById(R.id.LunarDayData);

        return view;
    }

    private void setMoonData(double latitude, double longitude) {
        ArrayList<String> moonInfo = new Calculator(latitude, longitude).setMoonData();
        this.longitude.setText(moonInfo.get(0));
        this.latitude.setText(moonInfo.get(1));
        this.moonrise.setText(moonInfo.get(2));
        this.moonset.setText(moonInfo.get(3));
        this.newMoon.setText(moonInfo.get(4));
        this.fullMoon.setText(moonInfo.get(5));
        this.faze.setText(moonInfo.get(6));
        this.lunarDayData.setText(moonInfo.get(7));
    }

    @Override
    public void onStart() {
        super.onStart();
        mContext = getActivity();
        AppPreferenceManager appPreferenceManager = new AppPreferenceManager(getActivity().getApplicationContext());
        savedLongitude = appPreferenceManager.loadLongitude();
        savedLatitude = appPreferenceManager.loadLatitude();
        refreshFrequency = appPreferenceManager.loadRefreshFrequency();

        setMoonData(savedLatitude, savedLongitude);

        handler.postDelayed(updateMoonDataTask, refreshFrequency * 1000L);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(updateMoonDataTask);
    }
}
