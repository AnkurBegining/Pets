/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.pets.PetDpHelper;

import com.example.android.pets.pets.PetsContract.PetsEntry;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetDpHelper mDbHelper;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        //
        mDbHelper = new PetDpHelper(this);
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();
        displayDatabaseInfo();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        //PetDpHelper mDbHelper = new PetDpHelper(this);

        // Create and/or open a database to read from it


        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        // Cursor cursor = db.rawQuery("SELECT * FROM " + PetsEntry.TABLE_NAME, null);
        String[] projection =
                {
                        PetsEntry._ID,
                        PetsEntry.COLUMN_PETS_NAME,
                        PetsEntry.COLUMN_PETS_BREED,
                        PetsEntry.COLUMN_PETS_GENDER,
                        PetsEntry.COLUMN_PETS_WEIGHT};
       /* String selection = PetsEntry.COLUMN_PETS_GENDER + "=?";
        String[] selectionArgs = new String[]{String.valueOf(PetsEntry.GENDER_MALE)};*/
        /*Cursor cursor = db.query(PetsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);*/
        Cursor cursor=getContentResolver().query(PetsEntry.CONTENT_URI,
                projection,
                null,
                null,
                null,
                null
        );
        TextView displayView = (TextView) findViewById(R.id.text_view_pet);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).

            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
            displayView.append("\n"+
                               PetsEntry._ID + " - "+
                               PetsEntry.COLUMN_PETS_NAME+ " - " +
                               PetsEntry.COLUMN_PETS_BREED +" - "+
                               PetsEntry.COLUMN_PETS_GENDER+" - "+
                               PetsEntry.COLUMN_PETS_WEIGHT);
            int idColumnIndex = cursor.getColumnIndex(PetsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PETS_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PETS_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PETS_GENDER);
            int weightCoumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PETS_WEIGHT);

            while(cursor.moveToNext()){
                int currentId=cursor.getInt(idColumnIndex);
                String nameOfPet=cursor.getString(nameColumnIndex);
                String breedOfPet=cursor.getString(breedColumnIndex);
                int genderOfPet=cursor.getInt(genderColumnIndex);
                int weightOfPet=cursor.getInt(weightCoumnIndex);
                displayView.append("\n"+currentId+" - " +nameOfPet+" - "+breedOfPet+" - "+genderOfPet+" - "+weightOfPet);

            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertPet() {
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PetsEntry.COLUMN_PETS_NAME, "Toto");
        values.put(PetsEntry.COLUMN_PETS_BREED, "Terrier");
        values.put(PetsEntry.COLUMN_PETS_GENDER, PetsEntry.GENDER_MALE);
        values.put(PetsEntry.COLUMN_PETS_WEIGHT, 7);
        Uri newUri = getContentResolver().insert(PetsEntry.CONTENT_URI, values);
        /*long newRowId = db.insert(PetsEntry.TABLE_NAME, null, values);
        Log.v("CatalogActivity", "New ID" + newRowId);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
