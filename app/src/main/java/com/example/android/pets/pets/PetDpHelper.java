package com.example.android.pets.pets;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.pets.pets.PetsContract.PetsEntry;

/**
 * Created by Lenovo on 22-03-2017.
 */

public class PetDpHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Shelter.db";
    public static final String SQL_CREATE_ENTERIES =
            "CREATE TABLE " + PetsEntry.TABLE_NAME +
                    "(" + PetsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PetsEntry.COLUMN_PETS_NAME + " TEXT NOT NULL, "
                    + PetsEntry.COLUMN_PETS_BREED +  " TEXT, "
                    + PetsEntry.COLUMN_PETS_GENDER + " INTEGER NOT NULL, "
                    + PetsEntry.COLUMN_PETS_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PetsEntry.TABLE_NAME;

    public PetDpHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTERIES);
        Log.i("PetDpHelper", SQL_CREATE_ENTERIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
