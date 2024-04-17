package edu.uncc.assignment11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import edu.uncc.assignment11.auth.LoginFragment;
import edu.uncc.assignment11.auth.RegisterFragment;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.LoginListener, RegisterFragment.RegisterListener, MailboxFragment.MailboxListener,
        NewMessageFragment.NewMessageListener, UserListFragment.UserListListener, ReplyFragment.ReplyListener {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mAuth.getCurrentUser() == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new LoginFragment())
                    .commit();
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new MailboxFragment())
                    .commit();
        }
    }

    @Override
    public void register() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new RegisterFragment())
                .commit();
    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void authCompleted() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new MailboxFragment())
                .commit();
    }

    @Override
    public void logout() {
        mAuth.signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void goToNewMessage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new NewMessageFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToUserList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new UserListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void back() {
        getSupportFragmentManager().popBackStack();
    }
}