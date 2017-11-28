package com.demo.grosavry.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.grosavry.DBhelper;
import com.demo.grosavry.DatabaseSingleton;
import com.demo.grosavry.LocationSingleton;
import com.demo.grosavry.R;

import java.util.ArrayList;

import java.lang.String;
import java.util.List;

public class GroceryList extends Fragment{

    private ListView lv = null;
    private ArrayAdapter<String> adapter = null;
    private ArrayList<String> shoppingList = null;

    private Button btnAddItem, btnRmvItem, btnSearchLoc, btnTotal;

    private DBhelper database;

    public GroceryList() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.grocery_list, container, false);
    }

    private void showInfo (String name, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setCancelable(true);
        builder.setTitle(name);
        builder.setMessage(text);
        builder.show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        shoppingList = new ArrayList<>();

        Cursor res = database.getAllItem();

        while (res.moveToNext()) {
            shoppingList.add(res.getString(0) + " - " + res.getString(1));
        }

        //Collections.addAll(shoppingList, "Eggs", "Yogurt", "Milk", "Bananas", "Apples", "Eggs", "Yogurt", "Milk", "Bananas", "Apples");
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_black, R.id.list_content, shoppingList);
        lv = getView().findViewById(R.id.results_view);
        lv.setAdapter(adapter);

        final Context context = getActivity().getApplicationContext();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                  {
                                      @Override
                                      public void onItemClick(final AdapterView<?> parent, View view, int position, long id){

                                          final String value = (String) parent.getItemAtPosition(position);

                                          LinearLayout layout = new LinearLayout(context);
                                          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
                                          builder.setTitle("Update Item");

                                          // Set up the input
                                          final EditText itemQty = new EditText(context);
                                          // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                          itemQty.setInputType(InputType.TYPE_CLASS_NUMBER);
                                          itemQty.setHint("Quantity");
                                          itemQty.setHintTextColor(getResources().getColor(R.color.colorHint));
                                          layout.addView(itemQty);

                                          builder.setView(layout);

                                          String[] s = value.split("-");

                                          final String name = s[0].trim(), qty = s[1].trim();
                                          Log.d("String", name + qty);

                                          // Set up the buttons
                                          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int which) {

                                                  String itemQtyText = itemQty.getText().toString();
                                                  if(!itemQtyText.matches("\\d+")) itemQtyText = "1"; // check that quantity is an integer

                                                  boolean isInserted = database.addItem(name, itemQtyText);
                                                  if(isInserted){
                                                      Toast.makeText(getActivity().getApplicationContext(), "Item Changed", Toast.LENGTH_LONG).show();
                                                      for(int i = 0; i < shoppingList.size(); i++){
                                                          if(shoppingList.get(i).equals(value)){
                                                              shoppingList.set(i, name + " - " + itemQtyText);
                                                              break;
                                                          }
                                                      }
                                                      adapter.notifyDataSetChanged();
                                                  }
                                              }
                                          });

                                          builder.setNegativeButton("Remove Item", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                    database.rmvItem(name);
                                                    for(int i = 0; i < shoppingList.size(); i++){
                                                        if(shoppingList.get(i).equals(value)){
                                                            shoppingList.remove(i);
                                                            adapter.notifyDataSetChanged();
                                                            break;
                                                        }
                                                    }

                                              }
                                          });

                                          builder.show();

                                      }
                                  }
        );

        /*
        btnAddItem = (Button)view.findViewById(R.id.AddItemBtn);
        btnRmvItem = (Button)view.findViewById(R.id.RemoveAllBtn);
        btnSearchLoc = (Button) view.findViewById(R.id.SearchBtn);
        //btnTotal = (Button)view.findViewById(R.id.TotalBtn);
        //AddItem();
        RmvAllItem();
        SearchLoc();
        ViewTotal();
        */
    }

    private void AddItem() {
        btnAddItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getItemData();

                    }
                }
        );
    }

    public void getItemData(){
        Context context = getActivity().getApplicationContext();

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle("Add Item");

        // Set up the input
        final EditText itemName = new EditText(context);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        itemName.setInputType(InputType.TYPE_CLASS_TEXT);
        itemName.setHint("Item");
        itemName.setHintTextColor(getResources().getColor(R.color.colorHint));
        layout.addView(itemName);

        final EditText itemQty = new EditText(context);
        itemQty.setInputType(InputType.TYPE_CLASS_NUMBER);
        itemQty.setHint("Quantity");
        itemQty.setHintTextColor(getResources().getColor(R.color.colorHint));
        layout.addView(itemQty);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemNameText = itemName.getText().toString();
                String itemQtyText = itemQty.getText().toString();
                if(!itemQtyText.matches("\\d+")) itemQtyText = "1"; // check that quantity is an integer

                boolean isInserted = database.addItem(itemNameText, itemQtyText);
                if (isInserted == true) {
                    Toast.makeText(getActivity().getApplicationContext(), "Item Added", Toast.LENGTH_LONG).show();
                    shoppingList.add(itemNameText + "  -  " + itemQtyText);
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(getActivity().getApplicationContext(), "Item Add failed!", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void RmvAllItem() {
        btnRmvItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database.rmvAll();
                        Toast.makeText(getActivity().getApplicationContext(), "All Items Removed", Toast.LENGTH_LONG).show();

                        shoppingList.clear();

                        adapter.notifyDataSetChanged();
                        /*
                        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_black, R.id.list_content, shoppingList);
                        lv = getView().findViewById(R.id.list_view);
                        lv.setAdapter(adapter);
                        */
                    }
                }
        );
    }

    private void SearchLoc() {
        btnSearchLoc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database.rmvAllLoc(); // reset locations
                        LocationSingleton.callLocation(LocationSingleton.radius); // insert new locations
                    }
                }
        );
    }

    private void ViewTotal() {
        btnTotal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    getTotal();
                    }
                }
        );
    }

    public void getTotal(){
        Cursor res = database.getTotal();
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

    public void setRadius(){

        final List<String> radiusList = new ArrayList<>();

        radiusList.add("2km");
        radiusList.add("5km");
        radiusList.add("10km");
        radiusList.add("20km");
        radiusList.add("30km");

        int checkedItem = -1;
        for(int i = 0; i < radiusList.size(); i++) {
            if (radiusList.get(i).equals(LocationSingleton.radius)) {
                checkedItem = i;
                Log.d("Error: ", "Checked Item " + i);
                break;
            }
        }

        final Context context = LocationSingleton.activity;

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(LocationSingleton.activity, R.style.AlertDialogStyle);
        builder.setTitle("Set Search Radius");
        builder.setSingleChoiceItems(R.array.dist_array, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(radiusList.get(which) != LocationSingleton.radius){
                    LocationSingleton.radius = radiusList.get(which);
                    LocationSingleton.radiusChanged = true;
                }

                Toast.makeText(context, "Radius set to "+ radiusList.get(which), Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

}