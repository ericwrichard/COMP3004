package com.example.rong.grosavrydb;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemDB extends AppCompatActivity {
    DBhelper myDb;
    EditText editItem, editQty, editLoc;
    Button btnAddItem, btnRmvItem, btnViewItem, btnAddLoc, btnRmvLoc, btnViewLoc, btnTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_db);
        myDb = new DBhelper(this);

        editItem = (EditText)findViewById(R.id.ItemName);
        editQty = (EditText)findViewById(R.id.ItemQty);
        editLoc = (EditText)findViewById(R.id.LocationName);

        btnAddItem = (Button)findViewById(R.id.AddItem);
        btnViewItem = (Button)findViewById(R.id.ViewItem);
        btnRmvItem = (Button)findViewById(R.id.DeleteItem);

        btnAddLoc = (Button)findViewById(R.id.AddLoc);
        btnViewLoc = (Button)findViewById(R.id.ViewLoc);
        btnRmvLoc = (Button)findViewById(R.id.RmvLoc);

        btnTotal = (Button)findViewById(R.id.Total);

        //when the button is clikced the mwthod will be called.
        AddItem();
        AddLoc();
        //when the button is cliked the method will be called.
        ViewItem();
        ViewLoc();

        RmvAllItem();
        RmvAllLoc();

        ViewTotal();
    }

    public  void AddItem() {
        btnAddItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.addItem(editItem.getText().toString(), editQty.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(ItemDB.this, "Item Added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ItemDB.this, "Item Add failed!", Toast.LENGTH_LONG).show();

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
                            Toast.makeText(ItemDB.this, "Location Added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ItemDB.this, "Location Add failed!", Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ItemDB.this, "All Items Removed", Toast.LENGTH_LONG).show();


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
                        Toast.makeText(ItemDB.this, "All Locations Removed", Toast.LENGTH_LONG).show();


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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(name);
        builder.setMessage(text);
        builder.show();
    }
}
