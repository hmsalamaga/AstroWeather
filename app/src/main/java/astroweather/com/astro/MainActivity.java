package astroweather.com.astro;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager = null;
    final Handler handler = new Handler();


    Runnable task2 = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 5000);
            Log.e("H", "czas");
            Toast.makeText(MainActivity.this, getResources().getString(R.string.refreshed), Toast.LENGTH_SHORT).show();
        }
    };


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
        handler.postDelayed(task2, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(task2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(task2, 5000);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(task2);
    }
}
