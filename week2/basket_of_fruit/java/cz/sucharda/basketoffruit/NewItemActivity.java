package cz.sucharda.basketoffruit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cz.sucharda.basketoffruit.Model.ListItem;

public class NewItemActivity extends AppCompatActivity {

    String[] spinnerItems = {"Apple", "Banana", "Pear"};
    Spinner mIconSpinner;
    Button mAddButton;
    TextView mTitle, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        mTitle = (TextView) findViewById(R.id.nia_et_title);
        mDescription = (TextView) findViewById(R.id.nia_et_description);
        mIconSpinner = (Spinner) findViewById(R.id.nia_s_icon);
        mAddButton = (Button) findViewById(R.id.nia_add_button);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerItems);
        mIconSpinner.setAdapter(spinnerAdapter);


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mTitle.getText()) && !TextUtils.isEmpty(mDescription.getText())) {

                    String title = mTitle.getText().toString();
                    String description = mDescription.getText().toString();
                    int imageResource;

                    switch (mIconSpinner.getSelectedItemPosition()) {
                        case 0:
                            imageResource = R.drawable.apple;
                            break;
                        case 1:
                            imageResource = R.drawable.banana;
                            break;
                        case 2:
                            imageResource = R.drawable.pear;
                            break;
                        default:
                            imageResource = R.mipmap.ic_launcher;
                            break;
                    }

                    ListItem newItem = new ListItem(title, description, imageResource);

                    Intent data = new Intent();
                    data.putExtra("NewItem", newItem);

                    setResult(Activity.RESULT_OK, data);

                    finish();
                } else {
                    Toast.makeText(NewItemActivity.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
