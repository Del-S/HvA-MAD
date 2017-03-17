package cz.sucharda.devicesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.sucharda.devicesapp.DeviceDetailActivity;
import cz.sucharda.devicesapp.R;
import cz.sucharda.devicesapp.adapters.holders.DeviceHolder;
import cz.sucharda.devicesapp.cursors.DeviceCursorWrapper;
import cz.sucharda.devicesapp.model.Device;
import cz.sucharda.devicesapp.model.database.DeviceProvider;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceHolder> {

    private Context mContext;
    private DeviceCursorWrapper mCursor;

    public DeviceAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        swapCursor(cursor);
        setHasStableIds(true);
    }

    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View deviceItemView = inflater.inflate(R.layout.grid_item_device, parent, false);
        return new DeviceHolder(deviceItemView);
    }

    @Override
    public void onBindViewHolder(DeviceHolder holder, int position) {
        // When the cursor can't move to given position, it will crash and burn.
        if (mCursor == null || !mCursor.moveToPosition(position)) {
            throw new IllegalStateException();
        }

        // Getting the item id to pass it to PlaceDetailActivity.
        // final modifier is necessary for the variable to be used
        // in the anonymous class implementation of the OnClickListeners below.
        final long itemId = getItemId(position);

        // This is where our CursorWrapper comes into play, fetching our Place.
        final Device device = mCursor.getDevice();
        holder.mNameTextView.setText(device.getName());
        holder.mValueTextView.setText(String.valueOf(device.getValue()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(DeviceProvider.CONTENT_URI + "/" + itemId);
                Intent intent = DeviceDetailActivity.newIntent(mContext, uri);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        if (mCursor == null || !mCursor.moveToPosition(position)) {
            return 0;
        }
        int idColumnIndex = mCursor.getColumnIndex(Device.KEY_ID);
        return mCursor.getLong(idColumnIndex);
    }

    // This method is derived from CursorAdapter's implementation.
    public void swapCursor(Cursor cursor) {
        // If the given cursor isn't null, we can use it to fetch Device objects.
        // Otherwise we have to notify the adapter that there is no cursor and thus no data.
        if (cursor != null) {
            mCursor = new DeviceCursorWrapper(cursor);
            notifyDataSetChanged();
        } else {
            mCursor = null;
            notifyItemRangeRemoved(0, getItemCount());
        }
    }
}