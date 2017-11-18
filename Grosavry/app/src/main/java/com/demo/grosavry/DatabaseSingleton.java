package com.demo.grosavry;

import com.demo.grosavry.DBhelper;

public class DatabaseSingleton {
    private static final DatabaseSingleton INSTANCE = new DatabaseSingleton();

    private static DBhelper database;

    private DatabaseSingleton() {}

    public static DatabaseSingleton getInstance(){
        return INSTANCE;
    }

    public static void setDatabase(DBhelper db){
        database = db;
    }

    public static DBhelper getDatabase(){
        return database;
    }

}
