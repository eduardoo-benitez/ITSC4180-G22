package edu.uncc.assignment12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.assignment12.Fragments.AddLogFragment;
import edu.uncc.assignment12.Fragments.DateTimeFragment;
import edu.uncc.assignment12.Fragments.HoursExercisedFragment;
import edu.uncc.assignment12.Fragments.HoursSleptFragment;
import edu.uncc.assignment12.Fragments.LogsFragment;
import edu.uncc.assignment12.Fragments.SleepQualityFragment;
import edu.uncc.assignment12.Fragments.VisualizeFragment;
import edu.uncc.assignment12.Models.Log;

public class MainActivity extends AppCompatActivity implements LogsFragment.LogsListener, AddLogFragment.AddLogListener, VisualizeFragment.VisualizeListener, DateTimeFragment.DateTimeListener,
        HoursSleptFragment.HoursSleptListener, SleepQualityFragment.SleepQualityListener, HoursExercisedFragment.HoursExercisedListener {

    private ArrayList<Log> mLogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, LogsFragment.newInstance(mLogs), "logs-tag")
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new DateTimeFragment())
                .addToBackStack(null)
                .commit();
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
    public void addLog(Log log) {
        mLogs.add(log);
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

    //TODO: Figure out what dtype the date/time is
    @Override
    public void selectedDateTime() {
        //TODO: Take the param and pass it into the corresponding setter in AddLogFragment

    }
}