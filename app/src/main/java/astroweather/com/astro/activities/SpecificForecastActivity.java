package astroweather.com.astro.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import astroweather.com.astro.adapters.CardAdapter;
import astroweather.com.astro.fragments.AdvancedInfoFragment;
import astroweather.com.astro.fragments.BasicInfoFragment;
import astroweather.com.astro.fragments.FutureInfoFragment;
import astroweather.com.astro.R;
import astroweather.com.astro.models.ForecastDataModel;
import astroweather.com.astro.models.ForecastDayModel;
import astroweather.com.astro.requests.ExampleRequest;
import astroweather.com.astro.requests.ExampleRequestManager;

public class SpecificForecastActivity extends AppCompatActivity {


    boolean tabletSize;
    String localizationData;
    public ForecastDataModel forecastDataModel;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    Button refresh;
    public String dataFormat = "";
    Button imperialSystem;
    Button metricSystem;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    boolean showErrorToast = true;

    BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
    AdvancedInfoFragment advancedInfoFragment = new AdvancedInfoFragment();
    FutureInfoFragment futureInfoFragment = new FutureInfoFragment(this);

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_forecast);
        getSupportActionBar().hide();
        localizationData = getIntent().getStringExtra("localizationInfo");
        showErrorToast = getIntent().getBooleanExtra("fromButton", false);
        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData(true);
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(localizationData, MODE_PRIVATE);
        dataFormat = sharedPreferences.getString("dataFormat", "");

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

//        tabletSize = getResources().getBoolean(R.bool.isTablet);
//
//        if (tabletSize && savedInstanceState==null) {
//            fragmentManager = getSupportFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.basicContent, basicInfoFragment,"fragmentBasicInfo");
//            fragmentTransaction.add(R.id.advancedContent, advancedInfoFragment,"fragmentAdvancedInfo");
//            fragmentTransaction.add(R.id.futureContent, futureInfoFragment,"fragmentFutureInfo");
//            fragmentTransaction.commit();
//
//        }
//        else if (tabletSize && savedInstanceState!=null)
//        {
//            fragmentManager = getSupportFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.basicContent, basicInfoFragment,"fragmentBasicInfo");
//            fragmentTransaction.replace(R.id.advancedContent, advancedInfoFragment,"fragmentAdvancedInfo");
//            fragmentTransaction.replace(R.id.futureContent, futureInfoFragment,"fragmentFutureInfo");
//            fragmentTransaction.commit();
//        }
//        else {
        pager = findViewById(R.id.forecastContainer);
        pagerAdapter = new ListPagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(pagerAdapter);
//        }

        refreshData(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data", dataFormat);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataFormat = savedInstanceState.getString("data");
        refreshData(false);
        showErrorToast = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void updateFragments() {
        basicInfoFragment.update();
        advancedInfoFragment.update();
        futureInfoFragment.update();
    }

    public void refreshData(final boolean fromButton) {
        ExampleRequest request = new ExampleRequest(Request.Method.GET, null, null, new Response.Listener() {
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
                editor.commit();
                forecastDataModel = gson.fromJson(jsonString, ForecastDataModel.class);
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
        ExampleRequestManager requestManager = ExampleRequestManager.getInstance(this);
        request.setCity(localizationData);
        request.setDataFormat(dataFormat);
        requestManager.addToRequestQueue(request);
    }


    class ListPagerAdapter extends PagerAdapter {

        FragmentManager fragmentManager;
        Fragment[] fragments;
        SpecificForecastActivity activity;

        ListPagerAdapter(FragmentManager fm, SpecificForecastActivity activity) {
            fragmentManager = fm;
            fragments = new Fragment[3];
            this.activity = activity;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            assert (0 <= position && position < fragments.length);
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.remove(fragments[position]);
            trans.commit();
            fragments[position] = null;
        }

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
        public boolean isViewFromObject(View view, Object fragment) {
            return ((Fragment) fragment).getView() == view;
        }

        public Fragment getItem(int position) {
            assert (0 <= position && position < fragments.length);
            if (fragments[position] == null) {
                if (position == 0)
                    fragments[position] = (basicInfoFragment = new BasicInfoFragment());
                else if (position == 1)
                    fragments[position] = (advancedInfoFragment = new AdvancedInfoFragment());
                else if (position == 2) {
                    fragments[position] = (futureInfoFragment = new FutureInfoFragment(activity));
                }
            }
            return fragments[position];
        }
    }

}
