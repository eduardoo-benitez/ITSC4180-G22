package edu.uncc.assignment04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import edu.uncc.assignment04.fragments.DemographicFragment;
import edu.uncc.assignment04.fragments.IdentificationFragment;
import edu.uncc.assignment04.fragments.MainFragment;
import edu.uncc.assignment04.fragments.ProfileFragment;
import edu.uncc.assignment04.fragments.SelectEducationFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.MainListener, IdentificationFragment.IdentificationListener,
        SelectEducationFragment.SelectEducationListener, DemographicFragment.DemographicListener {

    Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new MainFragment())
                .commit();
    }

    //MainFragment -> IdentificationFragment
    @Override
    public void goToIdentification() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new IdentificationFragment())
                .commit();
    }

    //IdentificationFragment -> DemographicFragment
    @Override
    public void goToDemographic(String name, String email, String role) {

        if (name.equals("") || email.equals("") || role.equals("")) {
            Toast.makeText(this, "Missing input!!!!!", Toast.LENGTH_LONG).show();
        }
        else {
            response = new Response(name, email, role);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootView, DemographicFragment.newInstance(response), "demographic-tag")
                    .addToBackStack(null)
                    .commit();
        }
    }

    //DemographicFragment -> EducationFragment
    @Override
    public void goToEducation() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectEducationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToProfile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(response))
                .addToBackStack(null)
                .commit();
    }

    //EducationFragment -> DemographicFragment
    @Override
    public void educationUpdate(String education) {
        DemographicFragment demographicFragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic-tag");
        if (demographicFragment != null) {
            demographicFragment.setSelectEducation(education);
            getSupportFragmentManager().popBackStack();
        }
    }

    //EducationFragment -> DemographicActivity
    @Override
    public void cancelEducationUpdate() {
        getSupportFragmentManager().popBackStack();
    }
}