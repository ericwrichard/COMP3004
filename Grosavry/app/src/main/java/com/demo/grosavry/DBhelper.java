package com.demo.grosavry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rong on 2017-10-29.
 */

public class DBhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Item.db";

    public static final String TABLE_NAME = "Item_table";
    public static final String TABLE_NAME2 = "Shopping";
    public static final String TABLE_NAME3 = "Location";
    // columns for item table
    public static final String COL_1 = "PNUM";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "PRICE";
    public static final String COL_4 = "LOCATION";

    // attributes for shopping list
    public static final String ATR_1 = "NAME";
    public static final String ATR_2 = "QUANTITY";

    // attributes for location we can reuse the COL_4



    public  DBhelper (Context context) {
        super (context,DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Error: ", "Creating new database.");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("create table " + TABLE_NAME +" (PNUM INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PRICE REAL,LOCATION TEXT)");
        db.execSQL("create table " + TABLE_NAME2 +" (NAME TEXT,QUANTITY INTEGER)");
        db.execSQL("create table " + TABLE_NAME3 +" (LOCATION TEXT)");

        ContentValues values = new ContentValues();
        // 10 items for loblaws - item # 1
        values.put(COL_2, "Apple".toLowerCase());
        values.put(COL_3, 0.97);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item # 2
        values.put(COL_2, "Banana".toLowerCase());
        values.put(COL_3, 0.72);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #3
        values.put(COL_2, "Milk".toLowerCase());
        values.put(COL_3, 4.57);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #4
        values.put(COL_2, "Eggs".toLowerCase());
        values.put(COL_3, 2.62);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #5
        values.put(COL_2, "Bread".toLowerCase());
        values.put(COL_3, 2.00);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #6
        values.put(COL_2, "Cereal".toLowerCase());
        values.put(COL_3, 5.65);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #7
        values.put(COL_2, "Cheese".toLowerCase());
        values.put(COL_3, 4.92);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #8
        values.put(COL_2, "Peanut Butter".toLowerCase());
        values.put(COL_3, 5.79);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #9
        values.put(COL_2, "Beef Steak".toLowerCase());
        values.put(COL_3, 12.12);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item 10
        values.put(COL_2, "Chicken breast".toLowerCase());
        values.put(COL_3, 7.88);
        values.put(COL_4, "Loblaws".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // 10 items for walmart - item # 1
        values.put(COL_2, "Apple".toLowerCase());
        values.put(COL_3, 1.00);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item # 2
        values.put(COL_2, "Banana".toLowerCase());
        values.put(COL_3, 0.22);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #3
        values.put(COL_2, "Milk".toLowerCase());
        values.put(COL_3, 5.57);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #4
        values.put(COL_2, "Eggs".toLowerCase());
        values.put(COL_3, 2.12);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #5
        values.put(COL_2, "Bread".toLowerCase());
        values.put(COL_3, 2.22);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #6
        values.put(COL_2, "Cereal".toLowerCase());
        values.put(COL_3, 6.65);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #7
        values.put(COL_2, "Cheese".toLowerCase());
        values.put(COL_3, 3.92);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #8
        values.put(COL_2, "Peanut Butter".toLowerCase());
        values.put(COL_3, 6.79);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #9
        values.put(COL_2, "Beef Steak".toLowerCase());
        values.put(COL_3, 13.12);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item 10
        values.put(COL_2, "Chicken breast".toLowerCase());
        values.put(COL_3, 7.48);
        values.put(COL_4, "Walmart".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // 10 items for Freshco - item # 1
        values.put(COL_2, "Apple".toLowerCase());
        values.put(COL_3, 0.80);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item # 2
        values.put(COL_2, "Banana".toLowerCase());
        values.put(COL_3, 0.72);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #3
        values.put(COL_2, "Milk".toLowerCase());
        values.put(COL_3, 4.75);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #4
        values.put(COL_2, "Eggs".toLowerCase());
        values.put(COL_3, 2.32);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #5
        values.put(COL_2, "Bread".toLowerCase());
        values.put(COL_3, 1.99);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #6
        values.put(COL_2, "Cereal".toLowerCase());
        values.put(COL_3, 5.50);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #7
        values.put(COL_2, "Cheese".toLowerCase());
        values.put(COL_3, 3.85);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #8
        values.put(COL_2, "Peanut Butter".toLowerCase());
        values.put(COL_3, 4.69);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #9
        values.put(COL_2, "Beef Steak".toLowerCase());
        values.put(COL_3, 12.22);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item 10
        values.put(COL_2, "Chicken breast".toLowerCase());
        values.put(COL_3, 7.88);
        values.put(COL_4, "Freshco".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // 10 items for Farm Boy - item # 1
        values.put(COL_2, "Apple".toLowerCase());
        values.put(COL_3, 0.87);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item # 2
        values.put(COL_2, "Banana".toLowerCase());
        values.put(COL_3, 0.82);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #3
        values.put(COL_2, "Milk".toLowerCase());
        values.put(COL_3, 4.77);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #4
        values.put(COL_2, "Eggs".toLowerCase());
        values.put(COL_3, 2.22);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #5
        values.put(COL_2, "Bread".toLowerCase());
        values.put(COL_3, 2.15);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #6
        values.put(COL_2, "Cereal".toLowerCase());
        values.put(COL_3, 5.00);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #7
        values.put(COL_2, "Cheese".toLowerCase());
        values.put(COL_3, 5.00);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #8
        values.put(COL_2, "Peanut Butter".toLowerCase());
        values.put(COL_3, 5.99);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #9
        values.put(COL_2, "Beef Steak".toLowerCase());
        values.put(COL_3, 12.00);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item 10
        values.put(COL_2, "Chicken breast".toLowerCase());
        values.put(COL_3, 8.88);
        values.put(COL_4, "Farm Boy".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // 10 items for Sobeys - item # 1
        values.put(COL_2, "Apple".toLowerCase());
        values.put(COL_3, 1.12);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item # 2
        values.put(COL_2, "Banana".toLowerCase());
        values.put(COL_3, 0.95);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #3
        values.put(COL_2, "Milk".toLowerCase());
        values.put(COL_3, 5.17);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #4
        values.put(COL_2, "Eggs".toLowerCase());
        values.put(COL_3, 2.52);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #5
        values.put(COL_2, "Bread".toLowerCase());
        values.put(COL_3, 1.60);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #6
        values.put(COL_2, "Cereal".toLowerCase());
        values.put(COL_3, 6.65);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #7
        values.put(COL_2, "Cheese".toLowerCase());
        values.put(COL_3, 5.92);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #8
        values.put(COL_2, "Peanut Butter".toLowerCase());
        values.put(COL_3, 6.79);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item #9
        values.put(COL_2, "Beef Steak".toLowerCase());
        values.put(COL_3, 12.12);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // item 10
        values.put(COL_2, "Chicken breast".toLowerCase());
        values.put(COL_3, 10.88);
        values.put(COL_4, "Sobeys".toLowerCase());
        db.insert(TABLE_NAME,null,values);

        // import a txt file and use for loop to put all values into the Database
    }

    public boolean addItem(String name, String qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ATR_1, name.toLowerCase());
        values.put(ATR_2, Integer.parseInt(qty));

        long result = db.insert(TABLE_NAME2,null,values);
        if (result == -1)
            return false;
        else
            return true;

    }

    public  Cursor getAllItem() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor items = db.rawQuery("select * from " + TABLE_NAME2, null);
        return items;
    }

    public void rmvAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME2);
        //db.execSQL("create table " + TABLE_NAME2 +" (NAME TEXT,QUANTITY INTEGER)");
    }

    public boolean addLoc(String Loc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_4, Loc.toLowerCase());
        long result = db.insert(TABLE_NAME3,null,values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public  Cursor getAllLoc() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor items = db.rawQuery("select * from " + TABLE_NAME3, null);
        return items;
    }

    public void rmvAllLoc() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME3);
        //db.execSQL("create table " + TABLE_NAME2 +" (NAME TEXT,QUANTITY INTEGER)");
    }

    public  Cursor getTotal() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor items = db.rawQuery("SELECT LOCATION, SUM(PRICE * QUANTITY) FROM  ( SELECT * FROM " +
                TABLE_NAME + "  NATURAL JOIN (SELECT * FROM " + TABLE_NAME3 +
                " CROSS JOIN " + TABLE_NAME2 + ") ) GROUP BY LOCATION"  , null);

        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while(data.moveToNext())
            Log.d("Error:", data.getString(0) + " " + data.getString(1)
                    + " " +  data.getString(2) + " " +  data.getString(3));

        return items;


        //SELECT LOCATION, SUM(PRICE * QUANTITY) FROM  ( SELECT * FROM 'Item_Table' NATURAL
        //JOIN (SELECT * FROM 'Location' CROSS JOIN 'Shopping') ) GROUP BY LOCATION
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}