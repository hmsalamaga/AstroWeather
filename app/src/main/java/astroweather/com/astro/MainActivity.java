package astroweather.com.astro;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager = null;

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
