package astroweather.com.astro;

import android.content.Context;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    private final Handler handler = new Handler();


    private int refreshFrequency;
    private double savedLongitude;
    private double savedLatitude;
    private Context mContext;

    private final Runnable updateTimerTask = new Runnable() {
        @Override
        public void run() {
            setCurrentTime();
            handler.postDelayed(this, 1000);
        }
    };

    private final Runnable updateSunDataTask = new Runnable() {
        @Override
        public void run() {
            setSunData(savedLatitude, savedLongitude);
            Toast.makeText(mContext, getResources().getString(R.string.refreshed), Toast.LENGTH_SHORT).show();
            handler.postDelayed(this, refreshFrequency * 1000L);
        }
    };

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
        setCurrentTime();

        return view;
    }

    private void setSunData(double longitude, double latitude) {
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
        AppPreferenceManager appPreferenceManager = new AppPreferenceManager(getActivity().getApplicationContext());
        savedLongitude = appPreferenceManager.loadLongitude();
        savedLatitude = appPreferenceManager.loadLatitude();
        refreshFrequency = appPreferenceManager.loadRefreshFrequency();

        setSunData(savedLatitude, savedLongitude);

        handler.postDelayed(updateTimerTask, 1000);
        handler.postDelayed(updateSunDataTask, refreshFrequency * 1000L);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(updateTimerTask);
        handler.removeCallbacks(updateSunDataTask);
    }

    private void setCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        time.setText(sdf.format(new Date()));
    }
}