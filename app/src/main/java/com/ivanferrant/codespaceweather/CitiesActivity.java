package com.ivanferrant.codespaceweather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.ivanferrant.codespaceweather.adapter.SimpleFragmentPagerAdapter;
import com.ivanferrant.codespaceweather.helper.WeatherHelper;
import com.ivanferrant.codespaceweather.model.LocationWeather;
import com.ivanferrant.codespaceweather.network.RequestService;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class CitiesActivity extends AppCompatActivity {

    private final int PERMISSIONS_REQUEST_LOCATION = 2;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RequestService mRequestService;
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

        // Service to perform requests
        mRequestService = new RequestService();

        // Service to request last known location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestWeatherInLocation();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchMenuItem.collapseActionView();
                changeToolbarTitle(query);
                requestCurrentWeather(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            // If request is cancelled, the result arrays are empty
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestWeatherInLocation();
            }
        }
    }

    /**
     * Request the weather of the last known location.
     * Also request location permission if it's not already granted
     */
    private void requestWeatherInLocation() {
        int permission = ContextCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_COARSE_LOCATION);
        // Check location permission
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
        } else {
            // Get current location and request weather
            mFusedLocationClient
                    .getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            // Request weather based on device coordinates
                            Disposable d = mRequestService
                                    .currentWeather(location.getLatitude(), location.getLongitude())
                                    .subscribe(
                                        this::sendDataToFragments,
                                        this::handleError
                                    );
                            compositeDisposable.add(d);
                        }
                    });
        }
    }

    /**
     * Changes the toolbar title
     * @param title New title
     */
    private void changeToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title.toUpperCase());
        }
    }

    /**
     * Request current weather of <code>location</code>
     */
    private void requestCurrentWeather(String location) {
        Disposable d = mRequestService.currentWeather(location).subscribe(
                this::sendDataToFragments,
                this::handleError
        );
        compositeDisposable.add(d);
    }

    /**
     * Handle request error
     * @param t {@link Throwable} object
     */
    private void handleError(Throwable t) {
        Snackbar.make(this.findViewById(android.R.id.content),
                "Something went wrong", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Retrieve the list of fragments and sends the {@link LocationWeather}
     * object to the fragments which implements {@link OnLocationWeather}
     * @param locationWeather {@link LocationWeather} object with the weather information
     */
    private void sendDataToFragments(LocationWeather locationWeather) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment: fragments) {
            if (fragment instanceof OnLocationWeather) {
                ((OnLocationWeather) fragment).onData(new WeatherHelper(locationWeather));
            }
        }

    }

}
