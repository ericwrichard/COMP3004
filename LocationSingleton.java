package com.demo.grosavry;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.HashMap;

public class LocationSingleton {

    private static final LocationSingleton INSTANCE = new LocationSingleton();

    private static boolean nearWalmart = false;
    private static boolean nearSobeys = false;
    private static boolean nearFarmboy = false;
    private static boolean nearFrescho = false;
    private static boolean nearLoblaws = false;

    private static String googleKey = "AIzaSyAQLSXt0c-M-meCzCbrOLp8dhdD1gYVqp8";

    private static double lat = 0.0;
    private static double longi = 0.0;

    public static final int REQUEST_LOCATION = 1;
    private static LocationManager lm;

    private static Activity activity;
    private static DBhelper myDb;

    private LocationSingleton() {}

    public static LocationSingleton getInstance(){
        return INSTANCE;
    }

    public static void setDatabase(DBhelper database){
        myDb = database;
    }

    public static void setActivity(Activity currentActivity){
        activity = currentActivity;
    }

    public static void callLocation(String radius) {
        lm = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
        getLocation(radius);
    }

    public static void getLocation(String radius){

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }

        else {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                lat = location.getLatitude();
                longi = location.getLongitude();

                HashMap<String, String> radius_conversion = new HashMap<>();

                radius_conversion.put("2km", "2000");
                radius_conversion.put("5km", "5000");
                radius_conversion.put("10km", "10000");
                radius_conversion.put("20km", "20000");
                radius_conversion.put("30km", "30000");

                String current_radius = radius_conversion.get(radius);
                Log.d("Error:", radius);

                String uWalmart = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=" + current_radius + "&name=walmart&key=" + googleKey;
                String uLoblaws = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=" + current_radius + "&name=loblaws&key=" + googleKey;
                String uFarmboy = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=" + current_radius + "&name=farmboy&key=" + googleKey;
                String uFreshco = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=" + current_radius + "&name=freshco&key=" + googleKey;
                String uSobeys = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=" + current_radius + "&name=sobeys&key=" + googleKey;

                Log.d("Error", uWalmart);
                /*
                String uWalmart = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=5000&name=walmart&key=" + googleKey;
                String uLoblaws = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=100&name=loblaws&key=" + googleKey;
                String uFarmboy = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=100&name=farmboy&key=" + googleKey;
                String uFreshco = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=100&name=freshco&key=" + googleKey;
                String uSobeys = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=30&name=sobeys&key=" + googleKey;
                */

                Stores walmart = (Stores) new Stores(new Stores.StoreResponse() {
                    @Override
                    public void finishedResult(String result) {

                        if (result == "true") {
                            nearWalmart = true;
                            sendLocation(nearWalmart, "Walmart");

                        }
                    }
                }).execute(uWalmart);

                Stores loblaws = (Stores) new Stores(new Stores.StoreResponse() {
                    @Override
                    public void finishedResult(String result) {
                        if (result == "true") {
                            nearLoblaws = true;
                            sendLocation(nearLoblaws, "Loblaws");

                        }
                    }
                }).execute(uLoblaws);

                Stores farmboy = (Stores) new Stores(new Stores.StoreResponse() {
                    @Override
                    public void finishedResult(String result) {

                        if (result == "true") {
                            nearFarmboy = true;
                            sendLocation(nearFarmboy, "Farm Boy");

                        }
                    }
                }).execute(uFarmboy);

                Stores freshco = (Stores) new Stores(new Stores.StoreResponse() {
                    @Override
                    public void finishedResult(String result) {
                        if (result == "true") {
                            nearFrescho = true;
                            sendLocation(nearFrescho, "Freshco");

                        }
                    }
                }).execute(uFreshco);

                Stores sobeys = (Stores) new Stores(new Stores.StoreResponse() {
                    @Override
                    public void finishedResult(String result) {
                        if (result == "true") {
                            nearSobeys = true;
                            sendLocation(nearSobeys, "Sobeys");

                        }
                    }
                }).execute(uSobeys);

            }
        }
    }


    public static void sendLocation(boolean b, String loc){
        if (b){
            Log.d("Error:", loc);
            myDb.addLoc(loc);
        }
    }

    public static void closestStore(String storeLat, String storeLong, String name){

        final String storeName = name;
        String findDist = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + lat + "," + longi + "&destinations=" + storeLat + storeLong;

        StoreInfo find = (StoreInfo) new StoreInfo(new StoreInfo.StoreInformation() {
            @Override
            public void finishedResult(String result) {
                String[] arr = new String[2];
                arr = result.split(",");
                String dis = arr[0].replaceAll("\"", "");
                String address = arr[1].replaceAll("\"", "");
                Log.d("information", "Dist: " + dis + " Address: " + address + storeName);

            }
        }).execute(findDist);

    }
}

