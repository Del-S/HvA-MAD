package cz.sucharda.devicesapp.cursors;

import android.database.Cursor;
import android.database.CursorWrapper;

import cz.sucharda.devicesapp.model.Device;

public class DeviceCursorWrapper extends CursorWrapper {

    public DeviceCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Device getDevice() {
        String name = getString(getColumnIndex(Device.KEY_NAME));
        double value = getDouble(getColumnIndex(Device.KEY_VALUES));

        return new Device(name, value);
    }
}