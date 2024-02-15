package edu.uncc.assignment05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.assignment05.fragments.AddUserFragment;
import edu.uncc.assignment05.fragments.SelectAgeFragment;
import edu.uncc.assignment05.fragments.SelectGenderFragment;
import edu.uncc.assignment05.fragments.SelectGroupFragment;
import edu.uncc.assignment05.fragments.SelectStateFragment;
import edu.uncc.assignment05.fragments.UsersFragment;
import edu.uncc.assignment05.models.User;

public class MainActivity extends AppCompatActivity implements UsersFragment.UsersListener, AddUserFragment.AddUserListener {

    private ArrayList<User> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test user
        mUsers.add(new User("Zname1", "email1", "gender1", 1, "state1", "group1"));
        mUsers.add(new User("Aname2", "email2", "gender2", 2, "state2", "group2"));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, UsersFragment.newInstance(mUsers), "users-tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToAddUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddUserFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToGender() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectGenderFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToAge() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectAgeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToState() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectStateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToGroup() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectGroupFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void userUpdate(User newUser) {
        UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("users-tag");
        if (usersFragment != null) {
            usersFragment.addUser(newUser);
            getSupportFragmentManager().popBackStack();
        }
    }
}