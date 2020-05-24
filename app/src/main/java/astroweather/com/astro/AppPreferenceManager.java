package astroweather.com.astro;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class AppPreferenceManager {
    private static final String PREF_LONGITUDE = "savedLongitude";
    private static final String PREF_LATITUDE = "savedLatitude";
    private static final String PREF_FREQUENCY = "refresh";

    private static final Double DEFAULT_LONGITUDE = 19.45598330;
    private static final Double DEFAULT_LATITUDE = 51.75924850;
    private static final int DEFAULT_FREQUENCY = 1;


    private SharedPreferences sharedPreferences;

    public AppPreferenceManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void saveLongitude(Double longitude) {
        sharedPreferences.edit().putLong(PREF_LONGITUDE, Double.doubleToLongBits(longitude)).apply();
    }

    public void saveLatitude(Double latitude) {
        sharedPreferences.edit().putLong(PREF_LATITUDE, Double.doubleToLongBits(latitude)).apply();
    }

    public void saveRefreshFrequency(int frequency) {
        sharedPreferences.edit().putInt(PREF_FREQUENCY, frequency).apply();
    }

    public double loadLongitude() {
        return Double.longBitsToDouble(sharedPreferences.getLong(PREF_LONGITUDE, Double.doubleToLongBits(DEFAULT_LONGITUDE)));
    }

    public double loadLatitude() {
        return Double.longBitsToDouble(sharedPreferences.getLong(PREF_LATITUDE, Double.doubleToLongBits(DEFAULT_LATITUDE)));
    }

    public int loadRefreshFrequency() {
        return sharedPreferences.getInt(PREF_FREQUENCY, DEFAULT_FREQUENCY);
    }
}
