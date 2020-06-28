package astroweather.com.astro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

import astroweather.com.astro.fragments.AdvancedInfoFragment;
import astroweather.com.astro.fragments.BasicInfoFragment;
import astroweather.com.astro.fragments.FutureInfoFragment;
import astroweather.com.astro.R;
import astroweather.com.astro.models.ForecastDataModel;
import astroweather.com.astro.requests.GetForecastsRequest;
import astroweather.com.astro.requests.RequestManager;
import astroweather.com.astro.utils.AppPreferenceManager;

public class SpecificForecastActivity extends AppCompatActivity {


    String localizationData;
    public ForecastDataModel forecastDataModel;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    public String dataFormat = "";
    Button imperialSystem;
    Button metricSystem;
    boolean showErrorToast = true;

    BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
    AdvancedInfoFragment advancedInfoFragment = new AdvancedInfoFragment();
    FutureInfoFragment futureInfoFragment = new FutureInfoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_forecast);

        FloatingActionButton fab = findViewById(R.id.refresh_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData(true);
            }
        });
        localizationData = getIntent().getStringExtra("localizationInfo");
        showErrorToast = getIntent().getBooleanExtra("fromButton", false);
        SharedPreferences sharedPreferences = getSharedPreferences(localizationData, MODE_PRIVATE);
        dataFormat = sharedPreferences.getString("dataFormat", "");

        setUpImperialSystemOnClickEvent();
        setUpMetricSystemOnClickEvent();

        pager = findViewById(R.id.forecastContainer);
        pagerAdapter = new ListPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        refreshData(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(getResources().getString(R.string.settings)).setIntent(new Intent(this, LocalizationListActivity.class));
        menu.add("Astro").setIntent(new Intent(this, MainActivity.class));
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data", dataFormat);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataFormat = savedInstanceState.getString("data");
        refreshData(false);
        showErrorToast = false;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void updateFragments() {
        basicInfoFragment.update();
        advancedInfoFragment.update();
        futureInfoFragment.update();
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.refreshed), Toast.LENGTH_LONG).show();
    }

    public void refreshData(final boolean fromButton) {
        GetForecastsRequest request = new GetForecastsRequest(Request.Method.GET, null, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                SharedPreferences sharedPreferences = getSharedPreferences(localizationData, MODE_PRIVATE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                String jsonString = response.toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(localizationData, jsonString);
                editor.putString("dataFormat", dataFormat);
                editor.apply();
                forecastDataModel = gson.fromJson(jsonString, ForecastDataModel.class);
                AppPreferenceManager appPreferenceManager = new AppPreferenceManager(getApplicationContext());
                appPreferenceManager.saveLongitude(Double.parseDouble(forecastDataModel.location.longitude));
                appPreferenceManager.saveLatitude(Double.parseDouble(forecastDataModel.location.latitude));
                updateFragments();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (fromButton) {
                    Toast.makeText(getApplicationContext(), "Nie udalo sie polaczyc z baza. Pobieranie z pamieci lokalnej", Toast.LENGTH_LONG).show();
                }
                SharedPreferences sharedPreferences = getSharedPreferences(localizationData, MODE_PRIVATE);
                String jsonString = sharedPreferences.getString(localizationData, "");
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();
                Log.e("FORECAST", jsonString);
                forecastDataModel = gson.fromJson(jsonString, ForecastDataModel.class);
                updateFragments();
            }
        });
        RequestManager requestManager = RequestManager.getInstance(this);
        request.setCity(localizationData);
        request.setDataFormat(dataFormat);
        requestManager.addToRequestQueue(request);
    }

    public void setUpImperialSystemOnClickEvent() {
        imperialSystem = findViewById(R.id.imperialSystem);
        imperialSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    dataFormat = "";
                    refreshData(false);
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences(localizationData, MODE_PRIVATE);
                    dataFormat = sharedPreferences.getString("dataFormat", "");
                }
            }
        });
    }

    public void setUpMetricSystemOnClickEvent() {
        metricSystem = findViewById(R.id.metricSystem);
        metricSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    dataFormat = "&u=c";
                    refreshData(false);
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences(localizationData, MODE_PRIVATE);
                    dataFormat = sharedPreferences.getString("dataFormat", "");

                }
            }
        });
    }


    class ListPagerAdapter extends PagerAdapter {

        final FragmentManager fragmentManager;
        final Fragment[] fragments;

        ListPagerAdapter(FragmentManager fm) {
            fragmentManager = fm;
            fragments = new Fragment[3];
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (0 > position || position >= fragments.length) throw new IndexOutOfBoundsException();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.remove(fragments[position]);
            trans.commit();
            fragments[position] = null;
        }

        @NonNull
        @Override
        public Fragment instantiateItem(ViewGroup container, int position) {
            Fragment fragment = getItem(position);
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.add(container.getId(), fragment, "fragment:" + position);
            trans.commit();
            return fragment;
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object fragment) {
            return ((Fragment) fragment).getView() == view;
        }

        public Fragment getItem(int position) {
            if (0 > position || position >= fragments.length) throw new IndexOutOfBoundsException();
            if (fragments[position] == null) {
                if (position == 0)
                    fragments[position] = (basicInfoFragment = new BasicInfoFragment());
                else if (position == 1)
                    fragments[position] = (advancedInfoFragment = new AdvancedInfoFragment());
                else if (position == 2) {
                    fragments[position] = (futureInfoFragment = new FutureInfoFragment());
                }
            }
            return fragments[position];
        }
    }

}
