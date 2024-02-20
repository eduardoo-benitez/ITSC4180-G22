package edu.uncc.assignment05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Comparator;

import edu.uncc.assignment05.fragments.AddUserFragment;
import edu.uncc.assignment05.fragments.SelectAgeFragment;
import edu.uncc.assignment05.fragments.SelectGenderFragment;
import edu.uncc.assignment05.fragments.SelectGroupFragment;
import edu.uncc.assignment05.fragments.SelectSortFragment;
import edu.uncc.assignment05.fragments.SelectStateFragment;
import edu.uncc.assignment05.fragments.UserDetailsFragment;
import edu.uncc.assignment05.fragments.UsersFragment;
import edu.uncc.assignment05.models.User;

public class MainActivity extends AppCompatActivity implements
        UsersFragment.UsersListener, AddUserFragment.AddUserListener, SelectSortFragment.SelectSortListener,
        SelectGenderFragment.SelectGenderListener, SelectAgeFragment.SelectAgeListener, UserDetailsFragment.UserDetailsListener ,
        SelectStateFragment.SelectStateListener, SelectGroupFragment.SelectGroupListener {

    private ArrayList<User> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test user
        mUsers.add(new User("Zname1", "Aemail1", "Zgender1", 1, "Astate1", "Zgroup1"));
        mUsers.add(new User("Aname2", "Zemail2", "Agender2", 2, "Zstate2", "Agroup2"));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, UsersFragment.newInstance(mUsers), "users-tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToSort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectSortFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToAddUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddUserFragment(), "add-user-tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToUserDetails(User user, int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UserDetailsFragment.newInstance(user, position))
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

    @Override
    public void sortToUsers(Comparator <User> comparator) {
        UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("users-tag");
        if (usersFragment != null) {
            usersFragment.sortUsers(comparator);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void sortToUsersCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectAge(int age) {
        AddUserFragment addUserFragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-tag");
        if (addUserFragment != null) {
            addUserFragment.setAge(age);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelSelectAge() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteUserDetails(int position) {
        UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("users-tag");
        if (usersFragment != null) {
            usersFragment.deleteUser(position);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void userDetailsBack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectGender(String gender) {
        AddUserFragment addUserFragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-tag");
        if (addUserFragment != null) {
            addUserFragment.setGender(gender);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelSelectGender() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectGroup(String group) {
        AddUserFragment addUserFragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-tag");
        if (addUserFragment != null) {
            addUserFragment.setGroup(group);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelSelectGroup() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectState(String state) {
        AddUserFragment addUserFragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-tag");
        if (addUserFragment != null) {
            addUserFragment.setState(state);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void cancelSelectState() {
        getSupportFragmentManager().popBackStack();
    }
}