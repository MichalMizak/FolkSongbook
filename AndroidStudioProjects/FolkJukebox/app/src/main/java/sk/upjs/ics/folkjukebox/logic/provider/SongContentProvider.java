package sk.upjs.ics.folkjukebox.logic.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import sk.upjs.ics.folkjukebox.utilities.Defaults;

import static sk.upjs.ics.folkjukebox.utilities.Defaults.ALL_COLUMNS;
import static sk.upjs.ics.folkjukebox.utilities.Defaults.NO_GROUP_BY;
import static sk.upjs.ics.folkjukebox.utilities.Defaults.NO_HAVING;
import static sk.upjs.ics.folkjukebox.utilities.Defaults.NO_SELECTION;
import static sk.upjs.ics.folkjukebox.utilities.Defaults.NO_SELECTION_ARGS;
import static sk.upjs.ics.folkjukebox.utilities.Defaults.NO_SORT_ORDER;

import static android.content.ContentResolver.SCHEME_CONTENT;

public class SongContentProvider extends ContentProvider {

    private DatabaseOpenHelper databaseHelper;
    public static final UriMatcher uriMatcher;

    public static final int SONG = 1;
    public static final int STYLE = 2;
    public static final int ATTRIBUTE = 3;
    public static final int ATTRIBUTE_SONG = 4;

    public static final int SONG_ITEM = 11;

    public static final String AUTHORITY = Provider.AUTHORITY;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, Provider.Song.TABLE_NAME + "/#", SONG_ITEM);

        uriMatcher.addURI(AUTHORITY, Provider.Song.TABLE_NAME, SONG);
        uriMatcher.addURI(AUTHORITY, Provider.Style.TABLE_NAME, STYLE);
        uriMatcher.addURI(AUTHORITY, Provider.Attribute.TABLE_NAME, ATTRIBUTE);
        uriMatcher.addURI(AUTHORITY, Provider.AttributeSong.TABLE_NAME, ATTRIBUTE_SONG);


    }

    public SongContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        this.databaseHelper = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Log.d("SongContentProvider", "I got here");

        switch (uriMatcher.match(uri)) {
            case SONG:
                cursor = db.query(Provider.Song.TABLE_NAME,
                        projection, selection, selectionArgs, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
                cursor.setNotificationUri(getContext().getContentResolver(), Provider.SONG_CONTENT_URI);
                break;
            case STYLE:
                cursor = db.query(Provider.Style.TABLE_NAME,
                        projection, selection, selectionArgs, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
                cursor.setNotificationUri(getContext().getContentResolver(), Provider.STYLE_CONTENT_URI);
                break;
            case ATTRIBUTE:
                cursor = db.query(Provider.Attribute.TABLE_NAME,
                        projection, selection, selectionArgs, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
                cursor.setNotificationUri(getContext().getContentResolver(), Provider.ATTRIBUTE_CONTENT_URI);
                break;
            case ATTRIBUTE_SONG:
                cursor = db.query(Provider.AttributeSong.TABLE_NAME,
                        projection, selection, selectionArgs, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
                cursor.setNotificationUri(getContext().getContentResolver(), Provider.ATTRIBUTE_SONG_CONTENT_URI);
                break;

            case SONG_ITEM:
                int id = Integer.parseInt(uri.getPathSegments().get(1));
                cursor = findById(id);
                break;
            default:
                return Defaults.NO_CURSOR;
        }
        return cursor;
    }

    private Cursor findById(long id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String selection = Provider.Song._ID + " = " + id;

        final Cursor cursor = db.query(Provider.Song.TABLE_NAME, ALL_COLUMNS, selection,
                NO_SELECTION_ARGS, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);

        cursor.setNotificationUri(getContext().getContentResolver(), Provider.SONG_CONTENT_URI);

        return cursor;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    /*private Cursor listSongs() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(Provider.Song.TABLE_NAME, ALL_COLUMNS, NO_SELECTION, NO_SELECTION_ARGS, NO_GROUP_BY, NO_HAVING, NO_SORT_ORDER);
        return cursor;
    }*/
}
