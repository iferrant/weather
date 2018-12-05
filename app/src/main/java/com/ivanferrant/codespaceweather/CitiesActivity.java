package com.ivanferrant.codespaceweather;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.ivanferrant.codespaceweather.adapter.SimpleFragmentPagerAdapter;
import com.ivanferrant.codespaceweather.model.LocationWeather;
import com.ivanferrant.codespaceweather.network.RequestService;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class CitiesActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(
                MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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

    /**
     * Request current weather of <code>location</code>
     */
    private void requestCurrentWeather(String location) {
        RequestService requestService = new RequestService();
        Disposable d = requestService.currentWeather(location).subscribe(
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
                ((OnLocationWeather) fragment).onData(locationWeather);
            }
        }

    }

}
