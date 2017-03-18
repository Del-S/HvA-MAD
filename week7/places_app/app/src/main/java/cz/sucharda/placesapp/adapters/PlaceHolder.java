package cz.sucharda.placesapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cz.sucharda.placesapp.R;

public class PlaceHolder extends RecyclerView.ViewHolder {
    public TextView mNameTextView;
    public TextView mCityTextView;
    public TextView mLatitudeTextView;
    public TextView mLongitudeTextView;

    public PlaceHolder(View itemView) {
        super(itemView);

        mNameTextView = (TextView) itemView.findViewById(R.id.rip_title);
        mCityTextView = (TextView) itemView.findViewById(R.id.rip_city);
        mLatitudeTextView = (TextView) itemView.findViewById(R.id.rip_latitude);
        mLongitudeTextView = (TextView) itemView.findViewById(R.id.rip_longitude);

    }
}
