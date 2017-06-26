package sk.upjs.ics.folkjukebox.logic.provider;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import sk.upjs.ics.folkjukebox.utilities.Defaults;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "folkJukebox";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, Defaults.DEFAULT_CURSOR_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseOpenHelper", "I got here");

    }


    private void createTables(SQLiteDatabase db) {

        String songSQL = "CREATE TABLE %s (\n" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "%s TEXT, \n" +
                "%s TEXT, \n" +
                "%s TEXT, \n" +
                "%s TEXT, \n" +
                "%s INTEGER" + ")";

        songSQL = String.format(songSQL,
                Provider.Song.TABLE_NAME,
                Provider.Song._ID,
                Provider.Song.COLUMN_NAMES.title.name(),
                Provider.Song.COLUMN_NAMES.lyrics.name(),
                Provider.Song.COLUMN_NAMES.region.name(),
                Provider.Song.COLUMN_NAMES.source.name(),
                Provider.Song.COLUMN_NAMES.styleId.name());

        db.execSQL(songSQL);


        String styleSQL = "CREATE TABLE %s (\n" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "%s TEXT" + ")";

        styleSQL = String.format(styleSQL,
                Provider.Style.TABLE_NAME,
                Provider.Style._ID,
                Provider.Style.COLUMN_NAMES.name.name());

        db.execSQL(styleSQL);


        String attributeSQL = "CREATE TABLE %s (\n" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "%s TEXT, \n" +
                "%s INTEGER" + ")";

        attributeSQL = String.format(attributeSQL,
                Provider.Attribute.TABLE_NAME,
                Provider.Attribute._ID,
                Provider.Attribute.COLUMN_NAMES.name.name(),
                Provider.Attribute.COLUMN_NAMES.styleId.name());

        db.execSQL(attributeSQL);


        String attributeSongSQL = "CREATE TABLE %s (\n" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "%s INTEGER, \n" +
                "%s INTEGER" + ")";

        attributeSongSQL = String.format(attributeSongSQL,
                Provider.AttributeSong.TABLE_NAME,
                Provider.AttributeSong._ID,
                Provider.AttributeSong.COLUMN_NAMES.attributeId.name(),
                Provider.AttributeSong.COLUMN_NAMES.styleId.name());

        db.execSQL(attributeSongSQL);

        //db.execSQL("TRUNCATE TABLE song");

        db.execSQL("INSERT INTO song VALUES (NULL, 'wats', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'sat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'sat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'aat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'aat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'qat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'wat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'sat', 'wat wat wat', 'watovce', 'pan wat', 0)");
        db.execSQL("INSERT INTO song VALUES (NULL, 'sat', 'wat wat wat', 'watovce', 'pan wat', 0)");

        db.execSQL("INSERT INTO style VALUES (0, 'Čardáš')");
    }

}