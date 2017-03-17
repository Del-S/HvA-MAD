package cz.sucharda.devicesapp.model.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import cz.sucharda.devicesapp.model.Device;

public class DeviceProvider extends ContentProvider {

    private static final String AUTHORITY = "cz.sucharda.deviceapp";
    private static final String BASE_PATH = "devices";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int DEVICES = 1;
    private static final int DEVICE_ID = 2;
    public static final String CONTENT_ITEM_TYPE = "Devices";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    static {
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH, DEVICES);
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/#", DEVICE_ID);
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
        if (URI_MATCHER.match(uri) == DEVICE_ID) {
            selection = Device.KEY_ID + "=" + uri.getLastPathSegment();
        }
        // The data is filtered in the UI so the 'selection' argument is passed with it
        return mDatabase.query(Device.TABLE, null, selection, null, null, null, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id = mDatabase.insert(Device.TABLE, null, values);
        //Create the URI to pass back that includes the new primary key value.
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return mDatabase.delete(Device.TABLE, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return mDatabase.update(Device.TABLE, values, selection, selectionArgs);
    }
}
