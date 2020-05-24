package astroweather.com.astro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class MyPagerAdapter extends FragmentStatePagerAdapter {

    SunFragment sunFragment = new SunFragment();
    MoonFragment moonFragment = new MoonFragment();

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return sunFragment;
            case 1:
                return moonFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}