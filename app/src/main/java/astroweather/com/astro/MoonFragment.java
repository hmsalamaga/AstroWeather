package astroweather.com.astro;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextClock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MoonFragment extends Fragment {

    private TextView longitude;
    private TextView latitude;
    private TextView moonrise;
    private TextView moonset;
    private TextView newMoon;
    private TextView fullMoon;
    private TextView faze;
    private TextView lunarDay;
    private TextView lunarDayData;
    private TextClock time;
    private Context mContext = getActivity();

    final Handler handler=new Handler();

    private AppPreferenceManager appPreferenceManager;
    int refreshFrequency;
    double savedLongitude;
    double savedLatitude;

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
        lunarDay = view.findViewById(R.id.LunarDay);
        lunarDayData = view.findViewById(R.id.LunarDayData);
        return view;
    }

    public void setMoonData(double longitude, double latitude) {
        ArrayList<String> moonInfo = new Calculator(longitude, latitude).setMoonData();
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
        appPreferenceManager = new AppPreferenceManager(getActivity().getApplicationContext());
        savedLongitude = appPreferenceManager.loadLongitude();
        savedLatitude = appPreferenceManager.loadLatitude();
        refreshFrequency = appPreferenceManager.loadRefreshFrequency();

        setMoonData(savedLongitude, savedLatitude);
        Runnable updateTask=new Runnable() {
            @Override
            public void run() {
                setMoonData(savedLongitude, savedLatitude);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                time.setText(sdf.format(cal.getTime()));
                handler.postDelayed(this, 1000 );
            }
        };
        handler.postDelayed(updateTask, 5000);
    }
}
