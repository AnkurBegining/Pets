package com.example.android.pets.pets;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

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
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // TODO: Fill out this method and return the list item view (instead of null)
        return null;
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
        // TODO: Fill out this method

    }
}
