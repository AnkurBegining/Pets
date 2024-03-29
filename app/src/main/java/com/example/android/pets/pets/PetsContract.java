package com.example.android.pets.pets;

import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Lenovo on 22-03-2017.
 */

public final class PetsContract {

    /**
          * The "Content authority" is a name for the entire content provider, similar to the
          * relationship between a domain name and its website.  A convenient string to use for the
          * content authority is the package name for the app, which is guaranteed to be unique on the
          * device.
     */

    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    /**
          * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
          * the content provider.
     */

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
          * Possible path (appended to base content URI for possible URI's)
          * For instance, content://com.example.android.pets/pets/ is a valid path for
          * looking at pet data. content://com.example.android.pets/staff/ will fail,
          * as the ContentProvider hasn't been given any information on what to do with "staff".
     */

    public static final String PATH_PETS = "pets";






    public  static class  PetsEntry implements BaseColumns{

        /** The content URI to access the pet data in the provider */

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        public static final String TABLE_NAME = "Pets";

        public static final String _ID=BaseColumns._ID;
        public static final String COLUMN_PETS_NAME ="name";
        public static final String COLUMN_PETS_BREED ="breed";
        public static final String COLUMN_PETS_GENDER="gender";
        public static final String COLUMN_PETS_WEIGHT ="weight";

        public static final int GENDER_UNKNOWN=0;
        public static final int GENDER_MALE=1;
        public static final int GENDER_FEMALE=2;



    }
}
