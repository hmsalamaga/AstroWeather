package astroweather.com.astro.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import astroweather.com.astro.R;
import astroweather.com.astro.models.ForecastDataModel;
import astroweather.com.astro.requests.ExampleRequest;
import astroweather.com.astro.requests.ExampleRequestManager;

public class LocalizationListActivity extends Activity {

    Button addLocalization;
    EditText editLocalization;
    LinearLayout buttonList;
    String[] buttons = new String[100];
    String buttonsJsonString;
    ForecastDataModel forecastDataModel;
    boolean valid=false;

    private void readButtonsFromMemory(){
        SharedPreferences sharedPreferences = getSharedPreferences("buttonsJsonString", MODE_PRIVATE);
        buttonsJsonString = sharedPreferences.getString("buttonsJsonString","");
        if(!buttonsJsonString.isEmpty()) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            buttons = gson.fromJson(buttonsJsonString, String[].class);
        }

        for (String s : buttons) {
            if (s != null && !s.isEmpty()) {
                final Button button = new Button(getApplicationContext());
                button.setText(s);
                button.setBackgroundColor(Color.parseColor("#AAAAAA"));
                button.setPadding(0, 10, 0, 10);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LocalizationListActivity.this, SpecificForecastActivity.class);
                        intent.putExtra("localizationInfo", button.getText().toString().replaceAll("\\s+", ""));
                        startActivity(intent);
                    }
                });
                buttonList.addView(button);
            } else
                break;
        }
    }

    private void saveButtonsToMemory(Button button)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        Gson gson = gsonBuilder.create();
        for(int i=0;i<buttons.length;i++)
            if(buttons[i]==null || buttons[i].isEmpty()) {
                buttons[i] = button.getText().toString();
                break;
            }
        buttonsJsonString = gson.toJson(buttons);
        SharedPreferences sharedPreferences = getSharedPreferences("buttonsJsonString", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("buttonsJsonString", buttonsJsonString);
        editor.commit();
    }

    private boolean validateName(final String localizationData)
    {
        forecastDataModel = null;
        valid=true;
        if(localizationData.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Nie znaleziono lokalizacji. Dane moga byc nieprawidlowe", Toast.LENGTH_LONG).show();
            return false;
        }
        try{
        ExampleRequest request = new ExampleRequest(Request.Method.GET, null, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                String jsonString = response.toString();
                forecastDataModel = gson.fromJson(jsonString, ForecastDataModel.class);
                if(forecastDataModel.location.woeid==null) valid = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                valid = false;
            }
        });
        ExampleRequestManager requestManager = ExampleRequestManager.getInstance(getApplicationContext());
        request.setCity(localizationData);
        requestManager.addToRequestQueue(request);
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Nie znaleziono lokalizacji. Dane moga byc nieprawidlowe", Toast.LENGTH_LONG).show();
        }

        return valid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        buttonList = findViewById(R.id.buttonList);
        readButtonsFromMemory();

        addLocalization = findViewById(R.id.addLocalization);
        editLocalization = findViewById(R.id.editLocalization);

        editLocalization.setHint("Nowa lokalizacja...");

        addLocalization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName(editLocalization.getText().toString())) return;
                final Button button = new Button(getApplicationContext());
                button.setText(editLocalization.getText());
                button.setBackgroundColor(Color.parseColor("#AAAAAA"));
                button.setPadding(0,10,0,10);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LocalizationListActivity.this,SpecificForecastActivity.class);
                        intent.putExtra("localizationInfo",button.getText().toString().replaceAll("\\s+",""));
                        startActivity(intent);
                    }
                });
                saveButtonsToMemory(button);
                buttonList.addView(button);
            }
        });

    }

}
