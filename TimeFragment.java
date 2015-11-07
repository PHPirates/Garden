package com.garden.gardenapp;

import android.app.Activity;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;
import android.text.format.DateFormat;
import java.util.Calendar;
import android.support.v4.app.DialogFragment;

public class TimeFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    /*same as DateFrament class, basically*/

    //interface to pass data
    public interface OnTimePass {
        public void onTimePass(int hour, int minute);
    }

    OnTimePass timePasser;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        timePasser = (OnTimePass) a;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

    }

    public void onTimeSet (TimePicker view, int hour, int minute) {
        timePasser.onTimePass(hour,minute);
    }
}
