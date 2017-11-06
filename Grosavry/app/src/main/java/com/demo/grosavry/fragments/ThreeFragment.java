package com.demo.grosavry.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.demo.grosavry.R;

import java.util.ArrayList;
import java.util.Collections;


public class ThreeFragment extends Fragment{

    private ListView lv = null;
    private ArrayAdapter<String> adapter = null;
    private ArrayList<String> shoppingList = null;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        shoppingList = new ArrayList<>();
        Collections.addAll(shoppingList, "Eggs", "Yogurt", "Milk", "Bananas", "Apples", "Tide with bleach", "Cascade");
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, shoppingList);
        lv = getView().findViewById(R.id.list_view);
        lv.setAdapter(adapter);
    }

}
