package edu.uncc.assignment04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import edu.uncc.assignment04.fragments.DemographicFragment;
import edu.uncc.assignment04.fragments.IdentificationFragment;
import edu.uncc.assignment04.fragments.MainFragment;
import edu.uncc.assignment04.fragments.SelectEducationFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.MainListener, IdentificationFragment.IdentificationListener, SelectEducationFragment.SelectEducationListener, DemographicFragment.DemographicListener {

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

    //Somehow pass response into demographic??
    //IdentificationFragment -> DemographicFragment
    @Override
    public void goToDemographic(String name, String email, String role) {

        if (name.equals("") || email.equals("") || role.equals("")) {
            Toast.makeText(this, "Missing input!!!!!", Toast.LENGTH_LONG).show();
        }
        else {
            response = new Response(name, email, role);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootView, new DemographicFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    //Maybe everything below this into DemographicFragment??
    //DemographicFragment -> EducationFragment
    @Override
    public void goToEducation() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectEducationFragment())
                .addToBackStack(null)
                .commit();
    }

    //Maybe use the back stack to go back to demographic instead of replacing??
    //EducationFragment -> DemographicFragment
    @Override
    public void educationUpdate(String education) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new DemographicFragment())
                .addToBackStack(null)
                .commit();

        TextView educationView = findViewById(R.id.textViewEducation);
        educationView.setText(education);
    }
}