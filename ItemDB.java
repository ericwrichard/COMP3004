package com.demo.grosavry;

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
    EditText editItem, editQty;
    Button btnAddItem, btnRmvItem, btnViewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_db);
        myDb = new DBhelper(this);

        editItem = (EditText)findViewById(R.id.ItemName);
        editQty = (EditText)findViewById(R.id.ItemQty);
        btnAddItem = (Button)findViewById(R.id.AddItem);
        btnViewItem = (Button)findViewById(R.id.ViewItem);
        btnRmvItem = (Button)findViewById(R.id.DeleteItem);

        //when the button is clikced the mwthod will be called.
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
                            Toast.makeText(ItemDB.this, "Item Added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ItemDB.this, "Item Add failed!", Toast.LENGTH_LONG).show();

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
                        Toast.makeText(ItemDB.this, "All Items Rrmoved", Toast.LENGTH_LONG).show();


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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(name);
        builder.setMessage(text);
        builder.show();
    }
}
