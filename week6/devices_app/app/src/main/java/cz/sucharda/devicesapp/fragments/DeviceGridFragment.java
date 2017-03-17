package cz.sucharda.devicesapp.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cz.sucharda.devicesapp.DeviceDetailActivity;
import cz.sucharda.devicesapp.R;
import cz.sucharda.devicesapp.model.Device;
import cz.sucharda.devicesapp.adapters.DeviceAdapter;
import cz.sucharda.devicesapp.model.database.DeviceProvider;

public class DeviceGridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private DeviceAdapter mDeviceAdapter;

    public static DeviceGridFragment newInstance() {
        return new DeviceGridFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deviceGridView = inflater.inflate(R.layout.fragment_device_grid, container, false);
        mDeviceAdapter = new DeviceAdapter(getActivity(), null);

        RecyclerView mDeviceRecycler = (RecyclerView) deviceGridView.findViewById(R.id.recycler_view_device_grid);
        mDeviceRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mDeviceRecycler.setAdapter(mDeviceAdapter);
        mDeviceRecycler.setHasFixedSize(true);

        FloatingActionButton fab = (FloatingActionButton) deviceGridView.findViewById(R.id.fab_add_device);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = DeviceDetailActivity.newIntent(getActivity(), null);
                startActivity(intent);
            }
        });
        return deviceGridView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), DeviceProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mDeviceAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDeviceAdapter.swapCursor(null);
    }
}
