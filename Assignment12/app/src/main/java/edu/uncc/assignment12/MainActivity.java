package edu.uncc.assignment12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.assignment12.Fragments.AddLogFragment;
import edu.uncc.assignment12.Fragments.DatePickerFragment;
import edu.uncc.assignment12.Fragments.HoursExercisedFragment;
import edu.uncc.assignment12.Fragments.HoursSleptFragment;
import edu.uncc.assignment12.Fragments.LogsFragment;
import edu.uncc.assignment12.Fragments.SleepQualityFragment;
import edu.uncc.assignment12.Fragments.TimePickerFragment;
import edu.uncc.assignment12.Fragments.VisualizeFragment;
import edu.uncc.assignment12.Models.LogEntry;

public class MainActivity extends AppCompatActivity implements LogsFragment.LogsListener, AddLogFragment.AddLogListener, VisualizeFragment.VisualizeListener, DatePickerFragment.DatePickerListener,
        TimePickerFragment.TimePickerListener, HoursSleptFragment.HoursSleptListener, SleepQualityFragment.SleepQualityListener, HoursExercisedFragment.HoursExercisedListener {

    private ArrayList<LogEntry> mLogEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, LogsFragment.newInstance(mLogEntries), "logs-tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToAddLog() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new AddLogFragment(), "add-log-tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToVisualize() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new VisualizeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToDateTime() {
        new DatePickerFragment().show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void goToHoursSlept() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new HoursSleptFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToSleepQuality() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SleepQualityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToHoursExercised() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new HoursExercisedFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addLog(LogEntry logEntry) {
        mLogEntries.add(logEntry);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void back() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectedSleepQuality(int sleepQuality) {
        //TODO: Take the param and pass it into the corresponding setter in AddLogFragment. Remember to use the tag :)
    }

    @Override
    public void selectedHoursSlept(double hoursSlept) {
        //TODO: Take the param and pass it into the corresponding setter in AddLogFragment
    }

    @Override
    public void selectedHoursExercised(double hoursExercised) {
        //TODO: Take the param and pass it into the corresponding setter in AddLogFragment
    }

    @Override
    public void selectedDate(String date) {
        AddLogFragment addLogFragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-tag");
        if (addLogFragment != null) {
            addLogFragment.setDate(date);
        }
    }

    @Override
    public void selectedTime(String time) {
        AddLogFragment addLogFragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-tag");
        if (addLogFragment != null) {
            addLogFragment.setTime(time);
        }
    }
}