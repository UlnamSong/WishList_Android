package com.ulnamsong.todolist.DataBase;

import android.provider.BaseColumns;

/**
 * Created by Ulnamsong on 2016. 12. 9..
 */

public final class Databases {

    public static final class CreateDB implements BaseColumns {
        public static final String TITLE = "title";
        public static final String CATEGORY_INDEX = "category_index";
        public static final String CUR_VALUE = "cur_value";
        public static final String MAX_VALUE = "max_value";
        public static final String IMPORTANT_VALUE = "importancy_value";
        public static final String START_DATE = "start_date";
        public static final String RECENT_DATE = "recent_date";
        public static final String _TABLENAME = "address";
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        + _ID + " integer primary key autoincrement, "
                        + TITLE + " text not null , "
                        + CATEGORY_INDEX + " integer , "
                        + CUR_VALUE + " integer , "
                        + MAX_VALUE + " integer , "
                        + IMPORTANT_VALUE + " integer , "
                        + START_DATE + " text not null , "
                        + RECENT_DATE + " text not null );";
    }
}
