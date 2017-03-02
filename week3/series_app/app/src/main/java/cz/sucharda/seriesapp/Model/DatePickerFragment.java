package cz.sucharda.seriesapp.Model;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private Calendar mCalendar;
    private SaveDateInterface saveDateInterface;
    private String tag;

    public DatePickerFragment() {} // required

    public interface SaveDateInterface {
        void saveStartDate(Date date);
        void saveFinishDate(Date date);
    }

    public static DatePickerFragment newInstance(Calendar c) {
        DatePickerFragment fragment = new DatePickerFragment();

        Bundle args = new Bundle();
        args.putSerializable("calendar", c);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        this.tag = tag;
        super.show(manager, tag);
    }

    // restore any initial passed arguments as needed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCalendar = (Calendar) getArguments().getSerializable("calendar");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            saveDateInterface = (SaveDateInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDateSetListener.");
        }
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        if (getDialog() == null) {  // Returns mDialog
            // Tells DialogFragment to not use the fragment as a dialog, and so won't try to use mDialog
            setShowsDialog(false);
        }
        super.onActivityCreated(arg0);  // Will now complete and not crash
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);

        if( mCalendar == null ) {
            mCalendar = Calendar.getInstance();
        }

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);



        switch (tag) {
            case "startDate":
                saveDateInterface.saveStartDate(c.getTime());
                break;
            case "finishDate":
                saveDateInterface.saveFinishDate(c.getTime());
                break;
        }
    }
}