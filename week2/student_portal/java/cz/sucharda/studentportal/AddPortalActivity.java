package cz.sucharda.studentportal;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cz.sucharda.studentportal.Model.ListItem;

public class AddPortalActivity extends AppCompatActivity {

    private EditText mTitle, mUrl;
    private Button mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_portal);

        mTitle = (EditText) findViewById(R.id.aap_editText_title);
        mUrl = (EditText) findViewById(R.id.aap_editText_url);
        mAdd = (Button) findViewById(R.id.aap_button_add);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                String url = mUrl.getText().toString();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(url)) {
                    Intent data = new Intent();
                    data.putExtra("newItem", new ListItem(title, url));
                    setResult(Activity.RESULT_OK, data);
                    finish();
                } else {
                    Toast.makeText(AddPortalActivity.this, "Fill in the required fields please!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
