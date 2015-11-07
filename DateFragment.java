package com.garden.gardenapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.widget.DatePicker;

import java.util.Calendar;

public class DateFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    /**
     * To communicata data between activity - fragment - activity
     * we have to use interfaces, according to the developer site
     */
    public interface OnDatePass {
        public void onDatePass(int year, int month, int day);
    }

    /**
     * Then, connect the containing class' implementation of
     * the interface to the fragment in the onAttach method
     */
    OnDatePass datePasser;
    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        datePasser = (OnDatePass) a;
    }

    //commented out is for normal dialog
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.dialogfragment, container,
//                false);
//        getDialog().setTitle("DialogFragment Tutorial");
//        // Do something else
//        return rootView;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        datePasser.onDatePass(year, month, day); //call data on datePasser object
    }


}
