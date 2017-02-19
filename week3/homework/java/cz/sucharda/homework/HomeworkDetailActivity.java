package cz.sucharda.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import cz.sucharda.homework.Model.DB.HomeworkDataSource;
import cz.sucharda.homework.Model.Homework;

public class HomeworkDetailActivity extends AppCompatActivity {

    private HomeworkDataSource mDataSource;
    private EditText mTitle, mSubject, mDate;
    private CheckBox mFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDataSource = new HomeworkDataSource(this);
        long homeworkId = getIntent().getLongExtra("homework_id", 0);
        final Homework mHomework = mDataSource.getHomeworkById(homeworkId);

        mTitle = (EditText) findViewById(R.id.chd_editText_title);
        mSubject = (EditText) findViewById(R.id.chd_editText_subject);
        mDate = (EditText) findViewById(R.id.chd_editText_date);
        mFinished = (CheckBox) findViewById(R.id.chd_checkBox_finished);

        mTitle.setText(mHomework.getTitle());
        mSubject.setText(mHomework.getSubject());
        mDate.setText(mHomework.getDate());
        mFinished.setChecked(mHomework.isFinished());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomework.setTitle(mTitle.getText().toString());
                mHomework.setSubject(mSubject.getText().toString());
                mHomework.setDate(mDate.getText().toString());
                mHomework.setFinished(mFinished.isChecked());
                mDataSource.updateHomework(mHomework);
                Intent intent = new Intent(HomeworkDetailActivity.this, HomeworkListActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

}
