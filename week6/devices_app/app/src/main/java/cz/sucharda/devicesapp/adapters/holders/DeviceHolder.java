package cz.sucharda.devicesapp.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cz.sucharda.devicesapp.R;

public class DeviceHolder extends RecyclerView.ViewHolder {
    public TextView mNameTextView;
    public TextView mValueTextView;

    public DeviceHolder(View itemView) {
        super(itemView);
        mNameTextView = (TextView) itemView.findViewById(R.id.text_view_device_name);
        mValueTextView = (TextView) itemView.findViewById(R.id.text_view_device_value);
    }
}
