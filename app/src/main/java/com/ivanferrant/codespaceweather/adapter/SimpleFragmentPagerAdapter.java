package com.ivanferrant.codespaceweather.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ivanferrant.codespaceweather.CurrentWeatherFragment;
import com.ivanferrant.codespaceweather.FutureWeatherFragment;
import com.ivanferrant.codespaceweather.R;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0? new CurrentWeatherFragment() : new FutureWeatherFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.current_weather_title);
            case 1:
                return mContext.getString(R.string.future_weather_title);
            default:
                return null;
        }
    }

}
