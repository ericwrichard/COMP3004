package com.demo.grosavry.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.grosavry.DBhelper;
import com.demo.grosavry.DatabaseSingleton;
import com.demo.grosavry.LocationSingleton;
import com.demo.grosavry.R;

import java.util.ArrayList;
import java.util.Collections;

import java.lang.String;

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
        lv = getView().findViewById(R.id.list_view);
        lv.setAdapter(adapter);

        btnAddItem = (Button)view.findViewById(R.id.AddItemBtn);
        btnRmvItem = (Button)view.findViewById(R.id.RemoveAllBtn);
        btnSearchLoc = (Button) view.findViewById(R.id.SearchBtn);
        btnTotal = (Button)view.findViewById(R.id.TotalBtn);

        AddItem();
        RmvAllItem();
        SearchLoc();
        ViewTotal();
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
        layout.addView(itemName);

        final EditText itemQty = new EditText(context);
        itemQty.setInputType(InputType.TYPE_CLASS_NUMBER);
        itemQty.setHint("Quantity");
        itemQty.setText("1");
        layout.addView(itemQty);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isInserted = database.addItem(itemName.getText().toString(), itemQty.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(getActivity().getApplicationContext(), "Item Added", Toast.LENGTH_LONG).show();
                    shoppingList.add(itemName.getText().toString()+ " - " + itemQty.getText().toString());
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

    /*
    public  void AddLoc() {
        btnAddLoc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = database.addLoc(editLoc.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(getActivity().getApplicationContext(), "Location Added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity().getApplicationContext(), "Location Add failed!", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    */

    private void RmvAllItem() {
        btnRmvItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database.rmvAll();
                        Toast.makeText(getActivity().getApplicationContext(), "All Items Rrmoved", Toast.LENGTH_LONG).show();

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

    /*
    public  void RmvAllLoc() {
        btnRmvLoc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database.rmvAllLoc();
                        Toast.makeText(getActivity().getApplicationContext(), "All Locations Removed", Toast.LENGTH_LONG).show();


                    }
                }
        );
    }
    */

    /*
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
    */

    private void SearchLoc() {
        btnSearchLoc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database.rmvAllLoc(); // reset locations
                        // TODO
                        LocationSingleton.callLocation("10km"); // insert new locations
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

}