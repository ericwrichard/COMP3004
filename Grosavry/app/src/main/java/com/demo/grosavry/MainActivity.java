package com.demo.grosavry;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.demo.grosavry.R;
import com.demo.grosavry.fragments.DbFragment;
import com.demo.grosavry.fragments.GroceryList;
import com.demo.grosavry.fragments.OneFragment;
import com.demo.grosavry.fragments.Results;
import com.demo.grosavry.fragments.ThreeFragment;
import com.demo.grosavry.fragments.TwoFragment;

    /*
        results page

        loading screen
     */

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    //private DbFragment dbfrag = null;

    private DBhelper database = null;

    private GroceryList gl = null;
    private Results results = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DBhelper(this);
        //database.onUpgrade(database.getWritableDatabase(), 0, 1);
        DatabaseSingleton.setDatabase(database);
        LocationSingleton.setDatabase(database);
        LocationSingleton.setActivity(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LocationSingleton.REQUEST_LOCATION:
                LocationSingleton.getInstance().getLocation(LocationSingleton.radius);
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        gl = new GroceryList();
        results = new Results();

        adapter.addFragment(gl, "LIST");
        adapter.addFragment(results, "RESULTS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                gl.setRadius();
                return true;

            case R.id.action_add:

                gl.getItemData();

                return true;

            case R.id.action_total:
                //gl.getTotal();


                if(LocationSingleton.radiusChanged){
                    database.rmvAllLoc();
                    LocationSingleton.callLocation(LocationSingleton.radius);
                    LocationSingleton.radiusChanged = false;
                }

                //results.updateResults(database.getTotal());

                // switch tabs
                TabLayout tabs = (TabLayout) this.findViewById(R.id.tabs);

                tabs.getTabAt(1).select();

                // Search in given radius

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }




    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}