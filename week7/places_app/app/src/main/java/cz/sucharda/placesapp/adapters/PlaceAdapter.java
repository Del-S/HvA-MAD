package cz.sucharda.placesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import cz.sucharda.placesapp.PlaceDetailActivity;
import cz.sucharda.placesapp.PlaceListActivity;
import cz.sucharda.placesapp.R;
import cz.sucharda.placesapp.model.Place;
import cz.sucharda.placesapp.model.cursors.PlaceCursorWrapper;
import cz.sucharda.placesapp.model.database.PlaceProvider;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceHolder> {

    private Context mContext;
    private PlaceCursorWrapper mCursor;

    public PlaceAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        swapCursor(cursor);
        setHasStableIds(true);
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View placeItemView = inflater.inflate(R.layout.row_item_place, parent, false);
        return new PlaceHolder(placeItemView);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        // When the cursor can't move to given position, it will crash and burn.
        if (mCursor == null || !mCursor.moveToPosition(position)) {
            throw new IllegalStateException();
        }

        // Getting the item id to pass it to PlaceDetailActivity.
        // final modifier is necessary for the variable to be used
        // in the anonymous class implementation of the OnClickListeners below.
        final long itemId = getItemId(position);

        // This is where our CursorWrapper comes into play, fetching our Place.
        final Place place = mCursor.getPlace();
        holder.mNameTextView.setText(place.getName());
        holder.mCityTextView.setText(place.getCity());
        holder.mLatitudeTextView.setText(String.format(Locale.US, "%.4f", place.getLatitude()));
        holder.mLongitudeTextView.setText(String.format(Locale.US, "%.4f", place.getLongitude()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlaceDetailActivity.class);
                Uri uri = Uri.parse(PlaceProvider.CONTENT_URI + "/" + itemId);
                intent.putExtra(PlaceProvider.CONTENT_ITEM_TYPE, uri);
                ((Activity) mContext).startActivityForResult(intent, PlaceListActivity.PLACE_DETAIL_REQUEST_CODE);
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
        int idColumnIndex = mCursor.getColumnIndex(Place.KEY_ID);
        return mCursor.getLong(idColumnIndex);
    }

    // This method is derived from CursorAdapter's implementation.
    public void swapCursor(Cursor cursor) {
        // If the given cursor isn't null, we can use it to fetch Place objects.
        // Otherwise we have to notify the adapter that there is no cursor and thus no data.
        if (cursor != null) {
            mCursor = new PlaceCursorWrapper(cursor);
            notifyDataSetChanged();
        } else {
            mCursor = null;
            notifyItemRangeRemoved(0, getItemCount());
        }
    }
}