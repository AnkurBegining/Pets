package com.example.android.pets.pets;

import android.provider.BaseColumns;

/**
 * Created by Lenovo on 22-03-2017.
 */

public final class PetsContract {
    public class PetsEntry implements BaseColumns{
        public static final String TABLE_NAME = "Pets";

        public static final String _ID=BaseColumns._ID;
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_BREED="breed";
        public static final String COLUMN_GENDER="gender";
        public static final String COLUMN_WEIGHT="weight";

        public static final int GENDER_UNKNOWN=0;
        public static final int GENDER_MALE=1;
        public static final int GENDER_FEMALE=2;

    }
}
