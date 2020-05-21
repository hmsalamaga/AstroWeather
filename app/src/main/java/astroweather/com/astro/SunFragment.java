package astroweather.com.astro;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SunFragment extends Fragment {

    private TextView longitude;
    private TextView latitude;
    private TextView sunriseTime;
    private TextView sunriseAzimuth;
    private TextView sunsetTime;
    private TextView sunsetAzimuth;
    private TextView twilightTime;
    private TextView dawnTime;
    private TextClock time;
    final Handler handler = new Handler();


    private AppPreferenceManager appPreferenceManager;
    int refreshFrequency;
    double savedLongitude;
    double savedLatitude;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sun_fragment, container, false);
        time = view.findViewById(R.id.time);
        longitude = view.findViewById(R.id.longitude);
        latitude = view.findViewById(R.id.latitude);
        sunriseTime = view.findViewById(R.id.SunriseTime);
        sunriseAzimuth = view.findViewById(R.id.SunriseAzimuth);
        sunsetTime = view.findViewById(R.id.SunsetTime);
        sunsetAzimuth = view.findViewById(R.id.SunsetAzimuth);
        twilightTime = view.findViewById(R.id.TwilightTime);
        dawnTime = view.findViewById(R.id.DawnTime);

        return view;
    }

    public void setSunData(double longitude, double latitude) {
        ArrayList<String> sunInfo = new Calculator(longitude, latitude).setSunData();
        this.longitude.setText(sunInfo.get(0));
        this.latitude.setText(sunInfo.get(1));
        this.sunriseTime.setText(sunInfo.get(2));
        this.sunriseAzimuth.setText(sunInfo.get(3));
        this.sunsetTime.setText(sunInfo.get(4));
        this.sunsetAzimuth.setText(sunInfo.get(5));
        this.twilightTime.setText(sunInfo.get(6));
        this.dawnTime.setText(sunInfo.get(7));

    }

    @Override
    public void onStart() {
        super.onStart();
        mContext = getActivity();
        appPreferenceManager = new AppPreferenceManager(getActivity().getApplicationContext());
        savedLongitude = appPreferenceManager.loadLongitude();
        savedLatitude = appPreferenceManager.loadLatitude();
        refreshFrequency = appPreferenceManager.loadRefreshFrequency();

        setSunData(savedLatitude, savedLongitude);
        Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                setSunData(savedLatitude, savedLongitude);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                time.setText(sdf.format(cal.getTime()));
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(updateTask, 5000);
    }

}