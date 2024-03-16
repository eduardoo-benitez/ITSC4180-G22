package edu.uncc.assignment06;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Comparator;

import edu.uncc.assignment06.fragments.AddTaskFragment;
import edu.uncc.assignment06.fragments.SelectCategoryFragment;
import edu.uncc.assignment06.fragments.SelectPriorityFragment;
import edu.uncc.assignment06.fragments.TaskDetailsFragment;
import edu.uncc.assignment06.fragments.TasksFragment;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksListener, TaskDetailsFragment.TaskDetailsListener, AddTaskFragment.AddTaskListener, SelectCategoryFragment.SelectCategoryListener, SelectPriorityFragment.SelectPriorityListener {

    private ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTasks.addAll(Data.sampleTestTasks); //adding for testing

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new TasksFragment(), "tasks-fragment-tag")
                .commit();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return mTasks;
    }

    @Override
    public void clearAllTasks() {
        mTasks.clear();
    }

    @Override
    public void gotoAddTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddTaskFragment(), "add-task-tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public ArrayList<Task> sortTasks(Comparator<Task> comparator) {
        mTasks.sort(comparator);
        return mTasks;
    }

    @Override
    public void gotoTaskDetails(Task task, int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, TaskDetailsFragment.newInstance(task, position))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteTask(int position) {
        mTasks.remove(position);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new TasksFragment())
                .commit();
    }

    @Override
    public void detailsBack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteFromDetails(int position) {
        mTasks.remove(position);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelSelectCategory() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectCategory(String category) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("add-task-tag");
        if (addTaskFragment != null) {
           addTaskFragment.setCategory(category);
           getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void goToCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectCategoryFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToPriority() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectPriorityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void tasksUpdate(Task newTask) {
        mTasks.add(newTask);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelSelectPriority() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectPriority(String priority) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("add-task-tag");
        if (addTaskFragment != null) {
            addTaskFragment.setPriority(priority);
            getSupportFragmentManager().popBackStack();
        }
    }
}