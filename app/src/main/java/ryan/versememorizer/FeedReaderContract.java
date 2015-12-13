package ryan.versememorizer;

import android.provider.BaseColumns;

/**
 * Created by Ryan on 12/12/2015.
 */
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Verse";
        public static final String COLUMN_NAME_BOOK = "book";
        public static final String COLUMN_NAME_CH = "ch";
        public static final String COLUMN_NAME_START = "start";
        public static final String COLUMN_NAME_END = "end";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_MEMORIZED = "memorized";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_BOOK + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_CH + " INTEGER" + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_START + " INTEGER" + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_END + " INTEGER" + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_MEMORIZED + " INTEGER" +
                " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    }
}
