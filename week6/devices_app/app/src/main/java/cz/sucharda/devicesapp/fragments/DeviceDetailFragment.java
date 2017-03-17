package cz.sucharda.devicesapp.fragments;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import cz.sucharda.devicesapp.R;
import cz.sucharda.devicesapp.cursors.DeviceCursorWrapper;
import cz.sucharda.devicesapp.model.Device;
import cz.sucharda.devicesapp.model.database.DeviceProvider;

public class DeviceDetailFragment extends Fragment {

    private EditText mNameEditText;
    private EditText mValueEditText;

    private Uri mDeviceUri;
    private Device mDevice;

    public static DeviceDetailFragment newInstance(Uri deviceUri) {
        Bundle args = new Bundle();
        args.putParcelable(DeviceProvider.CONTENT_ITEM_TYPE, deviceUri);
        DeviceDetailFragment fragment = new DeviceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDeviceUri = getArguments().getParcelable(DeviceProvider.CONTENT_ITEM_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deviceDetailView = inflater.inflate(R.layout.fragment_device_detail, container, false);

        setActionBarTitle();

        mNameEditText = (EditText) deviceDetailView.findViewById(R.id.edit_text_device_name);
        mValueEditText = (EditText) deviceDetailView.findViewById(R.id.edit_text_device_value);

        if (mDeviceUri != null) {
            mDevice = getDeviceFromUri();
            mNameEditText.setText(mDevice.getName());
            mValueEditText.setText(String.valueOf(mDevice.getValue()));
        }

        return deviceDetailView;
    }

    private void setActionBarTitle() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            if (mDeviceUri != null) {
                actionBar.setTitle("Edit Device");
            } else {
                actionBar.setTitle("New Device");
            }
        }
    }

    private Device getDeviceFromUri() {
        String selection = Device.KEY_ID + "=" + mDeviceUri.getLastPathSegment();
        DeviceCursorWrapper cursor = new DeviceCursorWrapper(getActivity().getContentResolver().query(
                DeviceProvider.CONTENT_URI,
                new String[]{Device.KEY_NAME, Device.KEY_VALUES},
                selection,
                null,
                null
        ));
        // Moving our cursor to the first(and only) record.
        cursor.moveToFirst();
        // Getting our device from our wrapper.
        Device device = cursor.getDevice();
        // And finally closing our cursor to prevent leaks.
        cursor.close();
        return device;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_device_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            case R.id.menu_action_save:
                if (!fieldHasErrors(mNameEditText) & !fieldHasErrors(mValueEditText)) {
                    if (mDevice != null) {
                        updateDevice();
                    } else {
                        insertDevice();
                    }
                    getActivity().finish();
                }
                return true;
            case R.id.menu_action_delete:
                if (mDeviceUri != null) {
                    deleteDevice();
                }
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean fieldHasErrors(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Cannot be empty!");
            return true;
        }
        return false;
    }

    private void insertDevice() {
        ContentValues contentValues = getContentValues();
        getActivity().getContentResolver().insert(DeviceProvider.CONTENT_URI, contentValues);
    }

    private void updateDevice() {
        ContentValues contentValues = getContentValues();
        getActivity().getContentResolver().update(
                DeviceProvider.CONTENT_URI,
                contentValues,
                Device.KEY_ID + "=" + mDeviceUri.getLastPathSegment(),
                null
        );
    }
    private void deleteDevice() {
        getActivity().getContentResolver().delete(
                DeviceProvider.CONTENT_URI,
                Device.KEY_ID + "=" + mDeviceUri.getLastPathSegment(),
                null
        );
    }
    private ContentValues getContentValues() {
        String name = mNameEditText.getText().toString();
        double value = Double.valueOf(mValueEditText.getText().toString());
        if(mDevice == null) {
            mDevice = new Device(name, value);
        } else {
            mDevice.setName(name);
            mDevice.setValue(value);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Device.KEY_NAME, mDevice.getName());
        contentValues.put(Device.KEY_VALUES, mDevice.getValue());
        return contentValues;
    }

}
