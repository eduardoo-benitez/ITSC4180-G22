package edu.uncc.assignment12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.uncc.assignment12.Fragments.AddLogFragment;
import edu.uncc.assignment12.Fragments.DateTimeFragment;
import edu.uncc.assignment12.Fragments.HoursExercisedFragment;
import edu.uncc.assignment12.Fragments.HoursSleptFragment;
import edu.uncc.assignment12.Fragments.LogsFragment;
import edu.uncc.assignment12.Fragments.SleepQualityFragment;
import edu.uncc.assignment12.Fragments.VisualizeFragment;

public class MainActivity extends AppCompatActivity implements LogsFragment.LogsListener, AddLogFragment.AddLogListener, VisualizeFragment.VisualizeListener, DateTimeFragment.DateTimeListener,
        HoursSleptFragment.HoursSleptListener, SleepQualityFragment.SleepQualityListener, HoursExercisedFragment.HoursExercisedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LogsFragment())
                .commit();
    }

    @Override
    public void goToAddLog() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new AddLogFragment())
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
    public void back() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectedSleepQuality() {

    }

    @Override
    public void selectedHoursSlept() {

    }

    @Override
    public void selectedHoursExercised() {

    }

    @Override
    public void selectedDateTime() {

    }
}