package cz.sucharda.basketoffruit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import cz.sucharda.basketoffruit.Model.ListItem;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ListItem item = (ListItem) getIntent().getSerializableExtra("item");

        ImageView image = (ImageView) findViewById(R.id.ad_item_image);
        TextView title = (TextView) findViewById(R.id.ad_title);
        TextView desc = (TextView) findViewById(R.id.ad_description);

        image.setImageResource(item.getImageResource());
        title.setText(item.getTitle());
        desc.setText(item.getDescription());
    }
}
