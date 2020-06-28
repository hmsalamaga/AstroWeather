package astroweather.com.astro.activities;

import android.content.Intent;
import android.content.res.Configuration;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import astroweather.com.astro.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        if (screenSize != Configuration.SCREENLAYOUT_SIZE_LARGE && screenSize != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            viewPager = findViewById(R.id.ViewPager);
            viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(getResources().getString(R.string.settings)).setIntent(new Intent(this, SettingsActivity.class));
        menu.add("Prognoza").setIntent(new Intent(this, LocalizationListActivity.class));
        return true;
    }

    public void onBackPressed() {
        try {
            if (viewPager.getCurrentItem() == 0) {
                super.onBackPressed();
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        } catch (NullPointerException e) {
            System.exit(1);
        }
    }
}
