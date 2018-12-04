package com.ivanferrant.codespaceweather;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.ivanferrant.codespaceweather.adapter.SimpleFragmentPagerAdapter;


public class CitiesActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        // Set viewpager adapter
        ViewPager viewPager = findViewById(R.id.vp_weather);
        SimpleFragmentPagerAdapter adapter =
                new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Join tablayout with viewpager
        TabLayout tabLayout = findViewById(R.id.tl_weather);
        tabLayout.setupWithViewPager(viewPager);

    }
}
