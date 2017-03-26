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
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.pets.PetDpHelper;
import com.example.android.pets.pets.PetsContract.PetsEntry;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /** EditText field to enter the pet's name */
    private EditText mNameEditText;

    /** EditText field to enter the pet's breed */
    private EditText mBreedEditText;

    /** EditText field to enter the pet's weight */
    private EditText mWeightEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;

    private PetDpHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();
        mDbHelper=new PetDpHelper(this);
    }
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        //PetDpHelper mDbHelper = new PetDpHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

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
        Cursor cursor = db.query(PetsEntry.TABLE_NAME, projection, null, null, null, null, null);
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

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }

    private void insertData(){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        String nameOfPet=mNameEditText.getText().toString().trim();
        String nameOfBreed=mBreedEditText.getText().toString().trim();
        //String genderSelected=mGenderSpinner.getSelectedItem().toString();
        String valueOfmeasurement=mWeightEditText.getText().toString().trim();
        int weight=Integer.parseInt(valueOfmeasurement);
        values.put(PetsEntry.COLUMN_PETS_NAME,nameOfPet);
        values.put(PetsEntry.COLUMN_PETS_BREED,nameOfBreed);
        values.put(PetsEntry.COLUMN_PETS_GENDER,mGender);
        values.put(PetsEntry.COLUMN_PETS_WEIGHT,weight);

        // Insert a new pet into the provider, returning the content URI for the new pet.
        Uri newUri = getContentResolver().insert(PetsEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                    Toast.LENGTH_SHORT).show();
        }
       /* long nameOfId=db.insert(PetsEntry.TABLE_NAME,null,values);
        Log.v("EditorActivity","Number of Id = "+nameOfId);
        if(nameOfId== -1){
            Toast.makeText(this,"ERROR "+nameOfId , Toast.LENGTH_LONG ).show();
        }
        else {
            Toast.makeText(this, "Pets Saved with ID " + nameOfId, Toast.LENGTH_LONG).show();
        }*/
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetsEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetsEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetsEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertData();
                displayDatabaseInfo();
                finish();

                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}