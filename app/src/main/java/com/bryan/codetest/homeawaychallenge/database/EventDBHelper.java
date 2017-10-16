package com.bryan.codetest.homeawaychallenge.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "series.db";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + FeedReaderContract.EpisodeFeedEntry.TABLE_NAME + "("
            + FeedReaderContract.EpisodeFeedEntry._ID + " INTEGER PRIMARY KEY,"
            + FeedReaderContract.EpisodeFeedEntry.COLUMN_NAME_EVENT_ID + " TEXT,"
            + FeedReaderContract.EpisodeFeedEntry.COLUMN_NAME_EVENT_NAME + " TEXT" + ")";

    private String[] columns = {FeedReaderContract.EpisodeFeedEntry._ID,
            FeedReaderContract.EpisodeFeedEntry.COLUMN_NAME_EVENT_ID,
            FeedReaderContract.EpisodeFeedEntry.COLUMN_NAME_EVENT_NAME};

    public EventDBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + FeedReaderContract.EpisodeFeedEntry.TABLE_NAME);

        onCreate(db);
    }

    public void addEvent(String eventID, String eventName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FeedReaderContract.EpisodeFeedEntry.COLUMN_NAME_EVENT_ID, eventID);
        values.put(FeedReaderContract.EpisodeFeedEntry.COLUMN_NAME_EVENT_NAME, eventName);

        db.insert(FeedReaderContract.EpisodeFeedEntry.TABLE_NAME, null, values);

        Log.d(this.getClass().getSimpleName(), "New Entry: " + values);

        db.close();
    }

    public void deleteEvent (String eventID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FeedReaderContract.EpisodeFeedEntry.TABLE_NAME,
                FeedReaderContract.EpisodeFeedEntry.COLUMN_NAME_EVENT_ID + " = ?",
                new String[] { String.valueOf(eventID)});

        Log.d(this.getClass().getSimpleName(), "Deleted: " + eventID);

        db.close();
    }

    public boolean eventExists(String eventID) {

        boolean exists = false;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FeedReaderContract.EpisodeFeedEntry.TABLE_NAME,
                columns, FeedReaderContract.EpisodeFeedEntry.COLUMN_NAME_EVENT_ID + " = ?",
                new String[]{String.valueOf(eventID)}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            exists = true;
        }

        return exists;
    }
}
