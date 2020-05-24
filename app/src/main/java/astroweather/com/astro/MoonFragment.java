package astroweather.com.astro;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextClock;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MoonFragment extends Fragment {

    private TextView longitude;
    private TextView latitude;
    private TextView moonrise;
    private TextView moonset;
    private TextView newMoon;
    private TextView fullMoon;
    private TextView faze;
    private TextView lunarDayData;
    private TextClock time;
    private Context mContext = getActivity();

    final Handler handler = new Handler();

    int refreshFrequency;
    double savedLongitude;
    double savedLatitude;

    Runnable updateTimerTask = new Runnable() {
        @Override
        public void run() {
            setCurrentTime();
            handler.postDelayed(this, 1000);
        }
    };

    Runnable updateMoonDataTask = new Runnable() {
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

        time = view.findViewById(R.id.time);
        longitude = view.findViewById(R.id.longitude);
        latitude = view.findViewById(R.id.latitude);
        moonrise = view.findViewById(R.id.Moonrise);
        moonset = view.findViewById(R.id.Moonset);
        newMoon = view.findViewById(R.id.NewMoon);
        fullMoon = view.findViewById(R.id.FullMoon);
        faze = view.findViewById(R.id.Faze);
        lunarDayData = view.findViewById(R.id.LunarDayData);
        setCurrentTime();

        return view;
    }

    public void setMoonData(double latitude, double longitude) {
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

        handler.postDelayed(updateTimerTask, 1000);
        handler.postDelayed(updateMoonDataTask, refreshFrequency * 1000L);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(updateTimerTask);
        handler.removeCallbacks(updateMoonDataTask);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimerTask);
        handler.removeCallbacks(updateMoonDataTask);
    }

    public void setCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        time.setText(sdf.format(new Date()));
    }
}
