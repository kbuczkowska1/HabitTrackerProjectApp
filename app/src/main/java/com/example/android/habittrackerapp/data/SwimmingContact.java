package com.example.android.habittrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by Kasia on 2017-07-19.
 */

public class SwimmingContact {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private SwimmingContact() {
    }

    //Inner class that defines constant values for the swimming database table.
    //Each entry in the table represents a single swimming day.
    public static final class SwimmingEntry implements BaseColumns {

        //Name of database table for swimming.
        public final static String TABLE_NAME = "swimming";

        //Unique ID number for the swimming day (only for use in the database table).
        //Type: INTEGER
        public final static String _ID = BaseColumns._ID;

        //Swimming day.
        //Type: INTEGER
        public final static String COLUMN_DAY = "day";

        //Swimming style.
        //Type: TEXT
        public final static String COLUMN_STYLE = "style";

        //Swimming time.
        //Type: INTEGER
        public final static String COLUMN_TIME = "time";

        //note.
        //Type: TEXT
        public final static String COLUMN_NOTE = "note";

        //Possible values for the day of the swimming.
        public static final int DAY_MONDAY = 1;
        public static final int DAY_TUESDAY = 2;
        public static final int DAY_WEDNESDAY = 3;
        public static final int DAY_THURSDAY = 4;
        public static final int DAY_FRIDAY = 5;
        public static final int DAY_SATURDAY = 6;
        public static final int DAY_SUNDAY = 7;

    }

}


