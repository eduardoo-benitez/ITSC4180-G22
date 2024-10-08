package edu.uncc.assignment12.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = month + "/" + day + "/" + year;
        new TimePickerFragment().show(getParentFragmentManager(), "timePicker");
        mListener.selectedDate(date);
    }

    DatePickerListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (DatePickerListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement DatePickerListener");
        }
    }

    public interface DatePickerListener {
        void selectedDate(String date);
    }
}
