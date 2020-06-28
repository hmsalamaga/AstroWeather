package astroweather.com.astro.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import astroweather.com.astro.fragments.MoonFragment;
import astroweather.com.astro.fragments.SunFragment;


class MyPagerAdapter extends FragmentStatePagerAdapter {

    private final SunFragment sunFragment = new SunFragment();
    private final MoonFragment moonFragment = new MoonFragment();

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
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