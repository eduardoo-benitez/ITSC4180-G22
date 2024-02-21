package edu.uncc.assignment05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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
import edu.uncc.assignment05.models.Data;
import edu.uncc.assignment05.models.User;

public class MainActivity extends AppCompatActivity implements
        UsersFragment.UsersListener, AddUserFragment.AddUserListener,
        SelectGenderFragment.SelectGenderListener, SelectAgeFragment.SelectAgeListener,
        SelectStateFragment.SelectStateListener, SelectGroupFragment.SelectGroupListener,
        UserDetailsFragment.UserDetailsListener, SelectSortFragment.SelectSortListener {

    private ArrayList<User> mUsers = Data.sampleTestUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, UsersFragment.newInstance(mUsers), "users-tag") //we use .newInstance(args) to initialize a Fragment with data. As such we need
                .addToBackStack(null)                                              //a newInstance function + an onCreate in the Fragment.
                .commit();                                                               //we need a tag in case we need to come back and update the fields (other than mUsers since when we update it in MainActivity, it also updates in Users (see lines 164 & 165))
    }                                                                                    //make sure to use .add for the first fragment rendered in onCreate and not .replace

    @Override
    public void goToAddUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddUserFragment(), "add-user-tag") //we use new Fragment() when we do not need to initialize the Fragment with anything.
                .addToBackStack(null)                                        //we do not need newInstance + an onCreate in the Fragment, we just use the default constructor.
                .commit();                                                         //we need a tag in case we need to come back and update the fields
    }
    @Override
    public void goToUserDetails(User user, int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, UserDetailsFragment.newInstance(user, position))
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
        mUsers.add(newUser); //since .add directly updates the mUsers array in memory, we do not need to reference any sort of setter in UsersFragment.
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void selectGender(String gender) {
        AddUserFragment addUserFragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("add-user-tag"); //when we need to UPDATE fields within a Fragment that has already been
        if (addUserFragment != null) {                                                                                     //initialized (like AddUser has been on line 41. REMEMBER THE TAG).
            addUserFragment.setGender(gender);
            getSupportFragmentManager().popBackStack();
        }
    }
    @Override
    public void cancelSelectGender() {
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
    public void deleteUserDetails(int position) {
        mUsers.remove(position);
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void userDetailsBack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sortToUsers(Comparator <User> comparator, String sortText) {
        UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("users-tag");
        if (usersFragment != null) {
            mUsers.sort(comparator); //because mUsers is an ArrayList, and .sort updates the same array in memory, it may be updated in MainActivity and said change is then reflected in UsersFragment, which was initialized with the same ArrayList (line 36). If we did not need to also update the string below we could have just has this function be the same as the ones on line 93 or 151.
            usersFragment.setSortText(sortText); //if we wanted to update sortText in a similar way as mUsers above, we would need do: this.sortText = sortText. HOWEVER, this creates a copy of the string in memory. this new string is separate from the one present in UsersFragment, causing the change to not reflect in UsersFragment. As such we need to use a setter defined in UsersFragment in order to update the string present in UsersFragment.
            getSupportFragmentManager().popBackStack();
        }
    }
    @Override
    public void sortToUsersCancel() {
        getSupportFragmentManager().popBackStack();
    }
}