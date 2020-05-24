package astroweather.com.astro;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsActivity extends Activity {

    private AppPreferenceManager appPreferenceManager;

    private EditText longitude;
    private EditText latitude;
    private EditText frequency;
    private Button ok;
    private static double longitudeValue;
    private static double latitudeValue;
    private static int frequencyRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        appPreferenceManager = new AppPreferenceManager(getApplicationContext());

        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        frequency = findViewById(R.id.frequency);
        ok = findViewById(R.id.ok);

        longitude.setText(String.valueOf(appPreferenceManager.loadLongitude()));
        latitude.setText(String.valueOf(appPreferenceManager.loadLatitude()));
        frequency.setText(String.valueOf(appPreferenceManager.loadRefreshFrequency()));

        onOkClicked();
    }

    private void onOkClicked() {

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ("".equals(longitude.getText().toString()) || "".equals(latitude.getText().toString()) || "".equals(frequency.getText().toString())) {
                    return;
                }
                try {
                    longitudeValue = Double.parseDouble(longitude.getText().toString());
                    latitudeValue = Double.parseDouble(latitude.getText().toString());
                    frequencyRefresh = Integer.parseInt(frequency.getText().toString());
                    if (longitudeValue < -180 || longitudeValue > 180) {
                        Toast.makeText(getApplicationContext(), "Longitude Input Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (latitudeValue < -90 || latitudeValue > 90) {
                        Toast.makeText(getApplicationContext(), "Latitude Input Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Input Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                appPreferenceManager.saveLongitude(longitudeValue);
                appPreferenceManager.saveLatitude(latitudeValue);
                appPreferenceManager.saveRefreshFrequency(frequencyRefresh);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("savedLongitude", longitude.getText().toString());
        savedInstanceState.putString("savedLatitude", latitude.getText().toString());
        savedInstanceState.putString("frequency", frequency.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        longitude.setText(savedInstanceState.getString("savedLongitude"));
        latitude.setText(savedInstanceState.getString("savedLatitude"));
        frequency.setText(savedInstanceState.getString("frequency"));
    }
}
