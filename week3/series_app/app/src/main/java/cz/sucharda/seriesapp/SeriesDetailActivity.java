package cz.sucharda.seriesapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.sucharda.seriesapp.Model.DB.SeriesDataSource;
import cz.sucharda.seriesapp.Model.DB.SeriesDbSchema;
import cz.sucharda.seriesapp.Model.DatePickerFragment;
import cz.sucharda.seriesapp.Model.Series;

public class SeriesDetailActivity extends AppCompatActivity implements DatePickerFragment.SaveDateInterface {

    private SeriesDataSource mDataSource;
    private EditText mName, mDescription;
    private Button mDateStart, mDateFinish, mUpdateSeries;
    private CheckBox mFinished;
    SimpleDateFormat simpledateformat;

    Series mSeries;

    private Date mStart, mFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);

        simpledateformat = new SimpleDateFormat(SeriesDbSchema.SeriesTable.DATE_FORMAT);

        mDataSource = new SeriesDataSource(this);
        long seriesId = getIntent().getLongExtra("series_id", 0);
        mSeries = mDataSource.getSeriesById(seriesId);

        mName = (EditText) findViewById(R.id.asd_name);
        mDescription = (EditText) findViewById(R.id.asd_description);
        mDateStart = (Button) findViewById(R.id.asd_ld_startDate);
        mDateFinish = (Button) findViewById(R.id.asd_ld_endDate);
        mFinished = (CheckBox) findViewById(R.id.asd_finished);
        mUpdateSeries = (Button) findViewById(R.id.asd_saveSeries);

        mName.setText(mSeries.getName());
        mDescription.setText(mSeries.getDescription());
        mFinished.setChecked(mSeries.isFinished());
        mStart = mSeries.getDateStart();
        if( mStart != null ) {
            mDateStart.setText( simpledateformat.format(mStart) );
        }
        mFinish = mSeries.getDateFinish();
        if( mFinish != null ) {
            mDateFinish.setText( simpledateformat.format(mFinish) );
        }
        mDateFinish.setEnabled(mSeries.isFinished());

        mFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDateFinish.setEnabled(isChecked);
            }
        });

        mDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "startDate");
            }
        });

        mDateFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "finishDate");
            }
        });

        mUpdateSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSeries();
            }
        });
    }

    private void saveSeries() {
        String mTestName = mName.getText().toString();

        // Check if required fields are entered
        if(TextUtils.isEmpty(mTestName)) {
            Toast.makeText(this, "Please enter series name.", Toast.LENGTH_SHORT).show();
        } else {
            boolean isFinished = mFinished.isChecked();

            mSeries.setName(mTestName);
            mSeries.setDescription(mDescription.getText().toString());
            mSeries.setFinished(mFinished.isChecked());
            mSeries.setDateStart(mStart);
            if (isFinished) {
                mSeries.setDateFinish(mFinish);
            } else {
                Date date3 = null;
                mSeries.setDateFinish(date3);
            }

            Intent data = new Intent();
            data.putExtra("updatedSeries", mSeries);
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void saveStartDate(Date mStart) {
        if( mStart != null ) {
            this.mStart = mStart;
            mDateStart.setText(simpledateformat.format(mStart));
        }
    }

    @Override
    public void saveFinishDate(Date mFinish) {
        if( mFinish != null ) {
            this.mFinish = mFinish;
            mDateFinish.setText(simpledateformat.format(mFinish));
        }
    }
}
