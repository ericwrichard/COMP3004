package com.demo.grosavry.fragments;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class DbFragment extends Fragment {
    DBhelper myDb;
    EditText editItem, editQty;
    Button btnAddItem, btnRmvItem, btnViewItem;

    public DbFragment() {
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
        return inflater.inflate(R.layout.activity_item_db, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //ImageView imageView = (ImageView) getView().findViewById(R.id.foo);
        // or  (ImageView) view.findViewById(R.id.foo);

        //setContentView(R.layout.activity_item_db);
        myDb = new DBhelper(getActivity().getApplicationContext());

        editItem = (EditText)view.findViewById(R.id.ItemName);
        editQty = (EditText)view.findViewById(R.id.ItemQty);
        btnAddItem = (Button)view.findViewById(R.id.AddItem);
        btnViewItem = (Button)view.findViewById(R.id.ViewItem);
        btnRmvItem = (Button)view.findViewById(R.id.DeleteItem);

        //when the button is clikced the method will be called.
        AddItem();
        //when the button is cliked the method will be called.
        ViewItem();
        RmvAllItem();
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

    public  void showInfo (String name, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setCancelable(true);
        builder.setTitle(name);
        builder.setMessage(text);
        builder.show();
    }
}
