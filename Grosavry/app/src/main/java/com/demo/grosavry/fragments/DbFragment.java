package com.demo.grosavry.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.ContextThemeWrapper;

import com.demo.grosavry.DBhelper;
//import com.demo.grosavry.ItemDB;
import com.demo.grosavry.R;
import com.demo.grosavry.Stores;

public class DbFragment extends Fragment {
    public DBhelper myDb;
    EditText editItem, editQty, editLoc;
    Button btnAddItem, btnRmvItem, btnViewItem, btnAddLoc, btnRmvLoc, btnViewLoc, btnTotal;

    String googleKey = "AIzaSyAQLSXt0c-M-meCzCbrOLp8dhdD1gYVqp8";
    static final int REQUEST_LOCATION = 1;
    LocationManager lm;

    boolean nearWalmart = false;
    boolean nearSobeys = false;
    boolean nearFarmboy = false;
    boolean nearFrescho = false;
    boolean nearLoblaws = false;

    public DbFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DBhelper(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_item_db, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //ImageView imageView = (ImageView) getView().findViewById(R.id.foo);
        // or  (ImageView) view.findViewById(R.id.foo);

        //setContentView(R.layout.activity_item_db);

        editItem = (EditText)view.findViewById(R.id.ItemName);
        editQty = (EditText)view.findViewById(R.id.ItemQty);
        editLoc = (EditText)view.findViewById(R.id.LocationName);

        btnAddItem = (Button)view.findViewById(R.id.AddItem);
        btnViewItem = (Button)view.findViewById(R.id.ViewItem);
        btnRmvItem = (Button)view.findViewById(R.id.DeleteItem);

        btnAddLoc = (Button)view.findViewById(R.id.AddLoc);
        btnViewLoc = (Button)view.findViewById(R.id.ViewLoc);
        btnRmvLoc = (Button)view.findViewById(R.id.RmvLoc);

        btnTotal = (Button)view.findViewById(R.id.Total);

        //when the button is clikced the mwthod will be called.
        AddItem();
        AddLoc();
        //when the button is cliked the method will be called.
        ViewItem();
        ViewLoc();

        RmvAllItem();
        RmvAllLoc();

        ViewTotal();

        callLocation();
    }

    public  void AddItem() {
        btnAddItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.addItem(editItem.getText().toString(), editQty.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(getActivity().getApplicationContext(), "Item Added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity().getApplicationContext(), "Item Add failed!", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public  void AddLoc() {
        btnAddLoc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.addLoc(editLoc.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(getActivity().getApplicationContext(), "Location Added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity().getApplicationContext(), "Location Add failed!", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public  void RmvAllItem() {
        btnRmvItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDb.rmvAll();
                        Toast.makeText(getActivity().getApplicationContext(), "All Items Rrmoved", Toast.LENGTH_LONG).show();


                    }
                }
        );
    }

    public  void RmvAllLoc() {
        btnRmvLoc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDb.rmvAllLoc();
                        Toast.makeText(getActivity().getApplicationContext(), "All Locations Removed", Toast.LENGTH_LONG).show();


                    }
                }
        );
    }

    public void ViewItem() {
        btnViewItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getAllItem();
                        if (res.getCount() == 0) {
                            //Toast.makeText(ItemDB.this, "No item in the list", Toast.LENGTH_LONG).show();
                            showInfo("Error", "Nothing Found");
                            return ;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ItemName: "+ res.getString(0));
                            buffer.append(" Quantity: " + res.getString(1)+ "\n");
                        }

                        // show all the items
                        showInfo("Data", buffer.toString());
                    }
                }
        );
    }

    public void ViewLoc() {
        btnViewLoc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getAllLoc();
                        if (res.getCount() == 0) {
                            //Toast.makeText(ItemDB.this, "No item in the list", Toast.LENGTH_LONG).show();
                            showInfo("Error", "Nothing Found");
                            return ;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Location: "+ res.getString(0)+ "\n");
                        }

                        // show all the items
                        showInfo("Location", buffer.toString());
                    }
                }
        );
    }

    public void ViewTotal() {
        btnTotal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDb.getTotal();
                        if (res.getCount() == 0) {
                            //Toast.makeText(ItemDB.this, "No item in the list", Toast.LENGTH_LONG).show();
                            showInfo("Error", "Nothing Found");
                            return ;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Place: "+ res.getString(0)+ "\n");
                            buffer.append("Price: "+ res.getString(1)+ "\n\n");
                        }

                        // show all the items
                        showInfo("Total Price", buffer.toString());
                    }
                }
        );
    }

    public  void showInfo (String name, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setCancelable(true);
        builder.setTitle(name);
        builder.setMessage(text);
        builder.show();
    }

    public void callLocation() {
        lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation();
    }

    void getLocation(){

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }

        else {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                double lat = location.getLatitude();
                double longi = location.getLongitude();

                String uWalmart = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=5000&name=walmart&key=" + googleKey;
                String uLoblaws = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=100&name=loblaws&key=" + googleKey;
                String uFarmboy = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=100&name=farmboy&key=" + googleKey;
                String uFreshco = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=100&name=freshco&key=" + googleKey;
                String uSobeys = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + longi + "&radius=30&name=sobeys&key=" + googleKey;


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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;

        }
    }

    public void sendLocation(boolean b, String loc){
        if (b){
            Log.d("Error:", loc);
            myDb.addLoc(loc);
        }
    }
}
