package cz.sucharda.placesapp.model.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import cz.sucharda.placesapp.model.Place;

public class PlaceProvider extends ContentProvider {

    private static final String AUTHORITY = "cz.sucharda.placesapp";
    private static final String BASE_PATH = "places";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int PLACES = 1;
    private static final int PLACE_ID = 2;
    public static final String CONTENT_ITEM_TYPE = "Places";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    static {
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH, PLACES);
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/#", PLACE_ID);
    }

    private SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        mDatabase = dbHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (URI_MATCHER.match(uri) == PLACE_ID) {
            selection = Place.KEY_ID + "=" + uri.getLastPathSegment();
        }
        // The data is filtered in the UI so the 'selection' argument is passed with it
        return mDatabase.query(Place.TABLE, Place.ALL_COLUMNS, selection, null, null, null, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id = mDatabase.insert(Place.TABLE, null, values);
        //Create the URI to pass back that includes the new primary key value.
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return mDatabase.delete(Place.TABLE, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return mDatabase.update(Place.TABLE, values, selection, selectionArgs);
    }
}