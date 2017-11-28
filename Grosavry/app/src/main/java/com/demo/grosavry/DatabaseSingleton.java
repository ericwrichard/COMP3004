package com.demo.grosavry;

import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.demo.grosavry.DBhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DatabaseSingleton {
    private static final DatabaseSingleton INSTANCE = new DatabaseSingleton();



    public static ArrayAdapter<String> adapter = null;
    public static ArrayList<String> storeList = null;
    public static HashMap<String,DistAddress> storeDistAddress = null;

    private static DBhelper database;

    private DatabaseSingleton() {}

    public static DatabaseSingleton getInstance(){
        return INSTANCE;
    }

    public static void setDatabase(DBhelper db){
        database = db;
    }
    //public static void setStoreList(ArrayList<String> list){ storeList = list}


    public static DBhelper getDatabase(){
        return database;
    }

    public static void updateResults(Cursor cursor){
        storeList.clear();
        TreeMap<Float, String> stores = new TreeMap<>();
        while (cursor.moveToNext()) {
            stores.put(cursor.getFloat(1), cursor.getString(0));
        }

        for(Map.Entry<Float, String> entry : stores.entrySet()){
            Float cost = entry.getKey();
            String loc = entry.getValue();

            String dist = "0km";
            if(DatabaseSingleton.storeDistAddress.get(loc) != null) dist = DatabaseSingleton.storeDistAddress.get(loc).distance;
            storeList.add(loc + " : $" + String.format("%.2f", cost) + "\n" +
                    "Distance: " + dist);
        }

        adapter.notifyDataSetChanged();
    }

}
