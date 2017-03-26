package com.example.android.pets.pets;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.R;

/**
 * PetCursorAdapter is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */

public class PetsCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new { PetCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */

    public PetsCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //  Fill out this method and return the list item view (instead of null)
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false );
    }


    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //  Fill out this method
        //find find to populate in template
        TextView nameTextView=(TextView)view.findViewById(R.id.name);
        TextView breedTextView=(TextView)view.findViewById(R.id.summary);

        //Extract properties from Cursor
        int nameColumnIndex = cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_PETS_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetsContract.PetsEntry.COLUMN_PETS_BREED);

        String nameOfPet=cursor.getString(nameColumnIndex);
        String breedOfPet=cursor.getString(breedColumnIndex);

        nameTextView.setText(nameOfPet);
        breedTextView.setText(breedOfPet);



    }
}
