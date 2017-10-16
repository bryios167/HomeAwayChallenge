package com.bryan.codetest.homeawaychallenge.database;

import android.provider.BaseColumns;

public class FeedReaderContract {

    private FeedReaderContract(){}

    public static final class EpisodeFeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "episodes_table";
        public static final String COLUMN_NAME_EVENT_ID = "event_id";
        public static final String COLUMN_NAME_EVENT_NAME = "event_name";
    }
}
