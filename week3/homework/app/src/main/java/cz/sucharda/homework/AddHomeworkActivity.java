package cz.sucharda.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import cz.sucharda.homework.Model.DB.HomeworkDataSource;
import cz.sucharda.homework.Model.Homework;

public class AddHomeworkActivity extends AppCompatActivity {

    private HomeworkDataSource mDataSource;
    private EditText mTitleEditText, mSubjectEditText, mDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDataSource = new HomeworkDataSource(this);
        mTitleEditText = (EditText) findViewById(R.id.aah_editText_title);
        mSubjectEditText = (EditText) findViewById(R.id.aah_editText_subject);
        mDateEditText = (EditText) findViewById(R.id.aah_editText_date);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Homework homework = new Homework();
                homework.setTitle(mTitleEditText.getText().toString());
                homework.setSubject(mSubjectEditText.getText().toString());
                homework.setDate(mDateEditText.getText().toString());

                mDataSource.addHomework(homework);

                Intent intent = new Intent(AddHomeworkActivity.this, HomeworkListActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

}
