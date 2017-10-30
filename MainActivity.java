package com.demo.grosavry;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.location.LocationManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.demo.grosavry.R;
import com.demo.grosavry.fragments.OneFragment;
import com.demo.grosavry.fragments.ThreeFragment;
import com.demo.grosavry.fragments.TwoFragment;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String googleKey = "AIzaSyAQLSXt0c-M-meCzCbrOLp8dhdD1gYVqp8";
    static final int REQUEST_LOCATION = 1;
    LocationManager lm;

    boolean nearWalmart = false;
    boolean nearSobeys = false;
    boolean nearFarmboy = false;
    boolean nearFrescho = false;
    boolean nearLoblaws = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        callLocation();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "LOCATION");
        adapter.addFragment(new TwoFragment(), "RESULT");
        //adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
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



    public void callLocation() {
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();
    }

    void getLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }

        else {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                double lat = location.getLatitude();
                double longi = location.getLongitude();

                String uWalmart = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=50000&name=walmart&key=" + googleKey;
                String uLoblaws = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=3000&name=loblaws&key=" + googleKey;
                String uFarmboy = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=3000&name=farmboy&key=" + googleKey;
                String uFreshco = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=3000&name=freshco&key=" + googleKey;
                String uSobeys = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=3000&name=sobeys&key=" + googleKey;


               Stores walmart = (Stores) new Stores(new Stores.StoreResponse() {
                   @Override
                   public void finishedResult(String result) {

                      if (result == "true") {
                          nearWalmart = true;
                          calcWalmart(nearWalmart);

                      }
                   }
               }).execute(uWalmart);

                Stores loblaws = (Stores) new Stores(new Stores.StoreResponse() {
                    @Override
                    public void finishedResult(String result) {
                        if (result == "true") {
                            nearLoblaws = true;
                            calcLoblaws(nearLoblaws);

                        }
                    }
                }).execute(uLoblaws);

                Stores farmboy = (Stores) new Stores(new Stores.StoreResponse() {
                    @Override
                    public void finishedResult(String result) {

                        if (result == "true") {
                            nearFarmboy = true;
                            calcFarmboy(nearFarmboy);

                        }
                    }
                }).execute(uFarmboy);

                Stores freshco = (Stores) new Stores(new Stores.StoreResponse() {
                    @Override
                    public void finishedResult(String result) {
                        if (result == "true") {
                            nearFrescho = true;
                            calcLoblaws(nearFrescho);

                        }
                    }
                }).execute(uFreshco);

                Stores sobeys = (Stores) new Stores(new Stores.StoreResponse() {
                    @Override
                    public void finishedResult(String result) {
                        if (result == "true") {
                            nearSobeys = true;
                            calcLoblaws(nearSobeys);

                        }
                    }
                }).execute(uSobeys);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;

        }
    }

    public void calcWalmart(boolean b){
        if (b){

        }
    }

    public void calcLoblaws(boolean b){
        if (b){

        }
    }

    public void calcFarmboy(boolean b){
        if (b){

        }
    }

    public void calcFreshco(boolean b){
        if (b){

        }
    }
    public void calcSobeys(boolean b){
        if (b){

        }
    }
}