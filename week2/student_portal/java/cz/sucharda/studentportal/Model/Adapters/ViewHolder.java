package cz.sucharda.studentportal.Model.Adapters;

import android.view.View;
import android.widget.TextView;

import cz.sucharda.studentportal.Model.ListItem;
import cz.sucharda.studentportal.R;

public class ViewHolder {
    private TextView name, url;

    public ViewHolder(View view) {
        name = (TextView) view.findViewById(R.id.rl_textView_name);
        url = (TextView) view.findViewById(R.id.rl_textView_url);
    }

    public void populateRow(ListItem item) {
        name.setText(item.getTitle());
        url.setText(item.getUrl());
    }
}