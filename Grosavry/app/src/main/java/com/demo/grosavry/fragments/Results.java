package com.demo.grosavry.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.demo.grosavry.DBhelper;
import com.demo.grosavry.DatabaseSingleton;
import com.demo.grosavry.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class Results extends Fragment {

    private ListView lv = null;
    //private ArrayAdapter<String> adapter = null;
    //private ArrayList<String> storeList = null;

    private DBhelper database;

    public Results(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = DatabaseSingleton.getInstance().getDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.results_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        DatabaseSingleton.storeList = new ArrayList<>();
        Cursor res = database.getTotal();
        DatabaseSingleton.adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_black, R.id.list_content,
                DatabaseSingleton.storeList);

        DatabaseSingleton.updateResults(res);

        //Collections.addAll(shoppingList, "Eggs", "Yogurt", "Milk", "Bananas", "Apples", "Eggs", "Yogurt", "Milk", "Bananas", "Apples");

        lv = getView().findViewById(R.id.results_view);
        lv.setAdapter(DatabaseSingleton.adapter);
    }


}
