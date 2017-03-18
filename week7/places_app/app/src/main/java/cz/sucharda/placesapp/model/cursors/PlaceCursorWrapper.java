package cz.sucharda.placesapp.model.cursors;

import android.database.Cursor;
import android.database.CursorWrapper;

import cz.sucharda.placesapp.model.Place;

public class PlaceCursorWrapper extends CursorWrapper {

    public PlaceCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Place getPlace() {
        String name = getString(getColumnIndex(Place.KEY_NAME));
        String city = getString(getColumnIndex(Place.KEY_CITY));
        double latitude = getDouble(getColumnIndex(Place.KEY_LATITUDE));
        double longitude = getDouble(getColumnIndex(Place.KEY_LONGITUDE));

        return new Place(name, city, latitude, longitude);
    }
}
